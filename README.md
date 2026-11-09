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

## Configuração

Crie um banco chamado `marketplace`. Configure as variáveis de ambiente:

```bash
export DB_USER=marketplace
export DB_PASSWORD=sua_senha
export JWT_SECRET=uma_chave_aleatoria_com_pelo_menos_32_caracteres
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
Authorization: Bearer <accessToken>
```

A configuração atual define validade de uma hora para o access token.

### Refresh token

A rota é `POST /usuarios/auth/refresh`. O serviço espera receber o refresh token e retorna um novo access token e um novo refresh token.

**Atenção:** no código atual, o campo do request chama-se `accessToken`, mas o valor é usado como refresh token. Ajuste o nome desse campo no DTO e no controller para `refreshToken`, ou documente claramente o contrato antes de integrar clientes à API.

## Rotas e permissões

| Recurso | Rotas | Acesso |
|---|---|---|
| Autenticação | `POST /usuarios/auth/cadastrar`, `POST /usuarios/auth/login`, `POST /usuarios/auth/refresh` | Público |
| Usuários | `GET /usuarios/auth/listar` | `ADMIN` ou `ENTREGADOR` |
| Usuários | `PUT /usuarios/auth/alterar/roles/{id}?nome=ROLE` | `ADMIN` |
| Usuários | `PUT /usuarios/auth/atualizar/{id}` | Autenticado; o serviço permite alterar apenas a própria conta |
| Usuários | `DELETE /usuarios/auth/deletar/{id}` | `ADMIN` |
| Endereços | `/enderecos/**` | Autenticado |
| Restaurantes | `GET /restaurantes/**` | `DONO_RESTAURANTE`, `ADMIN` ou `CLIENTE` |
| Restaurantes | Escrita em `/restaurantes/**` | `DONO_RESTAURANTE` ou `ADMIN` |
| Categorias | `/categorias/**` | `DONO_RESTAURANTE` |
| Pratos | `GET /pratos/**` | Autenticado |
| Pratos | Escrita em `/pratos/**` | `VENDEDOR` ou `DONO_RESTAURANTE` |
| Pedidos | `POST /pedidos/cadastrar` | `CLIENTE` |
| Pedidos | `PUT /pedidos/atualizar/{id}` | `ENTREGADOR`, `DONO_RESTAURANTE`, `VENDEDOR` ou `ADMIN` |
| Pedidos | `DELETE /pedidos/**` | `ADMIN` |
| Pedidos e itens | Demais rotas protegidas | Autenticado |
| Pagamentos | `/pagamentos/**` | `CAIXA` |

As listagens usam paginação Spring Data, com parâmetros como `page`, `size` e `sort`. A listagem de pratos também recebe o parâmetro `nome`, por exemplo: `GET /pratos/listar?nome=pizza`.

## Estrutura do projeto

```text
src/main/java/joao/alves/delivery/marketplace/
├── config/       # Segurança, filtro JWT e configuração
├── controller/   # Endpoints REST
├── domain/       # Entidades e regras do domínio
├── dto/          # Requests e responses
├── mapper/       # Conversão entre entidades e DTOs
├── repository/   # Acesso a dados com Spring Data JPA
└── service/      # Regras de negócio
```

## Autor

**João Vitor de Jesus Alves**  
[GitHub](https://github.com/JoaoVitorAlves-dev) · [Repositório Marketplace-Delivery](https://github.com/JoaoVitorAlves-dev/Marketplace-Delivery)