<div align="center">
  <h1>💰 EveryExpense Backend</h1>
  <p>The robust Spring Boot backend powering the EveryExpense application.</p>
</div>

---

## 🚀 Overview
This repository contains the backend service for EveryExpense. It provides a secure, RESTful API built with **Spring Boot**, utilizing **MongoDB** for flexible data storage and **MinIO** for S3-compatible object storage.

## 🛠️ Tech Stack
- **Framework:** Java, Spring Boot 
- **Security:** Spring Security with JWT Authentication
- **Database:** MongoDB
- **Object Storage:** MinIO
- **API Documentation:** OpenAPI / Swagger UI
- **Containerization:** Docker & Docker Compose

## 🐳 Quick Start (Docker Compose)
The easiest way to get the entire stack up and running is by using Docker Compose. This will spin up the backend application, MongoDB, Mongo Express (DB UI), and MinIO.

1. Ensure you have [Docker](https://docs.docker.com/get-docker/) and Docker Compose installed.
2. Run the following command in the root directory:
   ```bash
   docker-compose up --build
   ```

### 📍 Services & Endpoints
Once the containers are running, you can access the services at the following local URLs:

| Service | URL | Credentials (User / Pass) |
| :--- | :--- | :--- |
| **Spring Boot API** | http://localhost:8080 | - |
| **Mongo Express UI** | http://localhost:8081 | `mongoexpressuser` / `mongoexpresspass` |
| **MinIO Console** | http://localhost:9001 | `root` / `some_secret_password` |
| **MinIO API** | http://localhost:9000 | - |

## 💻 Local Development Setup
If you prefer to run the Spring Boot application locally (e.g., from your IDE) while using Docker just for the infrastructural dependencies:

1. **Start the backing services** (MongoDB, Mongo Express, MinIO) in detached mode:
   ```bash
   docker-compose up mongo mongo-express minio -d
   ```
2. **Run the Spring Boot application** using Maven:
   ```bash
   mvn spring-boot:run
   ```
   *Alternatively, you can just run the main application class directly from IntelliJ IDEA or Eclipse.*

## 📚 API Documentation
The API endpoints are fully documented using Swagger UI. 

When the application is running (either via Docker or locally), visit:
👉 **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

---
<div align="center">
  <i>Happy Coding! ✨</i>
</div>
