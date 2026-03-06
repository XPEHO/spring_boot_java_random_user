# Spring Boot Random User API

<div align="center">

[![Java](https://img.shields.io/badge/Java-25-blue?logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.3-green?logo=spring)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?logo=postgresql)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow)](LICENSE)

A REST API built with Spring Boot that fetches random users from [Random User API](https://randomuser.me/) and stores them in PostgreSQL.

**[Quick Start](#-quick-start) • [API Docs](#-api-documentation) • [Architecture](#-architecture)**

</div>

---

## 📦 Prerequisites

- Java 25+
- Docker Desktop
- Maven (wrapper included)

---

## 🚀 Quick Start

```bash
# 1. Clone
git clone https://github.com/XPEHO/spring_boot_java_random_user.git
cd spring_boot_java_random_user

# 2. Configure environment
cp .env.template .env
# Edit .env with your credentials

# 3. Start PostgreSQL
docker-compose up -d

# 4. Run application
./mvnw spring-boot:run
```

**Access:**
- Swagger UI: http://localhost:8080/api
- Health Check: http://localhost:8080/actuator/health

---

## ⚙️ Configuration

### Environment Variables (.env)

```env
POSTGRES_USER=your_user
POSTGRES_PASSWORD=your_password
POSTGRES_DB=your_database
POSTGRES_PORT=5432
```

### Profiles

- **default**: PostgreSQL (production)
- **local**: Custom local settings
- **test**: H2 in-memory database

---

## 📖 API Documentation

**Interactive Docs:** http://localhost:8080/api  
**OpenAPI Spec:** http://localhost:8080/v3/api-docs

### Endpoints

| Method | Path | Description | Status |
|--------|------|-------------|--------|
| `GET` | `/random-users?count=500` | Fetch and save random users | ✅ |
| `GET` | `/random-users/{id}` | Get user by ID | ✅ |
| `POST` | `/random-users` | Create a new user | ✅ |
| `PUT` | `/random-users/{id}` | Update user | ✅ |
| `DELETE` | `/random-users/{id}` | Delete user | ✅ |

### Example Request

```bash
# Fetch 10 random users
curl -X GET "http://localhost:8080/random-users?count=10"

# Get user by ID
curl -X GET "http://localhost:8080/random-users/1"

# Create user
curl -X POST "http://localhost:8080/random-users" \
  -H "Content-Type: application/json" \
  -d '{
    "gender": "female",
    "firstname": "Jane",
    "lastname": "Doe",
    "civility": "Ms",
    "email": "jane@example.com",
    "phone": "0600000000",
    "picture": "pic.jpg",
    "nat": "FR"
  }'

# Update user
curl -X PUT "http://localhost:8080/random-users/1" \
  -H "Content-Type: application/json" \
  -d '{
    "gender": "male",
    "firstname": "John",
    "lastname": "Smith",
    "civility": "Mr",
    "email": "john@example.com",
    "phone": "0611111111",
    "picture": "pic2.jpg",
    "nat": "US"
  }'
```

---

## 🧪 Testing

```bash
# Run all tests with Docker
./mvnw verify

# Unit tests only
./mvnw test

# With coverage report (target/site/jacoco/index.html)
./mvnw clean verify

#cucumber tests
./mvnw test -Dtest=CucumberIntegrationTest
```

**Test Stack:**
- JUnit 5 - Testing framework
- Mockito - Mocking
- JaCoCo - Code coverage
- Cucumber - BDD

---

## 🏗️ Architecture

### Clean Architecture (3 Layers)

```
┌─────────────────────────────────────┐
│     Presentation Layer              │  ← REST Controllers
│  (controllers, handlers)            │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│     Domain Layer                    │  ← Business Logic
│  (usecases, entities, exceptions)   │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│     Data Layer                      │  ← Data Access
│  (repositories, services, sources)  │
└─────────────────────────────────────┘
              ↓
         PostgreSQL
```

### Project Structure

```
src/main/java/com/xpeho/spring_boot_java_random_user/
├── SpringBootJavaRandomUserApplication.java  ← Entry point
├── config/                                    ← Configuration
├── presentation/                              ← REST API Layer
│   ├── controllers/                           ← API interfaces
│   └── handlers/                              ← Implementations
├── domain/                                    ← Business Layer
│   ├── entities/                              ← Domain models
│   ├── exceptions/                            ← Custom exceptions
│   ├── services/                              ← Business services
│   └── usecases/                              ← Use cases
└── data/                                      ← Data Layer
    ├── converters/                            ← DTO/Entity mapping
    ├── models/                                ← Data models
    ├── services/                              ← Data services
    └── sources/                               ← API & DB access
```

---

## 🗄️ Database Schema

### Users Table

| Column | Type | Description |
|--------|------|-------------|
| `id` | SERIAL PRIMARY KEY | Auto-incremented identifier |
| `gender` | VARCHAR(20) | Gender |
| `firstname` | VARCHAR(100) | First name |
| `lastname` | VARCHAR(100) | Last name |
| `civility` | VARCHAR(20) | Title (Mr, Ms, Mrs, etc.) |
| `email` | VARCHAR(255) | Email address |
| `phone` | VARCHAR(50) | Phone number |
| `picture` | VARCHAR(500) | Avatar/picture URL |
| `nat` | VARCHAR(10) | Nationality code |

**Schema auto-created on startup via `schema.sql`**

---

## 📊 Monitoring

### Actuator Endpoints

- Health Check: http://localhost:8080/actuator/health
- All Endpoints: http://localhost:8080/actuator
- Metrics: http://localhost:8080/actuator/metrics

**Available Metrics:** JVM metrics, HTTP metrics, Database connection pool metrics

---

## 🔍 Quality & CI/CD

### SonarQube Analysis

```bash
./mvnw clean verify sonar:sonar
```

### GitHub Actions

**Workflow:** `.github/workflows/sonar.yaml`

**Triggers:** Push to any branch, Pull requests

**Pipeline:**
1. Sets up Java 25
2. Runs Maven tests with code coverage
3. Uploads results to SonarQube
4. Waits for quality gate

**Required Secrets:**
- `SONAR_TOKEN` - SonarQube authentication
- `SONAR_HOST_URL` - SonarQube instance URL
- `POSTGRES_USER`, `POSTGRES_PASSWORD`, `POSTGRES_DB`, `POSTGRES_PORT`

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
