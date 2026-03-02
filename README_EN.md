# 💪 Gym Flow — Strength Progress Tracker

A Web application to track daily workout loads, select exercises based on muscle groups, and visualize your strength progress over time with clear and intuitive charts. Perfect for users who want to document their gym journey and follow their training evolution closely.

---

## 🧱 Project Structure

```
app-fitness/
├── backend/         → REST API built with Java (Spring Boot)
├── frontend/        → Web app built with TypeScript (Angular)
├── containers/      → Infrastructure (Docker Compose: PostgreSQL + MinIO)
└── README.md        → Project documentation
```

---

## 🚀 Features

- **OAuth2 authentication** with optional future support for social login
- **Daily load logging** for each exercise
- **Exercise selection** by muscle group (MuscleWiki-style interface)
- **Image and video upload** linked to each exercise (S3-compatible)
- **Progress graphs** to visualize strength evolution

---

## 🧰 Technologies Used

| Layer          | Technology              |
| -------------- | ----------------------- |
| Frontend       | Angular                 |
| Backend        | Java + Spring Boot      |
| Database       | PostgreSQL              |
| Storage        | MinIO (S3-compatible)   |
| Infrastructure | Docker + Docker Compose |

---

## 🔧 How to Run Locally

### Requirements

- [Docker](https://www.docker.com/) and [Docker Compose](https://docs.docker.com/compose/)
- Java 21+ (for backend)
- Node.js 18+ (for frontend)
- Android emulator or physical device for testing

---

### Start Containers

1. Navigate to the `containers` folder:
   ```bash
   cd containers
   ```
2. Start services with:
   ```bash
   docker-compose up -d
   ```

Services available:

- **PostgreSQL:** `localhost:5432`
- **MinIO API (S3):** `http://localhost:9000`
- **MinIO Web UI:** `http://localhost:9001`

---

### Run the Backend

1. Navigate to the `backend` folder:
   ```bash
   cd ../backend
   ```
2. Run the app (example using Maven):
   ```bash
   ./mvnw spring-boot:run
   ```

---

### Run the Frontend

1. Navigate to the `frontend` folder:
   ```bash
   cd ../frontend
   ```
2. Install dependencies and start the app:
   ```bash
   npm install
   npm start
   ```

---

## 📁 Environment Variables

### containers/.env

```env
# PostgreSQL
POSTGRES_DB=fitnessapp
POSTGRES_USER=admin
POSTGRES_PASSWORD=admin123

# MinIO
MINIO_ROOT_USER=admin
MINIO_ROOT_PASSWORD=admin123
```

### backend/.env or application.yml (example)

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/${POSTGRES_DB}
    username: ${POSTGRES_USER}
    password: ${POSTGRES_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect

minio:
  endpoint: http://localhost:9000
  access-key: ${MINIO_ROOT_USER}
  secret-key: ${MINIO_ROOT_PASSWORD}
```

---

## 📊 Project Progress

- [x] Project structure defined
- [x] Container setup with PostgreSQL + MinIO
- [x] OAuth2 authentication and token-based security
- [x] Database modeling and relationships
- [x] Image upload endpoint implementation
- [ ] Exercise selection UI with images/videos
- [ ] Graphs and statistics for training history

---

## 👨‍💻 Author

Developed with 💪 by [Your Name](https://github.com/yourusername)

---

## 📄 License

This project is free to use for personal learning and educational purposes.
