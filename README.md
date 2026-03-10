# 🔐 Auth Module API

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/SpringBoot-Backend-brightgreen)
![JWT](https://img.shields.io/badge/Auth-JWT-blue)
![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-blue)
![Docker](https://img.shields.io/badge/Container-Docker-blue)
![Maven](https://img.shields.io/badge/Build-Maven-red)

A secure authentication system built using **Spring Boot** and **JWT**.
This API provides user registration, login, and protected endpoints using token-based authentication.

## 🚀 Tech Stack

* Java
* Spring Boot
* Spring Security
* JWT Authentication
* PostgreSQL
* Swagger / OpenAPI
* Maven

## 📌 Features

* User Registration
* User Login
* JWT Token Generation
* Token Validation
* Secure Protected Endpoints
* Global Exception Handling
* Request Validation
* Swagger API Documentation

## ⚙️ Setup Instructions

### 1. Clone Repository

git clone https://github.com/codevithkarthik/auth-module.git

### 2. Configure Environment Variables

Set the following variables:

DB_URL=jdbc:postgresql://localhost:5432/auth_module_db

DB_USERNAME=postgres

DB_PASSWORD=yourpassword

JWT_SECRET=your_secret_key

### 3. Run Application

mvn spring-boot:run

## 📖 API Documentation

Swagger UI available at:

https://authentication-module-o7mh.onrender.com/swagger-ui/index.html

## 🔑 Authentication Flow

1. Register user
2. Login to receive JWT token
3. Use token in Authorization header
4. Access protected endpoints

## 📂 Project Structure
```
src
┣ 📂 controller
┣ 📂 service
┣ 📂 repository
┣ 📂 entity
┣ 📂 dto
┣ 📂 enums
┣ 📂 exception
┣ 📂 security
┗ 📂 config
```

## 🌍 Deployment

Application deployed using:

- Docker
- Render Cloud Platform

## 👨‍💻 Author

Karthik Teja Kalluri
