-- Inserção do usuário admin (corrigido com o prefixo $2a$)
INSERT INTO usuario (username, password, role)
VALUES ('usuarioadmin@sea.com', '$2a$10$v5n2NKk7VGzzgIVwWHTUuuRmGM0L1oyiO0eohG6.bEXuwpzuZdKJa', 'ROLE_ADMIN');

-- Inserção do usuário comum
INSERT INTO usuario (username, password, role)
VALUES ('usuariopadrao@sea.com', '$2a$10$pRynjJD47aV8mMhLASjbr.VtQAyW4FnPSUpGv.X38oma5IuEg7XRq', 'ROLE_USER');