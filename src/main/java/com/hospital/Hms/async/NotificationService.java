package com.hospital.Hms.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class NotificationService {

    @Async("taskExecutor")
    public CompletableFuture<Void> sendAppointmentNotification(Long appointmentId, String patientName, String doctorName) {
        try {
            Thread.sleep(3000);
            log.info("Appointment scheduled for patient '{}' with doctor '{}' (appointmentId: {})", patientName, doctorName, appointmentId);
            return CompletableFuture.completedFuture(null);

        } catch (InterruptedException e) {
            log.error("Notification sending interrupted for appointmentId: {}", appointmentId, e);
            Thread.currentThread().interrupt();
            return CompletableFuture.failedFuture(e);
        } catch (Exception ex) {
            log.error("Failed to send appointment notification for appointmentId: {}", appointmentId, ex);
            return CompletableFuture.failedFuture(ex);
        }
    }

    @Async("taskExecutor")
    public CompletableFuture<Void> sendPrescriptionIssuedNotification(Long prescriptionId, Long appointmentId) {
        try {
            Thread.sleep(3000);
            log.info("Prescription issued notification -> prescriptionId={}, appointmentId={}",
                    prescriptionId, appointmentId);
            return CompletableFuture.completedFuture(null);

        } catch (InterruptedException e) {
            log.error("Notification sending interrupted for prescriptionId: {}", prescriptionId, e);
            Thread.currentThread().interrupt();
            return CompletableFuture.failedFuture(e);
        } catch (Exception ex) {
            log.error("Failed to send prescription notification for prescriptionId: {}", prescriptionId, ex);
            return CompletableFuture.failedFuture(ex);
        }
    }
}
