-- Tabela para Controle de Acesso (Autenticação)
CREATE TABLE usuario
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(20)  NOT NULL
);

-- Tabela Principal de Clientes
CREATE TABLE cliente
(
    id   BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf  VARCHAR(11)  NOT NULL UNIQUE -- Armazenado sem máscara (11 dígitos) e único
);

-- Tabela de Endereços (Relacionamento 1 para 1 com Cliente)
CREATE TABLE endereco
(
    id          BIGSERIAL PRIMARY KEY,
    cliente_id  BIGINT       NOT NULL UNIQUE,
    cep         VARCHAR(8)   NOT NULL, -- Armazenado sem máscara (8 dígitos)
    logradouro  VARCHAR(150) NOT NULL,
    bairro      VARCHAR(100) NOT NULL,
    cidade      VARCHAR(100) NOT NULL,
    uf          VARCHAR(2)   NOT NULL,
    complemento VARCHAR(150),
    CONSTRAINT fk_endereco_cliente FOREIGN KEY (cliente_id) REFERENCES cliente (id) ON DELETE CASCADE
);

-- Tabela de Telefones (Relacionamento 1 para N com Cliente)
CREATE TABLE telefone
(
    id         BIGSERIAL PRIMARY KEY,
    cliente_id BIGINT      NOT NULL,
    numero     VARCHAR(11) NOT NULL, -- Armazenado sem máscara (até 11 dígitos para celular com 9º dígito)
    tipo       VARCHAR(20) NOT NULL, -- RESIDENCIAL, COMERCIAL, CELULAR
    CONSTRAINT fk_telefone_cliente FOREIGN KEY (cliente_id) REFERENCES cliente (id) ON DELETE CASCADE
);

-- Tabela de E-mails (Relacionamento 1 para N com Cliente)
CREATE TABLE email
(
    id         BIGSERIAL PRIMARY KEY,
    cliente_id BIGINT       NOT NULL,
    email      VARCHAR(150) NOT NULL,
    CONSTRAINT fk_email_cliente FOREIGN KEY (cliente_id) REFERENCES cliente (id) ON DELETE CASCADE
);

-- Inserção dos usuários no desafio
-- Nota: As senhas reais serão criptografadas com BCrypt mais adiante no Spring Security.
-- Por enquanto, inseri os registros para estruturar o ambiente.
INSERT INTO usuario (username, password, role)
VALUES ('admin', '123qwe!@#', 'ADMIN');
INSERT INTO usuario (username, password, role)
VALUES ('comum', '123qwe123', 'PADRAO');