<div align="center">

# 🎓 Course Master

### A Modern E-Learning Platform built on Microservices Architecture

*Engineering Thesis Project - Full-Stack Application*

![Java](https://img.shields.io/badge/Java_17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_3-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Angular](https://img.shields.io/badge/Angular_14-DD0031?style=for-the-badge&logo=angular&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

</div>

## 📌 About the Project

**DevCourseMaster** is a full-stack, production-inspired e-learning platform that allows users to browse, enroll in, and watch video courses. The project was designed and developed as an Engineering Thesis to demonstrate real-world software engineering practices.

The backend is split into independent **microservices** communicating through an **API Gateway**, with secure authentication handled via **OAuth2 + JWT**. The frontend is a responsive **Single Page Application** built with Angular 14.

---

## 🛠️ Technology Stack

| Layer | Technology |
|---|---|
| **Language** | Java 17, TypeScript |
| **Backend Framework** | Spring Boot 3.x |
| **Gateway** | Spring Cloud Gateway |
| **Security** | Spring Security, OAuth2, JWT |
| **Persistence** | Spring Data JPA, PostgreSQL |
| **Inter-service Comm.** | OpenFeign |
| **API Docs** | Springdoc OpenAPI (Swagger UI) |
| **Frontend** | Angular 14, Angular Material, Bootstrap 5 |
| **Reactive** | RxJS |
| **Build** | Maven, npm |
| **Containerization** | Docker |

---

## 🖼️ Screenshots

**Upload video**

![Dashboard](docs/screenshots/ss-3.PNG)

**Save video with details**

![Dashboard](docs/screenshots/ss-4.PNG)

**Uploaded videos list**

![Dashboard](docs/screenshots/ss-5.PNG)

**Newest videos list**

![Dashboard](docs/screenshots/ss-6.PNG)

**Play the video**

![Dashboard](docs/screenshots/ss-8.PNG)

---

## ✨ Key Features

- 🔐 **Secure Auth** - User registration, login, and token-based authorization via OAuth2 + JWT
- 🎬 **Course & Video Management** - Full CRUD for courses, video content, and thumbnails
- 🚪 **API Gateway** - Single entry point with centralized routing and request filtering
- 📱 **Responsive SPA** - Angular frontend with Material UI and Bootstrap, mobile-friendly
- 📄 **Swagger / OpenAPI 3** - Auto-generated API docs for both backend services
- 🔗 **Inter-service Communication** - Services communicate via OpenFeign HTTP clients
- 🐳 **Dockerized** - Each service ships with its own Dockerfile
---

## 🏗️ System Architecture

```
┌─────────────────────────────────────────┐
│               Angular SPA               │
│            (localhost:4200)             │
└──────────────────┬──────────────────────┘
                   │ HTTP
                   ▼
┌─────────────────────────────────────────┐
│           API Gateway Service           │
│         Spring Cloud Gateway            │
│            (single entry point)         │
└────────────┬────────────────────────────┘
             │                  │
    /auth/**  │                  │  /courses/**
             ▼                  ▼
┌────────────────┐    ┌──────────────────┐
│  Auth Service  │    │  Course Service  │
│  Spring Boot   │    │   Spring Boot    │
│  OAuth2 + JWT  │    │    JPA + CRUD    │
└───────┬────────┘    └────────┬─────────┘
        │                      │
        └──────────┬───────────┘
                   ▼
        ┌──────────────────┐
        │    course_db     │
        │   PostgreSQL     │
        └──────────────────┘
```

 
