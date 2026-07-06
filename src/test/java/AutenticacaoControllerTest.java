
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sea.desafiobackend.controller.AutenticacaoController;
import com.sea.desafiobackend.domain.Usuario;
import com.sea.desafiobackend.dto.request.LoginRequestDTO;
import com.sea.desafiobackend.dto.request.RegistroRequestDTO;
import com.sea.desafiobackend.enums.Perfil;
import com.sea.desafiobackend.repository.UsuarioRepository;
import com.sea.desafiobackend.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AutenticacaoControllerTest {

    @InjectMocks
    private AutenticacaoController controller;

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private TokenService tokenService;

    @Mock
    private UsuarioRepository usuarioRepository;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void registrar_ComDadosValidos_DeveRetornarCreated201() throws Exception {
        RegistroRequestDTO dto = new RegistroRequestDTO();
        dto.setLogin("novo@sea.com");
        dto.setSenha("123456");
        dto.setPerfil(Perfil.ROLE_USER);

        when(usuarioRepository.findByLogin(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(post("/auth/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated()) // Espera Status 201
                .andExpect(content().string("Usuário registrado com sucesso!")); // Espera a nossa frase customizada
    }

    @Test
    void registrar_ComLoginJaExistente_DeveRetornarBadRequest400() throws Exception {
        RegistroRequestDTO dto = new RegistroRequestDTO();
        dto.setLogin("existente@sea.com");
        dto.setSenha("123456");
        dto.setPerfil(Perfil.ROLE_USER);

        when(usuarioRepository.findByLogin(anyString())).thenReturn(Optional.of(new Usuario()));

        mockMvc.perform(post("/auth/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest()); // Espera Status 400
    }

    @Test
    void registrar_ComDadosInvalidos_DeveRetornarBadRequest400() throws Exception {
        RegistroRequestDTO dto = new RegistroRequestDTO();
        dto.setLogin("");
        dto.setSenha("123456");
        dto.setPerfil(null);

        mockMvc.perform(post("/auth/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void autenticar_ComCredenciaisValidas_DeveRetornarToken() throws Exception {
        LoginRequestDTO dto = new LoginRequestDTO();
        dto.setLogin("teste@sea.com");
        dto.setSenha("123456");

        Authentication authMock = mock(Authentication.class);
        when(authManager.authenticate(any())).thenReturn(authMock);
        when(tokenService.gerarToken(authMock)).thenReturn("eyJhbGci.super.secreto");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value("Usuário Logado com sucesso!"))
                .andExpect(jsonPath("$.token").value("eyJhbGci.super.secreto"))
                .andExpect(jsonPath("$.tipo").value("Bearer"));
    }

    @Test
    void autenticar_ComCredenciaisInvalidas_DeveRetornarUnauthorized401() throws Exception {
        LoginRequestDTO dto = new LoginRequestDTO();
        dto.setLogin("teste@sea.com");
        dto.setSenha("senhaErrada");

        when(authManager.authenticate(any())).thenThrow(new BadCredentialsException("Credenciais inválidas"));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized()) // Espera Status 401
                .andExpect(content().string("Login ou senha inválidos!"));
    }

    @Test
    void autenticar_ComDadosInvalidos_DeveRetornarBadRequest400() throws Exception {
        LoginRequestDTO dto = new LoginRequestDTO();
        dto.setLogin("teste@sea.com");
        dto.setSenha("");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}