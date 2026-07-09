# Desafio Backend SEA - API RESTful

![Java](https://img.shields.io/badge/Java-8-blue?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-2.7.x-brightgreen?logo=spring)
![Spring Security](https://img.shields.io/badge/Spring_Security-JWT-green)
![Docker](https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI_3-85EA2D?logo=swagger)
![Status](https://img.shields.io/badge/Status-Backend_Conclu%C3%ADdo-success)

## Visão Geral
API RESTful desenvolvida como parte do desafio técnico da **SEA** (para o cargo de Desenvolvedor BackEnd Júnior). O sistema é responsável pelo gerenciamento de clientes e seus respectivos telefones, incorporando um sistema robusto de autenticação, controle de acesso por perfis (RBAC) e cobertura de testes automatizados.

> **FrontEnd:** https://github.com/CaioVMSantos/desafio-fullstack-sea-frontend-.

---

## Tecnologias e Ferramentas

O projeto foi construído utilizando as seguintes tecnologias:

* **Linguagem:** Java 8
* **Framework:** Spring Boot (2.7.x)
* **Segurança:** Spring Security + JWT (JSON Web Token) + BCrypt
* **Persistência:** Spring Data JPA / Hibernate
* **Banco de Dados:** PostgreSQL / MySQL *(configurável via variáveis de ambiente)*
* **Infraestrutura:** Docker & Docker Compose
* **Documentação:** Springdoc OpenAPI (Swagger UI)
* **Testes:** JUnit 5, Mockito, MockMvc
* **Gerenciador de Dependências:** Maven
* **Outros:** Lombok, Bean Validation

---

## Decisões Arquiteturais e Padrões

Para garantir manutenibilidade, segurança e escalabilidade, foram adotados os seguintes padrões:

1. **Arquitetura em Camadas (Layered Architecture):** Separação clara entre `Controllers`, `Services`, `Repositories` e `Security`.
2. **Padrão DTO (Data Transfer Object):** Isolamento do modelo de domínio (Entidades) das requisições e respostas da API, prevenindo *Over-Posting* e exposição de dados sensíveis.
3. **Segurança Stateless (JWT):** A API não mantém estado (sessão) no servidor. Toda requisição protegida deve enviar o token JWT no cabeçalho `Authorization: Bearer <token>`.
4. **Role-Based Access Control (RBAC):** Restrição de endpoints sensíveis (ex: Deleção de clientes) exclusiva para usuários com perfil `ROLE_ADMIN`.
5. **Mapeamento O-R (JPA):** Relacionamento Otimizado bidirecional (`@OneToMany` / `@ManyToOne`) com uso correto de `mappedBy` para evitar tabelas associativas desnecessárias.
6. **Tratamento de Erros e Padrão REST:** Retorno correto dos Status Codes HTTP (`201 Created`, `204 No Content`, `401 Unauthorized`, `403 Forbidden`, `400 Bad Request`).
7. **Segurança de Credenciais:** Uso de variáveis de ambiente (`.env`) para isolar dados sensíveis, como credenciais de banco de dados.

---

## Fluxo de Autenticação

1. **Registro:** Faça um `POST` em `/auth/registrar` enviando `login`, `senha` e `perfil` (`ROLE_ADMIN` ou `ROLE_USER`).
2. **Login:** Faça um `POST` em `/auth/login` com as credenciais. A API retornará um Token JWT.
3. **Acesso:** Envie o token no Header `Authorization` (formato Bearer) para acessar rotas protegidas (ex: `/clientes`).

### Usuários de Teste (Pré-configurados)
Você pode utilizar as credenciais abaixo para testes rápidos, que constavam no edital do desafio:

* **Usuário Admin:**
  * **Login:** `usuarioadmin@sea.com`
  * **Senha:** `123qwe!@#`
* **Usuário Padrão:**
  * **Login:** `usuariopadrao@sea.com`
  * **Senha:** `123qwe123`

---

## Como Executar o Projeto

### Pré-requisitos
* Java 8 instalado.
* Maven instalado (ou utilize o *wrapper* `./mvnw`).
* Docker e Docker Compose instalados **OU** SGBD (PostgreSQL) rodando localmente na sua máquina.

### Passos para rodar

**1. Clone o repositório e acesse a pasta:**
```bash
git clone [https://github.com/seu-usuario/desafio-backend-sea.git](https://github.com/seu-usuario/desafio-backend-sea.git)
cd desafio-backend-sea

```

**2. Crie o Banco de Dados (via Docker):**
Durante o desenvolvimento deste projeto, utilizei o **Docker Compose** para orquestrar o banco de dados de forma ágil. Para subir o container do PostgreSQL já configurado com o banco da aplicação, basta rodar o comando abaixo na raiz do projeto:

```bash
docker-compose up -d

```

*(Alternativa: Se preferir não usar o Docker, abra o seu gerenciador de banco de dados, como pgAdmin ou DBeaver, e execute `CREATE DATABASE desafio_sea_db;`)*

**3. Configure as Variáveis de Ambiente:**
O projeto possui um arquivo base chamado `.env.example`. Crie uma cópia deste arquivo com o nome `.env` na raiz do projeto, ou crie manualmente pela sua IDE de preferência. Esse arquivo será utilizado para armazenar as credenciais do banco de dados e outras variáveis sensíveis.:

```bash
cp .env.example .env

```

Em seguida, abra o arquivo `.env` gerado e certifique-se de que as credenciais coincidem com as do banco de dados:

```env
DB_URL=jdbc:postgresql://localhost:5432/desafio_sea_db
DB_USER=seu_usuario_aqui
DB_PASS=sua_senha_aqui

```

**4. Compile e instale as dependências:**

```bash
mvn clean install

```

**5. Inicie a aplicação:**

```bash
mvn spring-boot:run

```

A API estará disponível em `http://localhost:8080`.

---

## Documentação da API (Swagger)

Essa API está totalmente documentada via **Swagger**. Com a aplicação rodando, acesse:

👉 **http://localhost:8080/swagger-ui.html**

*Dica: Utilize o botão **"Authorize"** no topo da página do Swagger para inserir o seu Token JWT (Bearer) e testar os endpoints restritos diretamente pelo navegador.*

---

## Testes Automatizados

O projeto conta com testes unitários cobrindo a lógica de geração/validação de tokens e as regras de negócio dos Controllers utilizando *Mocks*.

Para rodar a suíte de testes:

```bash
mvn test

```

---

*Desenvolvido por Caio Victor Mendonça, com dedicação para o Desafio Técnico SEA.*
