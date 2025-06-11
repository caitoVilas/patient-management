package com.pm.patientservice.api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Request model for creating or updating a patient.
 * This class contains fields for the patient's name, email, address, DNI, and birth date.
 * It is used to transfer data from the client to the server.
 *
 * @author caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class PatientRequest implements Serializable {
    private String name;
    private String email;
    private String address;
    private String phone;
    private String dni;
    private LocalDate birthDate; // Assuming this is a string in the format "yyyy-MM-dd"
}
