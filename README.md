GS SOA & WebServices – Support System (Gestão de Chamados)
Integrantes

Fabio Hideki Kamikihara – RM550610

Eduardo Osorio – RM550161

Descrição do Projeto

Este projeto consiste em uma API REST para gestão de chamados de suporte (HelpDesk).
Ele permite registrar usuários, realizar login, criar chamados, alterar status e controlar permissões de acordo com o perfil de cada usuário.

A API foi construída seguindo os critérios da disciplina de SOA & WebServices, incluindo:

Autenticação JWT

Autorização por perfis (USER / ADMIN)

Política de sessão STATELESS

Entities, DTOs, VOs e Enums

Organização modular

Tratamento global de exceções

Padrão de resposta padronizado

O sistema é simples, direto e funcional, atendendo exatamente os requisitos solicitados na GS.

Tecnologias Utilizadas

Java 17

Spring Boot 3

Spring Web

Spring Data JPA

Spring Security

JWT (JJwt)

H2 Database (em memória — não precisa MySQL)

Maven

Modelagem
Entidade User

id (Long)

name (String)

email (String, único)

password (String, hash)

role (ADMIN / USER)

contactInfo (VO: phone, department)

createdAt (LocalDateTime)

Entidade Ticket

id (Long)

title

description

status (OPEN / IN_PROGRESS / CLOSED)

priority (LOW / MEDIUM / HIGH)

createdAt / updatedAt

createdBy (User)

assignedTo (User, opcional)

Value Object

ContactInfoVO → phone, department

Enums

Role

TicketStatus

Priority

Endpoints Principais
🔐 Autenticação
Registrar usuário
POST /auth/register


Exemplo:

{
  "name": "Admin",
  "email": "admin@teste.com",
  "password": "123456",
  "role": "ADMIN"
}

Login
POST /auth/login


Exemplo:

{
  "email": "admin@teste.com",
  "password": "123456"
}


Retorno:

{
  "status": "ok",
  "message": "Login realizado com sucesso",
  "data": {
    "token": "jwt_aqui",
    "type": "Bearer"
  }
}

🎫 Chamados (Tickets)
Criar chamado
POST /tickets
Authorization: Bearer <TOKEN>

Listar chamados do usuário
GET /tickets
Authorization: Bearer <TOKEN>

Listar todos os chamados (ADMIN)
GET /tickets/all
Authorization: Bearer <TOKEN_ADMIN>

Atualizar chamado
PUT /tickets/{id}

Alterar status (ADMIN)
PUT /tickets/{id}/status

Atribuir chamado (ADMIN)
PUT /tickets/{id}/assign/{userId}

Padrão de Resposta

Todas as respostas seguem este formato:

{
  "status": "ok" | "error",
  "message": "Mensagem",
  "data": { }
}

Como Rodar o Projeto
1. Pré-requisitos

Java 17

Maven

(Banco não precisa instalar, usa H2)

2. Rodar a API

Dentro da pasta do projeto:

mvn spring-boot:run


A aplicação irá iniciar em:

http://localhost:8080

3. Console do H2 (opcional)
http://localhost:8080/h2-console


Config padrão:

JDBC URL: jdbc:h2:mem:supportdb

User: sa

Password: (vazio)

Fluxo de Teste Rápido
1. Registrar ADMIN

email: admin@teste.com

2. Registrar USER

email: user@teste.com

3. Fazer login com ambos

copiar o token JWT

4. Como USER

Criar e visualizar chamados

5. Como ADMIN

Ver todos, alterar status e atribuir chamados

✔ Critérios da GS atendidos
✓ Entities, DTOs, VOs e Enums

Presentes e organizados no projeto.

✓ ResponseEntity em todos os endpoints

Todas as respostas são padronizadas.

✓ Tratamento global de exceções

GlobalExceptionHandler implementado.

✓ Autenticação de usuário

Login com JWT.

✓ Autorização por perfil (USER/ADMIN)

Endpoints protegidos e validados.

✓ Sessão STATELESS com JWT

SessionCreationPolicy.STATELESS.

✓ Casos de uso separados em Services

Regras de negócio isoladas.

✓ Organização modular e reutilizável

Pacotes bem divididos seguindo SOA.