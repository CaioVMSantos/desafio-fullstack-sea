-- V2__inserir_usuarios.sql

-- Inserção do usuário admin com hash BCrypt
INSERT INTO usuario (username, password, role)
VALUES ('admin', '$2a$10$I100DOSpFeJUENH8wCQq4./Adh09y3T/o3olfgBT1WApI/68gYZP6', 'ADMIN');

-- Inserção do usuário comum com hash BCrypt
INSERT INTO usuario (username, password, role)
VALUES ('comum', '$2a$10$X90a7EYfxf9uW50TyoHaEeSlJ2du/ZO9T87ifZYDsO33WznPSqfoW', 'PADRAO');