import com.sea.desafiobackend.domain.Usuario;
import com.sea.desafiobackend.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class TokenServiceTest {

    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        tokenService = new TokenService();
        ReflectionTestUtils.setField(tokenService, "secret", "chaveSecretaMuitoForteParaOsTestesUnitarios");
        ReflectionTestUtils.setField(tokenService, "expiration", "86400000");
    }

    @Test
    void gerarToken_ComAutenticacaoValida_DeveRetornarToken() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setId(10L);
        usuario.setLogin("teste@sea.com");

        Authentication auth = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

        // Act
        String token = tokenService.gerarToken(auth);

        // Assert
        assertNotNull(token);
        assertTrue(tokenService.isTokenValido(token));
    }

    @Test
    void getIdUsuario_ComTokenValido_DeveRetornarIdDoUsuario() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setId(99L);
        usuario.setLogin("admin@sea.com");
        Authentication auth = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

        String token = tokenService.gerarToken(auth);

        // Act
        Long idExtraido = tokenService.getIdUsuario(token);

        // Assert
        assertEquals(99L, idExtraido);
    }

    @Test
    void isTokenValido_ComAssinaturaInvalida_DeveRetornarFalse() {
        // Arrange
        String tokenInvalido = "eyJhbGciOiJIUzI1NiJ9.token.invalido.inventado";

        // Act
        boolean isValido = tokenService.isTokenValido(tokenInvalido);

        // Assert
        assertFalse(isValido);
    }

}
