# Hospital Healthcare Management System (HMS)

A comprehensive Spring Boot application designed to manage hospital operations including patient management, doctor assignments, appointments, medical inventory, prescriptions, and feedback systems.

## Project Overview

The Hospital Healthcare Management System is a modern, enterprise-grade backend application that provides a complete solution for managing hospital operations. It supports REST API endpoints, GraphQL queries, comprehensive audit logging, and performance monitoring through AOP (Aspect-Oriented Programming).

## Technology Stack

- Java 21
- Spring Boot 4.0.2
- Spring Data JPA - Database ORM
- PostgreSQL - Database
- GraphQL - API queries
- OpenAPI 3.0 - API documentation
- Lombok - Code generation
- BCrypt - Password hashing
- Spring AOP - Aspect-oriented programming for logging and performance monitoring
- Spring Cache - In-memory caching
- Spring Validation - Input validation
- Maven - Build tool

## Core Features

1. Patient Management
   - Register and manage patient information
   - Track patient appointments and feedback
   - View patient medical history

2. Doctor Management
   - Register and manage doctor profiles
   - Assign doctors to departments
   - Track doctor specializations and contact information

3. Appointment Management
   - Schedule patient appointments with doctors
   - Track appointment status
   - Manage appointment confirmations and cancellations

4. Medical Inventory
   - Manage medical supplies and equipment
   - Track inventory levels
   - Monitor inventory usage

5. Prescription Management
   - Create and manage prescriptions for patients
   - Track prescription items and dosages
   - Maintain prescription history

6. Department Management
   - Organize hospital departments
   - Assign doctors to departments
   - Manage department operations

7. Patient Feedback
   - Collect patient feedback and ratings
   - Generate feedback reports
   - Improve service quality based on feedback

8. System Users
   - Manage hospital staff and administrators
   - Control user roles and permissions
   - Secure authentication with BCrypt

## Project Structure

```
src/main/java/com/hospital/Hms/
├── HmsApplication.java           # Main Spring Boot application
├── aspect/                        # AOP aspects
│   ├── LoggingAspect.java        # Request/response logging
│   └── PerformanceAspect.java    # Performance monitoring
├── config/                        # Configuration classes
│   └── OpenApiConfig.java        # Swagger/OpenAPI configuration
├── controller/                    # REST API controllers
│   ├── PatientController.java
│   ├── DoctorController.java
│   ├── AppointmentController.java
│   ├── InventoryController.java
│   ├── PrescriptionController.java
│   ├── DepartmentController.java
│   ├── FeedbackController.java
│   ├── SystemUserController.java
│   └── PrescriptionItemController.java
├── dto/                           # Data Transfer Objects
│   ├── request/                   # Request DTOs
│   ├── response/                  # Response DTOs
│   └── update/                    # Update DTOs
├── entity/                        # JPA entities
│   ├── Patient.java
│   ├── Doctor.java
│   ├── Department.java
│   ├── Appointment.java
│   ├── Prescription.java
│   ├── MedicalInventory.java
│   ├── PatientFeedback.java
│   ├── SystemUser.java
│   ├── Gender.java
│   ├── Role.java
│   └── AppointmentStatus.java
├── exception/                     # Custom exceptions
├── graphql/                       # GraphQL resolvers
├── mapper/                        # Entity-DTO mappers
├── repository/                    # Data access layer
└── service/                       # Business logic layer

src/main/resources/
├── application.properties         # Application configuration
├── application-dev.properties    # Development profile
├── application-prod.properties   # Production profile
├── application-test.properties   # Test profile
└── graphql/                       # GraphQL schema files
```

## Prerequisites

- Java 21 or higher
- PostgreSQL database
- Maven 3.6 or higher
- Git

## Installation and Setup

1. Clone the repository:
```bash
git clone <repository-url>
cd Hms
```

2. Configure your database connection in `application-dev.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/hospital_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

3. Build the project:
```bash
mvn clean install
```

## Running the Application

1. Run with Maven:
```bash
mvn spring-boot:run
```

2. The application will start on `http://localhost:8080` by default.

3. Access the API documentation at `http://localhost:8080/swagger-ui.html`

4. Access GraphQL endpoint at `http://localhost:8080/graphql`

## API Documentation

The application provides comprehensive API documentation through OpenAPI 3.0 (Swagger). After starting the application, visit:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- API Documentation: `http://localhost:8080/v3/api-docs`

## API Endpoints Overview

### Patient Management
- `GET /patient` - List all patients
- `POST /patient` - Create new patient
- `GET /patient/{id}` - Get patient details
- `PUT /patient/{id}` - Update patient

### Doctor Management
- `GET /doctors` - List all doctors
- `POST /doctors` - Register doctor
- `PATCH /doctors/update/{id}` - Update doctor

### Appointments
- `GET /api/appointments` - List appointments
- `POST /api/appointments` - Create appointment
- `GET /api/appointments/{id}` - Get appointment details
- `PATCH /api/appointments/{id}/status` - Update appointment status
- `DELETE /api/appointments/{id}` - Cancel appointment

### Medical Inventory
- `GET /api/inventory` - List inventory items
- `POST /api/inventory` - Add inventory item
- `GET /api/inventory/{id}` - Get inventory details
- `PUT /api/inventory/{id}` - Update inventory
- `DELETE /api/inventory/{id}` - Remove inventory item

### Prescriptions
- `GET /prescription` - List prescriptions
- `POST /prescription` - Create prescription
- `GET /prescription/{id}` - Get prescription details
- `PUT /prescription/{id}` - Update prescription

### Feedback
- `GET /api/feedback` - List feedback
- `POST /api/feedback` - Submit feedback
- `GET /api/feedback/{id}` - Get feedback details

### Departments
- `GET /departments` - List departments
- `POST /departments` - Create department
- `GET /departments/{id}` - Get department details

## Key Features Implementation

### Logging and Monitoring
- LoggingAspect logs all incoming requests and outgoing responses
- PerformanceAspect monitors method execution time and logs performance metrics
- Configured using Spring AOP

### Input Validation
- All DTOs use Jakarta validation annotations
- Custom validation rules for business logic
- Automatic validation at controller layer

### Caching
- Spring Cache enabled for improved performance
- Configurable caching strategies for frequently accessed data

### Database
- PostgreSQL for persistent data storage
- Spring Data JPA for database operations
- Support for pagination and sorting

### Security
- BCrypt for password hashing
- Role-based access control through SystemUser and Role entities
- Request validation and error handling

## Database Schema

The application uses the following main entities:

1. Patient - Hospital patients
2. Doctor - Hospital medical staff
3. Department - Hospital departments
4. Appointment - Patient appointments with doctors
5. Prescription - Medicine prescriptions for patients
6. PrescriptionItem - Individual items in prescriptions
7. MedicalInventory - Hospital medical supplies and equipment
8. PatientFeedback - Patient feedback and ratings
9. SystemUser - System users and administrators
10. Role - User roles for access control

## Configuration Profiles

The application supports multiple configuration profiles:

- `dev` - Development environment
- `prod` - Production environment
- `test` - Testing environment

Activate a profile using:
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

## Building for Production

Create a production-ready JAR file:

```bash
mvn clean package
```

Run the JAR file:

```bash
java -jar target/Hms-0.0.1-SNAPSHOT.jar
```

## Troubleshooting

1. Database Connection Issues
   - Ensure PostgreSQL is running
   - Verify database credentials in properties file
   - Check that the database exists

2. Port Already in Use
   - Change server port in application properties: `server.port=8081`

3. Build Issues
   - Clear Maven cache: `mvn clean`
   - Rebuild: `mvn install`

## Future Enhancements

- Email notifications for appointments
- SMS alerts for patients
- Advanced reporting and analytics
- Mobile application support
- Integration with payment systems
- Telemedicine capabilities
- Multilingual support

## License

This project is licensed under the terms specified in the LICENSE file.

## Contact and Support

For issues, questions, or contributions, please contact the development team or submit issues through the project repository.

## Version

Current Version: 0.0.1-SNAPSHOT

Last Updated: 2026
