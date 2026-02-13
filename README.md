# Hospital Healthcare Management System (HMS)

A web-based hospital management backend built with Spring Boot and PostgreSQL.  
The system is structured for enterprise-style development with layered architecture, REST and GraphQL APIs, centralized exception handling, validation, role-based access control, JWT authentication, OAuth2 login, and AOP-based monitoring.

## System Architecture

The project follows a layered architecture to keep concerns separated and maintainable:

- Presentation Layer:
  REST controllers in `controller/` and GraphQL controllers in `graphql/`.
- Service Layer:
  Business logic, transaction boundaries, and orchestration in `service/`.
- Data Access Layer:
  Persistence through Spring Data JPA repositories in `repository/`.
- Cross-Cutting Concerns:
  Logging and performance monitoring in `aspect/`, plus centralized error handling in `exception/`.
- Domain Layer:
  Core entities and enums in `entity/`.

## Architectural Concepts

### 1. How Spring Wires the Application (Dependency Injection)

Spring manages bean lifecycle and wiring. The codebase primarily uses constructor injection.

Benefits in this project:

- Clear dependencies between layers
- Better testability
- Safer immutability patterns

### 2. Layered MVC + Service Pattern

- Controllers expose API endpoints and validate input
- Services enforce domain/business rules
- Repositories abstract persistence logic

### 3. Aspect-Oriented Programming (AOP)

Two aspects are currently implemented:

- `LoggingAspect`:
  Surrounds service methods and logs start/success/error details.
- `PerformanceAspect`:
  Measures execution time for controller, GraphQL, and service methods.

Read full AOP guide: `docs/aop.md`

### 4. GraphQL Integration

GraphQL is implemented alongside REST to support flexible data retrieval and mutation workflows.

- Schemas are in: `src/main/resources/graphql`
- Resolver/controllers are in: `src/main/java/com/hospital/Hms/graphql`

Read full GraphQL and GraphiQL guide: `docs/graphql.md`

## Features

### Hybrid API Support

- REST APIs for hospital modules
- GraphQL for flexible querying and mutations

### Security

- JWT authentication via `POST /user/login`
- Role-based authorization with `@PreAuthorize`
- OAuth2 login (Google client) with JWT issuance on success

### Quality and Monitoring

- Validation using Jakarta Bean Validation
- Global exception handling for REST and GraphQL
- AOP logging and performance monitoring
- Swagger/OpenAPI for API exploration

### Core Domain Modules

- System Users
- Patients
- Doctors
- Departments
- Appointments
- Prescriptions and Prescription Items
- Medical Inventory
- Patient Feedback

## Project Structure

```text
.
|- docs/
|  |- aop.md                          AOP documentation and run/verify guide
|  |- graphql.md                      GraphQL and GraphiQL documentation and run/verify guide
|- src/main/java/com/hospital/Hms/
|  |- HmsApplication.java             Spring Boot entry point
|  |- aspect/                         AOP aspects (logging, performance)
|  |- Config/                         OpenAPI configuration
|  |- controller/                     REST controllers
|  |- dto/                            Request/response/update DTOs
|  |- entity/                         Domain entities and enums
|  |- exception/                      Global exception handlers
|  |- graphql/                        GraphQL controllers (queries/mutations)
|  |- mapper/                         DTO/entity mapping helpers
|  |- repository/                     Spring Data JPA repositories
|  |- security/                       JWT, OAuth2 success handler, filter, config
|  |- service/                        Business logic
|- src/main/resources/
|  |- graphql/                        GraphQL schema files (*.graphqls)
|  |- application.properties          Base config (default active profile)
|  |- application-dev.properties      Dev profile config
|  |- application-prod.properties     Prod profile config
|  |- application-test.properties     Test profile config
|- pom.xml                            Maven configuration
```

## Tech Stack and Dependencies

### Core Technologies

- Framework: Spring Boot 4.0.2
- Language: Java 21
- Database: PostgreSQL
- API: REST + GraphQL
- Security: Spring Security, JWT, OAuth2 (Google)

### Key Dependencies

- Spring Data JPA
- Spring Web MVC
- Spring GraphQL
- Spring Validation
- Spring AOP
- Spring Cache
- Spring Security OAuth2 Client
- JJWT (`io.jsonwebtoken`)
- Springdoc OpenAPI
- Lombok

## Configuration and Profiles

Profiles available:

- `dev` (default):
  Swagger and GraphiQL enabled.
- `prod`:
  API docs disabled, GraphiQL disabled.
- `test`:
  API docs disabled, GraphiQL disabled.

Run with profile:

```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=prod"
```

## Setup and Installation

### 1. Prerequisites

- Java 21+
- PostgreSQL running locally or remotely
- Maven 3.6+

### 2. Environment Configuration

Create/update `.env` in the project root:

```env
DB_HOST=jdbc:postgresql://localhost:5432/hospital_db
DB_USER=postgres
DB_PASSWORD=your_password
SECRET_KEY=your_32_plus_char_secret
DURATION_EXPIRATION=1800000
CLIENT_ID=your_google_oauth_client_id
CLIENT_SECRET=your_google_oauth_client_secret
```

### 3. Build and Run

```bash
mvn clean install
mvn spring-boot:run
```

### 4. Useful URLs (dev profile)

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- GraphQL endpoint: `http://localhost:8080/graphql`
- GraphiQL: `http://localhost:8080/graphiql`

## Main Endpoint Groups

- `/user/*`
- `/patient/*`
- `/doctors/*`
- `/department/*`
- `/api/appointments/*`
- `/api/prescriptions/*`
- `/api/prescription-items/*`
- `/api/inventory/*`
- `/api/feedback/*`

## Authentication Quick Start

1. Login:

```http
POST /user/login
Content-Type: application/json

{
  "username": "your_username",
  "password": "your_password"
}
```

2. Use returned token:

```http
Authorization: Bearer <jwt_token>
```

## Role Access Guide

### `ADMIN`

- Can do: Full access across users, patients, doctors, departments, appointments, prescriptions, inventory, and feedback.
- Cannot do: No functional restrictions in current role rules.

### `DOCTOR`

- Can do: View patients/doctors/departments/appointments, update appointment status, create prescriptions, delete prescriptions, create prescription items, read inventory.
- Cannot do: Create system users, manage inventory write operations, create/deactivate patients, create/deactivate departments, create/deactivate appointments (unless admin/reception role is also granted).

### `NURSE`

- Can do: View patients/doctors/departments/appointments, read inventory, create/view feedback, read prescription details.
- Cannot do: Create users, create doctors, manage departments, manage inventory writes, create/delete prescriptions, create prescription items, change appointment status.

### `RECEPTIONIST`

- Can do: Create/update/deactivate patients, create/delete appointments, view patients/doctors/departments, update permitted user profile endpoints.
- Cannot do: Create users, create doctors, manage inventory, manage prescriptions, change appointment status, create feedback as nurse/admin routes.

## Documentation Files

- AOP: `docs/aop.md`
- GraphQL and GraphiQL: `docs/graphql.md`

Quick links:

- [AOP Guide](docs/aop.md)
- [GraphQL and GraphiQL Guide](docs/graphql.md)

## Version

`0.0.1-SNAPSHOT`
