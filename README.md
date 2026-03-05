# Spring Boot Java Random User API

<div align="center">

[![Java](https://img.shields.io/badge/Java-25-blue?logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.3-green?logo=spring)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?logo=postgresql)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow)](LICENSE)

A modern Spring Boot REST API that consumes the public [Random User](https://randomuser.me/) API and persists data in a PostgreSQL database. Includes interactive API documentation via **Swagger UI / OpenAPI**.

[Features](#-features) • [Quick Start](#-quick-start) • [API Endpoints](#-api-endpoints) • [Testing](#-testing) • [Architecture](#-architecture)

</div>

---

## 📋 Table of Contents

1. [Features](#-features)
2. [Tech Stack](#-tech-stack)
3. [Prerequisites](#-prerequisites)
4. [Quick Start](#-quick-start)
5. [Configuration](#-configuration)
6. [Running the Project](#-running-the-project)
7. [API Documentation](#-api-documentation)
8. [Testing](#-testing)
9. [Architecture](#-architecture)
10. [Database Schema](#-database-schema)
11. [Monitoring](#-monitoring)
12. [Quality & CI/CD](#-quality--cicd)
13. [Troubleshooting](#-troubleshooting)
14. [Todo](#-todo)

---

## ✨ Features

- ✅ **RESTful API** - Fetch and manage random users
- ✅ **PostgreSQL Integration** - Persist data with Spring Data JDBC
- ✅ **OpenAPI/Swagger** - Interactive API documentation at `/api`
- ✅ **Docker Support** - Easy setup with Docker Compose
- ✅ **Health Monitoring** - Spring Actuator endpoints
- ✅ **Code Quality** - SonarQube integration via GitHub Actions
- ✅ **Test Coverage** - JUnit 5 + Mockito + JaCoCo
- ✅ **BDD Testing** - Cucumber support (in progress)
- ✅ **Environment Management** - dotenv-java for secure configuration

---

## 🛠️ Tech Stack

| Component | Version | Purpose |
|-----------|---------|---------|
| **Java** | 25 | Language |
| **Spring Boot** | 4.0.3 | Framework |
| **Spring Web MVC** | (auto) | REST API |
| **Spring Data JDBC** | (auto) | Database access |
| **Spring Actuator** | (auto) | Monitoring |
| **PostgreSQL** | 15 | Database |
| **H2** | (test) | In-memory DB for tests |
| **springdoc-openapi** | 3.0.1 | OpenAPI/Swagger |
| **dotenv-java** | 5.2.2 | Environment config |
| **Docker Compose** | - | Local infrastructure |
| **Maven** | Wrapper | Build tool |
| **JUnit 5** | (auto) | Testing framework |
| **Mockito** | (auto) | Mocking |
| **JaCoCo** | 0.8.14 | Code coverage |

---

## 📦 Prerequisites

- **Java 25+** (or compatible JDK)
- **Maven 3.8+** (included wrapper available)
- **Docker Desktop** (for PostgreSQL)
- **Git** (for version control)

---

## 🚀 Quick Start

### 1. Clone the repository

```bash
git clone https://github.com/XPEHO/spring_boot_java_random_user.git
cd spring_boot_java_random_user
```

### 2. Set up environment variables

```bash
cp .env.template .env
# Edit .env with your PostgreSQL credentials
```

### 3. Start PostgreSQL

```bash
docker-compose up -d
```

### 4. Run the application

```bash
./mvnw spring-boot:run
```

### 5. Access the API

- **Swagger UI**: http://localhost:8080/api
- **Health Check**: http://localhost:8080/actuator/health

---

## ⚙️ Configuration

### Environment Setup

#### .env file (git-ignored)

```bash
cp .env.template .env
```

**Content:**
```env
POSTGRES_USER=your_user
POSTGRES_PASSWORD=your_password
POSTGRES_DB=your_database
POSTGRES_PORT=5432
```

#### Local Profile (application-local.properties)

For local development with different credentials:

```properties
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
```

Set `SPRING_PROFILES_ACTIVE=local` in your IDE run configuration.

#### Test Profile (application-test.properties)

H2 in-memory database for unit tests (auto-configured, no setup needed).

### Default Configuration

**application.properties** includes:

```properties
spring.application.name=spring_boot_java_random_user
spring.datasource.url=jdbc:postgresql://localhost:${POSTGRES_PORT}/${POSTGRES_DB}
spring.datasource.driver-class-name=org.postgresql.Driver
springdoc.swagger-ui.path=/api
randomuser.api.base-url=https://dummyjson.com/
```

---

## 🏃 Running the Project

### Development Mode

```bash
# Using Maven wrapper (recommended)
./mvnw spring-boot:run

# Or with Maven installed
mvn spring-boot:run
```

### Production Mode

```bash
# Build JAR
./mvnw clean package

# Run JAR
java -jar target/spring_boot_java_random_user-0.0.1-SNAPSHOT.jar
```

### Docker Mode

```bash
# Build Docker image
docker build -t xpeho/spring-boot-random-user .

# Run container
docker run -p 8080:8080 --env-file .env xpeho/spring-boot-random-user
```

Application will be available at: **http://localhost:8080**

---

## 📖 API Documentation

### Interactive Swagger UI

```
http://localhost:8080/api
```

### OpenAPI JSON Specification

```
http://localhost:8080/v3/api-docs
```

### Available Endpoints

#### 🔵 GET /random-users

Fetch and persist random users from the external API.

```http
GET /random-users?count=500
```

**Parameters:**
- `count` (optional): Number of users to fetch (default: 500, max: 5000)

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "gender": "male",
    "firstname": "John",
    "lastname": "Doe",
    "civility": "Mr",
    "email": "john.doe@example.com",
    "phone": "+1 234 567 8900",
    "picture": "https://example.com/pic.jpg",
    "nat": "US"
  }
]
```

#### 🟢 GET /random-users/{id}

Retrieve a specific user by ID.

```http
GET /random-users/1
```

**Response:** `200 OK` or `404 Not Found`

#### 🟡 PUT /random-users/{id}

Update an existing user's information.

```http
PUT /random-users/1
Content-Type: application/json
```

**Request Body:**
```json
{
  "gender": "female",
  "firstname": "Jane",
  "lastname": "Smith",
  "civility": "Ms",
  "email": "jane.smith@example.com",
  "phone": "+1 987 654 3210",
  "picture": "pic.jpg",
  "nat": "FR"
}
```

**Responses:**
- `200 OK` - User updated successfully
- `404 Not Found` - User not found

---

## 🧪 Testing

### Run All Tests

```bash
./mvnw verify
```

Test execution lifecycle:
1. **pre-integration-test** → Docker Compose starts PostgreSQL
2. **integration-test** → Unit and integration tests run
3. **post-integration-test** → Docker Compose stops

### Run Specific Test Suite

```bash
# Unit tests only
./mvnw test

# Integration tests only
./mvnw failsafe:integration-test

# Tests with specific tag (Cucumber)
./mvnw test -Dcucumber.filter.tags="@smoke"
```

### Test Coverage Report

```bash
./mvnw clean verify
```

Generated reports:
- **HTML Report**: `target/site/jacoco/index.html`
- **XML Report**: `target/site/jacoco/jacoco.xml` (used by SonarQube)

### Test Framework Details

- **Framework**: JUnit 5
- **Mocking**: Mockito
- **BDD**: Cucumber (planned)
- **Coverage**: JaCoCo

---

## 🏗️ Architecture

### Project Structure

```
spring_boot_java_random_user/
├── src/
│   ├── main/
│   │   ├── java/com/xpeho/spring_boot_java_random_user/
│   │   │   ├── SpringBootJavaRandomUserApplication.java    ← Entry point
│   │   │   ├── config/                                      ← Spring configuration
│   │   │   ├── data/                                        ← Data layer
│   │   │   │   ├── converters/                              ← DTO/Entity conversion
│   │   │   │   ├── models/                                  ← Data models
│   │   │   │   ├── services/                                ← Data services
│   │   │   │   └── sources/                                 ← API & DB sources
│   │   │   ├── domain/                                      ← Business logic
│   │   │   │   ├── entities/                                ← Domain entities
│   │   │   │   ├── exceptions/                              ← Custom exceptions
│   │   │   │   ├── services/                                ← Business services
│   │   │   │   └── usecases/                                ← Use cases
│   │   │   └── presentation/                                ← API layer
│   │   │       ├── controllers/                             ← REST controllers
│   │   │       └── handlers/                                ← Exception handlers
│   │   └── resources/
│   │       ├── application.properties                       ← Main config
│   │       ├── application-local.properties                 ← Local overrides
│   │       ├── schema.sql                                   ← DB schema
│   │       └── static/                                      ← Static assets
│   └── test/
│       ├── java/                                            ← Test code
│       └── resources/
│           ├── application-test.properties                  ← Test config
│           └── features/                                    ← Cucumber features
├── .github/
│   └── workflows/
│       ├── sonar.yaml                                       ← SonarQube CI/CD
│       └── ISSUE_TEMPLATE/                                  ← GitHub templates
├── docker-compose.yml                                       ← Local infrastructure
├── pom.xml                                                  ← Maven config
├── .env.template                                            ← Env variables template
├── .gitignore                                               ← Git ignore rules
└── README.md                                                ← This file
```

### Layered Architecture

```
Presentation Layer (Controllers)
         ↓
Domain Layer (Use Cases, Services)
         ↓
Data Layer (Repositories, Converters)
         ↓
Database (PostgreSQL)
```

---

## 🗄️ Database Schema

### Users Table

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | SERIAL | PRIMARY KEY | Auto-incremented identifier |
| `gender` | VARCHAR(20) | - | Gender |
| `firstname` | VARCHAR(100) | - | First name |
| `lastname` | VARCHAR(100) | - | Last name |
| `civility` | VARCHAR(20) | - | Title (Mr, Ms, Mrs, etc.) |
| `email` | VARCHAR(255) | - | Email address |
| `phone` | VARCHAR(50) | - | Phone number |
| `picture` | VARCHAR(500) | - | Avatar/picture URL |
| `nat` | VARCHAR(10) | - | Nationality code |

### DDL

Auto-generated on startup via `schema.sql`:

```sql
DROP TABLE IF EXISTS "users";

CREATE TABLE IF NOT EXISTS "users" (
    id SERIAL PRIMARY KEY,
    gender VARCHAR(20),
    firstname VARCHAR(100),
    lastname VARCHAR(100),
    civility VARCHAR(20),
    email VARCHAR(255),
    phone VARCHAR(50),
    picture VARCHAR(500),
    nationality VARCHAR(10)
);
```

---

## 📊 Monitoring

### Health Check

```
http://localhost:8080/actuator/health
```

### Actuator Endpoints

```
http://localhost:8080/actuator
http://localhost:8080/actuator/health
http://localhost:8080/actuator/metrics
http://localhost:8080/actuator/beans
```

### Metrics

- JVM metrics
- HTTP metrics
- Database connection pool metrics

---

## 🔍 Quality & CI/CD

### Local SonarQube Analysis

```bash
./mvnw clean verify sonar:sonar
```

### GitHub Actions Workflows

#### 🟦 SonarQube Analysis (.github/workflows/sonar.yaml)

**Triggers:**
- Push to any branch
- Pull requests

**What it does:**
1. Sets up Java 25
2. Runs Maven tests with code coverage
3. Uploads results to SonarQube
4. Waits for quality gate

**Required Secrets:**
- `SONAR_TOKEN` - SonarQube authentication
- `SONAR_HOST_URL` - SonarQube instance URL
- `POSTGRES_USER` - DB user
- `POSTGRES_PASSWORD` - DB password
- `POSTGRES_DB` - DB name
- `POSTGRES_PORT` - DB port

---

## ❌ Troubleshooting

### DataSource Configuration Error

**Error:**
```
Failed to configure a DataSource: 'url' attribute is not specified
and no embedded datasource could be configured.
```

**Solution:**
Ensure `spring.datasource.url` is set in `application.properties` or environment variables.

### PostgreSQL Connection Failed

**Error:**
```
org.postgresql.util.PSQLException: Connection to localhost:5432 refused
```

**Solution:**
```bash
# Check if Docker is running
docker ps

# Start PostgreSQL
docker-compose up -d

# Verify connection
psql -U postgres -h localhost
```

### Tests Fail with H2

**Error:**
```
Circular placeholder reference in application-test.properties
```

**Solution:**
Use the corrected `application-test.properties` with H2 configuration:
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
```

### Java Version Not Supported

**Error:**
```
error: release version 25 not supported
```

**Solution:**
- Install Java 25 or later
- Update Maven compiler plugin version in `pom.xml`

---

## 🌐 External API

This project integrates with **Random User Generator API**:

- **URL**: https://randomuser.me/api/
- **Documentation**: https://randomuser.me/documentation
- **Features**:
  - Generate random users
  - Filter by nationality
  - Customize response fields

---

## 📈 Future Enhancements

- [ ] [Delete endpoint - DELETE /random-users/{id}](https://github.com/XPEHO/spring_boot_java_random_user/issues/10)
- [ ] [Create endpoint - POST /random-users](https://github.com/XPEHO/spring_boot_java_random_user/issues/11)
- [ ] [Liquibase database migrations](setup-docs/liquibase-guide.md)
- [ ] [Cucumber BDD testing suite](setup-docs/cucumber-guide-english.md)

---

## ✅ Todo

- [x] [Add Sonarqube in the project](https://github.com/XPEHO/spring_boot_java_random_user/issues/2)
- [x] [Add PostgreSQL database with docker](https://github.com/XPEHO/spring_boot_java_random_user/issues/6)
- [x] [Add this endpoint get /user/random](https://github.com/XPEHO/spring_boot_java_random_user/issues/5)
- [x] [Add this endpoint get /user/{id}](https://github.com/XPEHO/spring_boot_java_random_user/issues/8)
- [x] [Add this endpoint put /user/{id}](https://github.com/XPEHO/spring_boot_java_random_user/issues/9)
- [ ] [Add this endpoint delete /user/{id}](https://github.com/XPEHO/spring_boot_java_random_user/issues/10)
- [ ] [Add this endpoint post /user](https://github.com/XPEHO/spring_boot_java_random_user/issues/11)

---

## 👥 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📝 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

---

## 👤 Author

**XPEHO** - [GitHub Organization](https://github.com/XPEHO)

---

## 🔗 Useful Links

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Random User API Docs](https://randomuser.me/documentation)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Docker Documentation](https://docs.docker.com/)
- [SonarQube Documentation](https://docs.sonarqube.org/)

---

<div align="center">

**Made with ❤️ by XPEHO**

⭐ If you found this helpful, please consider starring the repository!

</div>

