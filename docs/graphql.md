# GraphQL and GraphiQL Documentation

## Overview

The application provides GraphQL APIs in parallel with REST APIs.

GraphQL components:

- Controllers: `src/main/java/com/hospital/Hms/graphql`
- Schemas: `src/main/resources/graphql/*.graphqls`
- Endpoint: `/graphql`
- UI (dev profile): `/graphiql`

## How GraphQL Is Organized

Each domain has its own schema/controller pair, for example:

- Patients
- Doctors
- Departments
- Appointments
- Prescriptions
- Prescription items
- Inventory
- Feedback
- System users

Controllers use:

- `@QueryMapping` for read operations
- `@MutationMapping` for write operations

## Run GraphQL and GraphiQL

1. Run in `dev` profile (default):

```bash
mvn spring-boot:run
```

2. Open:

- GraphQL endpoint: `http://localhost:8080/graphql`
- GraphiQL UI: `http://localhost:8080/graphiql`

3. If you run with `prod` or `test`, GraphiQL is disabled by configuration.

## Example Queries and Mutations

### 1) List patients

```graphql
query {
  patients(page: 0, size: 5, search: null) {
    patientId
    fullName
    gender
    phone
  }
}
```

### 2) Get patient by ID

```graphql
query {
  patientById(id: 1) {
    patientId
    fullName
    address
  }
}
```

### 3) Login user (returns JWT)

```graphql
mutation {
  login(input: { username: "admin", password: "admin123" }) {
    userId
    username
    role
    token
  }
}
```

### 4) Add appointment

```graphql
mutation {
  addAppointment(
    input: {
      patientId: 1
      doctorId: 1
      appointmentDate: "2026-02-12T10:00:00"
      reason: "General checkup"
    }
  ) {
    appointmentId
    patientName
    doctorName
    status
  }
}
```

## Security Behavior (Current)

- In `SecurityConfig`, `/graphql/**` and `/graphiql/**` are currently marked as public.
- Some GraphQL operations still call secured service logic and may fail based on business/security constraints.
- For stricter GraphQL security, remove public access in security config and enforce role checks in resolvers/services.

## Common Issues

1. GraphiQL not opening:

- Confirm `spring.graphql.graphiql.enabled=true` in the active profile.
- Use `dev` profile.

2. Schema errors:

- Check names/types in `src/main/resources/graphql/*.graphqls`.
- Ensure controller method names/arguments match schema fields.

3. Runtime field errors:

- Check app logs and `GraphQLExceptionHandler` output for details.
