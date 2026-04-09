# Sistema de Tesouraria (API REST)

![Status](https://img.shields.io/badge/status-em%20desenvolvimento-yellow)  
![Java](https://img.shields.io/badge/Java-21-red?logo=java&logoColor=white)  
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-green?logo=springboot)  
![SQL Server](https://img.shields.io/badge/SQL%20Server-database-blue?logo=microsoftsqlserver)  

**Desenvolvido por [Daniel Chipolesk](https://github.com/chipolesk)**  

API REST para controle financeiro de um departamento interno, inspirada em funcionalidades de um banco digital.  
O projeto tem como foco o **aprendizado e prática com Java + Spring Boot + SQL Server**, servindo como base para estudos e evolução pessoal.  

---

## 🚀 Tecnologias
- **Java 21**  
- **Spring Boot**  
- **Spring Data JPA**  
- **SQL Server**  

---

## 📌 Funcionalidades disponíveis hoje na API
- 👤 **Usuários**: criar, listar, atualizar e remover membros.
- 👥 **Mensalidades**: criar mensalidade, pagar mensalidade, consultar mensalidade específica, listar devedores por mês e listar devedores gerais.
- 🍔 **Cantinas**: criar e consultar cantinas (listagem geral, por mês, por data) e consultar histórico de devedor por nome.
- 💸 **Extras**: criar e consultar lançamentos extras (listagem geral, por mês e por data).

---

## 📡 Endpoints REST disponíveis

### Usuários (`/users`)
- `POST /users`
- `GET /users`
- `PUT /users/{id}`
- `DELETE /users/{name}`

### Mensalidades (`/monthly-fees`)
- `POST /monthly-fees`
- `PUT /monthly-fees/pay`
- `GET /monthly-fees/{username}/{month}`
- `GET /monthly-fees/open/{month}`
- `GET /monthly-fees/debtors`

### Cantinas (`/canteen`)
- `POST /canteen`
- `GET /canteen`
- `GET /canteen/{month}`
- `GET /canteen/date/{date}`
- `GET /canteen/debtors/{nameDebtor}`

### Extras (`/extras`)
- `POST /extras`
- `GET /extras`
- `GET /extras/{month}`
- `GET /extras/date/{date}`

---

## 📅 Futuro do projeto
- Autenticação de administrador (gestão interna).  
- Relatórios visuais com gráficos em frontend.  
- Integração com um frontend simples.  
- Deploy em nuvem.  
