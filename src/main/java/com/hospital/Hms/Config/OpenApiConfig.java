package com.shema.Hospital_managment_system_Spring.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "HOSPITAL MANAGEMENT SYSTEM",
                version = "1.0",
                description = """
                        REST API for managing hospital operations including:
                        - Departments
                        - Doctors
                        - Patients
                        - Appointments
                        - Prescriptions
                        - Prescription Items (Medicines)

                        This API enforces business rules such as:
                        - Prevent deleting departments with doctors
                        - Prevent deleting items used in prescriptions
                        - Prevent deleting appointments with prescriptions
                        """,
                contact = @Contact(
                        name = "Shema Alphonse",
                        email = "support@hospital-system.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                )
        )
)
@Configuration
public class OpenApiConfig {
}
