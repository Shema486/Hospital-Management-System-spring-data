# AOP Documentation

## Overview

This project uses Spring AOP to implement cross-cutting concerns without duplicating logic in controllers/services.

Package:

- `src/main/java/com/hospital/Hms/aspect`

Implemented aspects:

- `LoggingAspect`
- `PerformanceAspect`

## 1) LoggingAspect

File:

- `src/main/java/com/hospital/Hms/aspect/LoggingAspect.java`

Pointcut:

- `execution(* com.hospital.Hms.service..*(..))`

Behavior:

- Logs method start for service methods
- Logs successful completion and duration
- Logs exception details if a service method fails

Purpose:

- Improve observability of business operations
- Make debugging service failures easier

## 2) PerformanceAspect

File:

- `src/main/java/com/hospital/Hms/aspect/PerformanceAspect.java`

Pointcuts:

- Controllers + GraphQL:
  `execution(* com.hospital.Hms.graphql..*(..)) || execution(* com.hospital.Hms.controller..*(..))`
- Services:
  `execution(* com.hospital.Hms.service..*(..))`

Behavior:

- Measures execution time for targeted methods
- Logs runtime in milliseconds

Purpose:

- Monitor request processing cost
- Identify potential performance bottlenecks

## How to Run and Verify AOP

1. Start the app:

```bash
mvn spring-boot:run
```

2. Trigger some API calls (REST or GraphQL).

Example REST:

```bash
curl -X GET http://localhost:8080/api/appointments \
  -H "Authorization: Bearer <token>"
```

3. Check console logs. You should see:

- Service start/success/error messages from `LoggingAspect`
- Execution-time messages from `PerformanceAspect`

## Notes

- AOP is active because `spring-boot-starter-aop` is included in `pom.xml`.
- No additional manual configuration is required for these two aspects.
