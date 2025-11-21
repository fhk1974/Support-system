🚀 GS SOA & WebServices – Support System (Gestão de Chamados)
👥 Integrantes

Fabio Hideki Kamikihara – RM550610

Eduardo Osorio – RM550161

📌 Descrição do Projeto

Este projeto consiste em uma API REST para gestão de chamados de suporte (HelpDesk), permitindo:

Registro e autenticação de usuários

Controle de acesso por perfis (USER / ADMIN)

Criação, listagem e gerenciamento de chamados

Alteração de status e atribuição de chamados

Utilização de JWT para autenticação

Padrão STATELESS para sessões

Respostas padronizadas e tratamento global de exceções

O sistema foi desenvolvido atendendo todos os critérios da GS de SOA & WebServices.

🧩 Funcionalidades
🔐 Autenticação & Segurança

Login com JWT

Middleware de validação do token

Perfis:

ADMIN: controla tudo

USER: cria e consulta seus próprios chamados

Política de sessão: STATELESS

🎫 Gestão de Chamados

Criar chamado

Listar chamados do usuário logado

Listar todos os chamados (ADMIN)

Atualizar chamado

Mudar status (OPEN, IN_PROGRESS, CLOSED)

Atribuir chamado a outro usuário (ADMIN)

🧱 Estrutura da Aplicação

Entities: User, Ticket

Enums: Role, TicketStatus, Priority

VO: ContactInfoVO

DTOs: Login, Registro, TicketRequest, TicketResponse

Controllers: AuthController, TicketController

Services: AuthService, TicketService

Security: JwtUtil, JwtAuthFilter, SecurityConfig

Exception Handling: GlobalExceptionHandler

🛠 Tecnologias Utilizadas
Tecnologia	Versão	Uso
Java	17	Linguagem principal
Spring Boot	3	Base da aplicação
Spring Web	-	Criação dos endpoints
Spring Security	-	Autenticação / autorização
Spring Data JPA	-	Persistência
H2 Database	-	Banco em memória
JWT (jjwt)	0.11.x	Tokens de autenticação
Maven	-	Gerenciador de dependências
🗂 Estrutura de Pastas
/src
 └── main
     ├── java
     │    └── com.support.system
     │          ├── controllers
     │          ├── dto
     │          ├── entities
     │          ├── enums
     │          ├── exceptions
     │          ├── repositories
     │          ├── security
     │          ├── services
     │          └── vo
     └── resources
          ├── application.properties
          └── data.sql  (carga inicial opcional)

▶ Como Rodar o Projeto
1. Pré-requisitos

Java 17

Maven

Não precisa instalar banco de dados (H2 em memória)

2. Executar
mvn spring-boot:run

3. Acessar API

Servidor sobe em:

👉 http://localhost:8080

4. Console do H2

👉 http://localhost:8080/h2-console

Configuração:

JDBC URL: jdbc:h2:mem:supportdb

Usuário: sa

Senha: (vazio)

🧪 Endpoints Principais
🔐 Autenticação
Registrar
POST /auth/register

Login
POST /auth/login


Retorno:

{
  "status": "ok",
  "data": {
    "token": "JWT_AQUI",
    "type": "Bearer"
  }
}

🎫 Chamados
Criar Chamado (USER/ADMIN)
POST /tickets
Authorization: Bearer <TOKEN>

Listar Meus Chamados
GET /tickets
Authorization: Bearer <TOKEN>

Listar Todos (ADMIN)
GET /tickets/all
Authorization: Bearer <TOKEN>
