# spring_boot_java_random_user

Spring Boot REST API that consumes the public [Random User](https://randomuser.me/) API and persists data in a PostgreSQL database.
Interactive API documentation is available via **Swagger UI / OpenAPI**.

---

## 🛠️ Tech Stack

| Technology | Version |
|---|---|
| Java | 25 |
| Spring Boot | 4.0.3 |
| Spring Web MVC | (managed by Boot) |
| Spring Data JDBC | (managed by Boot) |
| Spring Actuator | (managed by Boot) |
| springdoc-openapi | 3.0.1 |
| PostgreSQL | 15 |
| dotenv-java | 5.2.2 |
| Docker / Docker Compose | - |
| Maven | Wrapper included |

---

## 📋 Prerequisites

- **Java 25+**
- **Maven** (or use the included `./mvnw` wrapper)
- **Docker Desktop**

---

## ⚙️ Configuration

### 1. Environment variables

Copy the template and fill in the values:

```bash
cp .env.template .env
```

`.env` content:

```env
POSTGRES_USER={POSTGRES_USER}
POSTGRES_PASSWORD={POSTGRES_PASSWORD}
POSTGRES_DB={POSTGRES_DB}
POSTGRES_PORT={POSTGRES_PORT}
```

> ⚠️ The `.env` file is git-ignored. Never commit it.

### 2. Test properties

```bash
cp src/test/resources/application-test.properties.template src/test/resources/application-test.properties
```

> ⚠️ This file is also git-ignored.
But for credentials and local overrides, use:

```
src/main/resources/application-local.properties
```

### Example for application.properties

```properties
spring.application.name=spring_boot_java_random_user
springdoc.swagger-ui.path=/api
spring.datasource.url=jdbc:postgresql://localhost:5432/<database_name>
spring.datasource.driver-class-name=org.postgresql.Driver
```

### Local credentials & security

To avoid exposing database credentials in source code, create a `.env` file at the project root.

Then, in `src/main/resources/application-local.properties`:

```properties
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
```

Both `.env` and `application-local.properties` are in `.gitignore` (already set).

To activate the local profile in IntelliJ or VS Code, add this environment variable to your run configuration:

```
SPRING_PROFILES_ACTIVE=local
```

This way, each developer can use their own credentials without risk of leaking them to GitHub.


> ⚠️ **Common startup error**:
> ```
> Failed to configure a DataSource: 'url' attribute is not specified
> and no embedded datasource could be configured.
> Reason: Failed to determine a suitable driver class
> ```
> This error occurs because the PostgreSQL driver is present in the dependencies but the datasource URL is not configured.
> **Fix**: either add the `spring.datasource.*` properties above, or exclude the DataSource auto-configuration if no database is needed:
> ```properties
> spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
> ```

---

## 🚀 Running the project

### Start the database

```bash
docker-compose up -d
```

### With the Maven Wrapper (recommended)

```bash
./mvnw spring-boot:run
```

### With Maven installed

```bash
mvn spring-boot:run
```

### Build and run the JAR

```bash
./mvnw clean package
java -jar target/spring_boot_java_random_user-0.0.1-SNAPSHOT.jar
```

The application will be available at: http://localhost:8080

---

## 📖 API Documentation (Swagger UI)

```
http://localhost:8080/api
```

Raw OpenAPI specification (JSON):

```
http://localhost:8080/v3/api-docs
```

---

## 🔍 Monitoring (Actuator)

```
http://localhost:8080/actuator
http://localhost:8080/actuator/health
```

---

## 🧪 Tests

The Maven plugin starts and stops Docker Compose automatically during tests:

```bash
./mvnw verify
```

Execution cycle:
1. `pre-integration-test` → `docker-compose up` (PostgreSQL starts)
2. `integration-test` → tests run
3. `post-integration-test` → `docker-compose down` (PostgreSQL stops)

> **Prerequisite**: Docker must be installed and running.

---

## 🔎 Code Quality (SonarQube)

A GitHub Actions workflow is configured in `.github/workflows/sonar.yaml`.

### Workflow triggers

- `push` on all branches
- `pull_request`

### What it runs

- Java 25 setup (Temurin)
- Maven build + tests + SonarQube analysis:

```bash
./mvnw clean verify sonar:sonar -Dsonar.qualitygate.wait=true
```

### Required GitHub Secrets

| Secret | Description |
| `APPLICATION_TEST_PROPERTIES` (Base64-encoded `application-test.properties` content)
| `SONAR_TOKEN` | SonarQube authentication token |
| `SONAR_HOST_URL` | SonarQube instance URL |
| `POSTGRES_USER` | PostgreSQL user |
| `POSTGRES_PASSWORD` | PostgreSQL password |
| `POSTGRES_DB` | Database name |
| `POSTGRES_PORT` | PostgreSQL port |

> `GITHUB_TOKEN` is provided automatically by GitHub Actions.

### JaCoCo coverage report (local)

```bash
./mvnw clean verify
```

Generated reports:
- HTML: `target/site/jacoco/index.html`
- XML (used by SonarQube): `target/site/jacoco/jacoco.xml`

---

## 📁 Project structure

```
src/
├── main/
│   ├── java/com/xpeho/spring_boot_java_random_user/
│   │   └── SpringBootJavaRandomUserApplication.java  ← Entry point, loads .env
│   └── resources/
│       ├── application.properties                    ← Spring configuration
│       └── schema.sql                                ← Database schema
└── test/
    ├── java/com/xpeho/spring_boot_java_random_user/
    │   └── SpringBootJavaRandomUserApplicationTests.java
    └── resources/
        ├── application-test.properties               ← Test config (git-ignored)
        └── application-test.properties.template      ← Template to copy
.env                                                  ← Environment variables (git-ignored)
.env.template                                         ← Template to copy
docker-compose.yml                                    ← Local PostgreSQL
```

---

## 🗄️ Database

### Schema

The `user` table is created automatically on startup via `schema.sql`:

| Column | Type | Description |
|---|---|---|
| `id` | SERIAL PK | Auto-incremented identifier |
| `gender` | VARCHAR(20) | Gender |
| `firstname` | VARCHAR(100) | First name (`name.first`) |
| `lastname` | VARCHAR(100) | Last name (`name.last`) |
| `civility` | VARCHAR(20) | Title (`name.title`) |
| `email` | VARCHAR(255) | Email address |
| `phone` | VARCHAR(50) | Phone number |
| `picture` | VARCHAR(500) | Medium picture URL |
| `nationality` | VARCHAR(10) | Nationality |

---

## 🌐 External API

This project consumes the public **Random User Generator** API:

- **URL**: [https://randomuser.me/api/](https://randomuser.me/api/)
- **Documentation**: [https://randomuser.me/documentation](https://randomuser.me/documentation)

---

## ✅ Todo

- [x] [Add Sonarqube in the project](https://github.com/XPEHO/spring_boot_java_random_user/issues/2)
- [x] [Add PostgreSQL database with docker](https://github.com/XPEHO/spring_boot_java_random_user/issues/6)
- [X] [Add this endpoint get /user/random](https://github.com/XPEHO/spring_boot_java_random_user/issues/5)
- [ ] [Add this endpoint get /user/{id}](https://github.com/XPEHO/spring_boot_java_random_user/issues/8)
- [ ] [Add this endpoint put /user/{id}](https://github.com/XPEHO/spring_boot_java_random_user/issues/9)
- [ ] [Add this endpoint delete /user/{id}](https://github.com/XPEHO/spring_boot_java_random_user/issues/10)
- [ ] [Add this endpoint post /user](https://github.com/XPEHO/spring_boot_java_random_user/issues/11)

---

## 👤 Author

Project developed by **XPEHO**.
