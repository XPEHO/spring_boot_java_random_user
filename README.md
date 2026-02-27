Project developed by **XPEHO**.

## 👤 Author
- [x] [Add Sonarqube in the project](https://github.com/XPEHO/spring_boot_java_random_user/issues/2)
- [x] [Add PostgreSQL database with docker](https://github.com/XPEHO/spring_boot_java_random_user/issues/6)
- [ ] [Add this endpoint get /user/random](https://github.com/XPEHO/spring_boot_java_random_user/issues/5)
- [ ] [Add this endpoint get /user/{id}](https://github.com/XPEHO/spring_boot_java_random_user/issues/8)
- [ ] [Add this endpoint put /user/{id}](https://github.com/XPEHO/spring_boot_java_random_user/issues/9)
- [ ] [Add this endpoint delete /user/{id}](https://github.com/XPEHO/spring_boot_java_random_user/issues/10)
- [ ] [Add this endpoint post /user](https://github.com/XPEHO/spring_boot_java_random_user/issues/11)
## ✅ Todo
- **URL**: [https://randomuser.me/api/](https://randomuser.me/api/)
- **Documentation**: [https://randomuser.me/documentation](https://randomuser.me/documentation)
This project consumes the public **Random User Generator** API:
## 🌐 External API
    └── java/com/xpeho/spring_boot_java_random_user/
        └── SpringBootJavaRandomUserApplicationTests.java
│       └── application.properties                      # Configuration
│   │   └── SpringBootJavaRandomUserApplication.java   # Entry point
## 📁 Project structure
Generated reports:

- HTML report: `target/site/jacoco/index.html`
- XML report (used by SonarQube): `target/site/jacoco/jacoco.xml`

mvn clean verify sonar:sonar
```

### Generate the JaCoCo coverage report locally

Run:

```bash
./mvnw clean verify
- Java 25 setup (Temurin)
- Maven build + tests + SonarQube analysis:
### What it runs
- `push` on all branches
- `pull_request`
### Workflow triggers
.github/workflows/sonar.yaml
Tests use **Testcontainers** to spin up ephemeral Docker containers for external dependencies (e.g. PostgreSQL).

> **Prerequisite for tests**: Docker must be installed and running.

---

## 🔎 Code Quality (SonarQube)

A GitHub Actions workflow is configured in:
./mvnw test
Run unit and integration tests:
## 🧪 Tests
---

## 📖 API Documentation (Swagger UI)

Once the project is running, the interactive documentation is available at:

```
http://localhost:8080/api
```

The raw OpenAPI specification (JSON) is available at:

```
http://localhost:8080/v3/api-docs
```

---

## 🔍 Monitoring (Actuator)

Spring Boot Actuator is enabled. Health endpoints are available at:

```
http://localhost:8080/actuator
http://localhost:8080/actuator/health
```
./mvnw clean package
java -jar target/spring_boot_java_random_user-0.0.1-SNAPSHOT.jar
### Build and run the JAR
mvn spring-boot:run
### With Maven installed
./mvnw spring-boot:run
### With the Maven Wrapper (recommended)

## Running PostgreSQL with Docker

1. Copy the environment template and adjust values as needed:

   ```bash
   cp .env.template .env
   # then edit .env to set database name, user, password, etc.
## 🚀 Running the project
### Properties to configure

```properties
spring.application.name=spring_boot_java_random_user

# Swagger UI — available at /api
springdoc.swagger-ui.path=/api

# PostgreSQL datasource (add these if you use persistence)
spring.datasource.url=jdbc:postgresql://localhost:5432/<database_name>
spring.datasource.username=<username>
spring.datasource.password=<password>
spring.datasource.driver-class-name=org.postgresql.Driver
```

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
## 📋 Prerequisites

- **Java 25** (or compatible version)
- **Maven** (or use the included `./mvnw` wrapper)
- **PostgreSQL** running (if data persistence is enabled)

---

## ⚙️ Configuration

The main configuration file is located at:

```
src/main/resources/application.properties
```
| Technology           | Version         |
|----------------------|-----------------|
| Java                 | 25              |
| Spring Boot          | 4.0.3           |
| Spring Web MVC       | (managed by Boot) |
| Spring Actuator      | (managed by Boot) |
| springdoc-openapi    | 3.0.1           |
| PostgreSQL (driver)  | (managed by Boot) |
| Testcontainers       | (managed by Boot) |
| Maven                | Wrapper included |
## 🛠️ Tech Stack
Spring Boot Java project that exposes a REST API consuming the public [Random User](https://randomuser.me/) API.
The interactive API documentation is available via **Swagger UI / OpenAPI**.
# spring_boot_java_random_user

Spring Boot Java project that exposes a REST API consuming the public [Random User](https://randomuser.me/) API.
The interactive API documentation is available via **Swagger UI / OpenAPI**.

---

## 🛠️ Tech Stack

| Technology           | Version         |
|----------------------|-----------------|
| Java                 | 25              |
| Spring Boot          | 4.0.3           |
| Spring Web MVC       | (managed by Boot) |
| Spring Actuator      | (managed by Boot) |
| springdoc-openapi    | 3.0.1           |
| PostgreSQL (driver)  | (managed by Boot) |
| Testcontainers       | (managed by Boot) |
| Maven                | Wrapper included |

---

## 📋 Prerequisites

- **Java 25** (or compatible version)
- **Maven** (or use the included `./mvnw` wrapper)
- **PostgreSQL** running (if data persistence is enabled)

---

## ⚙️ Configuration

The main configuration file is located at:

```
src/main/resources/application.properties
```

### Properties to configure

```properties
spring.application.name=spring_boot_java_random_user

# Swagger UI — available at /api
springdoc.swagger-ui.path=/api

# PostgreSQL datasource (add these if you use persistence)
spring.datasource.url=jdbc:postgresql://localhost:5432/<database_name>
spring.datasource.username=<username>
spring.datasource.password=<password>
spring.datasource.driver-class-name=org.postgresql.Driver
```

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

---

## 📖 API Documentation (Swagger UI)

Once the project is running, the interactive documentation is available at:

```
http://localhost:8080/api
```

The raw OpenAPI specification (JSON) is available at:

```
http://localhost:8080/v3/api-docs
```

---

## 🔍 Monitoring (Actuator)

Spring Boot Actuator is enabled. Health endpoints are available at:

```
http://localhost:8080/actuator
http://localhost:8080/actuator/health
```

---

## 🧪 Tests

Run unit and integration tests:

```bash
./mvnw test
```

Tests use **Testcontainers** to spin up ephemeral Docker containers for external dependencies (e.g. PostgreSQL).

> **Prerequisite for tests**: Docker must be installed and running.

---

## 🔎 Code Quality (SonarQube)

A GitHub Actions workflow is configured in:

```bash
.github/workflows/sonar.yaml
```

### Workflow triggers

- `push` on all branches
- `pull_request`

### What it runs

- Java 25 setup (Temurin)
- Maven build + tests + SonarQube analysis:

```bash
mvn clean verify sonar:sonar
```

The CI command waits for the Quality Gate result:

```bash
./mvnw clean verify sonar:sonar -Dsonar.qualitygate.wait=true
```

Before running tests, the workflow recreates:

```bash
src/test/resources/application-test.properties
```

from a Base64-encoded GitHub secret.

### Required GitHub Secrets

- `SONAR_TOKEN`
- `SONAR_HOST_URL`
- `APPLICATION_TEST_PROPERTIES` (Base64-encoded `application-test.properties` content)

> `GITHUB_TOKEN` is provided automatically by GitHub Actions.

### Generate the JaCoCo coverage report locally

Run:

```bash
./mvnw clean verify
```

Generated reports:

- HTML report: `target/site/jacoco/index.html`
- XML report (used by SonarQube): `target/site/jacoco/jacoco.xml`


---

## 📁 Project structure

```
src/
├── main/
│   ├── java/com/xpeho/spring_boot_java_random_user/
│   │   └── SpringBootJavaRandomUserApplication.java   # Entry point
│   └── resources/
│       └── application.properties                      # Configuration
└── test/
    └── java/com/xpeho/spring_boot_java_random_user/
        └── SpringBootJavaRandomUserApplicationTests.java
```

---

## 🌐 External API

This project consumes the public **Random User Generator** API:

- **URL**: [https://randomuser.me/api/](https://randomuser.me/api/)
- **Documentation**: [https://randomuser.me/documentation](https://randomuser.me/documentation)

---

## ✅ Todo

- [x] [Add Sonarqube in the project](https://github.com/XPEHO/spring_boot_java_random_user/issues/2)
- [ ] [Add PostgreSQL database with docker](https://github.com/XPEHO/spring_boot_java_random_user/issues/6)
- [ ] [Add this endpoint get /user/random](https://github.com/XPEHO/spring_boot_java_random_user/issues/5)
- [ ] [Add this endpoint get /user/{id}](https://github.com/XPEHO/spring_boot_java_random_user/issues/8)
- [ ] [Add this endpoint put /user/{id}](https://github.com/XPEHO/spring_boot_java_random_user/issues/9)
- [ ] [Add this endpoint delete /user/{id}](https://github.com/XPEHO/spring_boot_java_random_user/issues/10)
- [ ] [Add this endpoint post /user](https://github.com/XPEHO/spring_boot_java_random_user/issues/11)

---

## 👤 Author

Project developed by **XPEHO**.
