package com.hospital.Hms.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {

    @Async("taskExecutor")
    public void sendAppointmentNotification(Long appointmentId,String patientName,String doctorName){

        try{
            Thread.sleep(3000);

        }catch (InterruptedException e){
            log.error("Notification sending interrupted for appointmentId: {}", appointmentId, e);
            Thread.currentThread().interrupt();
        }
        log.info("Appointment scheduled for patient '{}' with doctor '{}' (appointmentId: {})", patientName, doctorName, appointmentId);
    }

    @Async("taskExecutor")
    public void sendPrescriptionIssuedNotification(Long prescriptionId,Long appointmentId){
        try{
            Thread.sleep(3000);

        }catch (InterruptedException e){
            log.error("Notification sending interrupted for prescriptionId: {}", prescriptionId, e);
            Thread.currentThread().interrupt();
        }
        log.info("Prescription issued notification -> prescriptionId={}, appointmentId={}",
                prescriptionId, appointmentId);
    }
}
