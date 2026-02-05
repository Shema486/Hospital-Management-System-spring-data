package com.hospital.Hms.graphql;


import com.hospital.Hms.dto.request.AppointmentRequest;
import com.hospital.Hms.dto.response.AppointmentResponse;
import com.hospital.Hms.entity.AppointmentStatus;
import com.hospital.Hms.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AppointmentGraphQLController {

    private final AppointmentService appointmentService;

    @QueryMapping
    public List<AppointmentResponse> getAllAppointment() {
        return appointmentService.getAllAppointments();
    }

    @MutationMapping
    public AppointmentResponse addAppointment(@Argument("input") AppointmentRequest input) {
        return appointmentService.addAppointment(input);
    }

    @MutationMapping
    public String deleteAppointment(@Argument Long id) {
         appointmentService.deleteAppointment(id);
         return "appointment deleted successfully" + id;
    }

    @MutationMapping
    public AppointmentResponse updateAppointment(
            @Argument Long id,
            @Argument("input") AppointmentStatus input
    ) {
        return appointmentService.updateStatus(id, input);
    }
}
