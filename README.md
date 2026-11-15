# Marketplace Delivery API

API REST para um marketplace de delivery com restaurantes, pratos, pedidos, pagamentos e controle de acesso por perfil. Desenvolvida com Java, Spring Boot, Spring Security e MySQL.

## Tecnologias

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA e Hibernate
- Spring Security
- JWT com JJWT
- MySQL
- Bean Validation
- Lombok
- Maven
- Docker e Docker Compose

## Funcionalidades

- Cadastro e autenticação de usuários.
- Autenticação stateless com access token JWT e refresh token.
- Senhas protegidas com BCrypt.
- Autorização por roles.
- Gestão de restaurantes, categorias e pratos.
- Criação e atualização de pedidos.
- Itens de pedido e pagamentos.
- Cadastro de endereços.
- Listagens paginadas.

## Perfis

| Perfil | Acesso principal |
|---|---|
| `ADMIN` | Gerenciamento de usuários e operações administrativas |
| `CLIENTE` | Criação de pedidos e consulta de restaurantes |
| `DONO_RESTAURANTE` | Gerenciamento de restaurantes e categorias |
| `VENDEDOR` | Gerenciamento de pratos |
| `ENTREGADOR` | Consulta de usuários e atualização de pedidos |
| `CAIXA` | Operações de pagamento |

## Requisitos

- JDK 21
- Maven 3.9 ou superior, ou o Maven Wrapper incluído
- MySQL
- Docker e Docker Compose (para executar com containers)

## Configuração

Crie um banco chamado `marketplace`. Configure as variáveis de ambiente:

```bash
export DB_USER=marketplace
export DB_PASSWORD=sua_senha
export JWT_SECRET=uma_chave_aleatoria_com_pelo_menos_32_caracteres
```

Para executar com Docker, defina também a senha do administrador do MySQL:

```bash
export DB_ROOT_PASSWORD=sua_senha_root
```

A conexão padrão está configurada para `jdbc:mysql://localhost:3306/marketplace`. Não versione senhas nem uma chave JWT real.

## Executar

Com o Maven Wrapper:

```bash
./mvnw spring-boot:run
```

Ou com Maven instalado:

```bash
mvn spring-boot:run
```

A API inicia, por padrão, em `http://localhost:8080`.

## Executar com Docker

O `Dockerfile` e o `compose.yaml` sobem a API e o MySQL juntos. Crie um arquivo `.env` na raiz com `DB_USER`, `DB_PASSWORD`, `DB_ROOT_PASSWORD` e `JWT_SECRET` (o `DB_USER` deve ser um usuário comum, como `marketplace`, nunca `root`) e execute:

```bash
docker compose up --build
```

A API inicia em `http://localhost:8080`. Para parar:

```bash
docker compose down      # mantém os dados do banco
docker compose down -v   # apaga os dados do banco
```

## Testes

```bash
./mvnw test
```

O teste atualmente presente verifica o carregamento do contexto Spring. Ele não substitui testes de integração dos endpoints, regras de autorização ou operações de pedido.

## Autenticação

Cadastre um usuário em `POST /usuarios/auth/cadastrar` e faça login em `POST /usuarios/auth/login`.

O login recebe `username` e `password`:

```json
{
  "username": "joao",
  "password": "senha-segura"
}
```

Envie o access token nas rotas protegidas:

```http
Authorization: Bearer