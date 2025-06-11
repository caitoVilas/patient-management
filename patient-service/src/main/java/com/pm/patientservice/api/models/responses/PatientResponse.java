package com.pm.patientservice.api.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Response model for a patient.
 * This class contains fields for the patient's ID, name, email, address, DNI, and birth date.
 * It is used to transfer data from the server to the client.
 *
 * @author caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class PatientResponse implements Serializable {
    private UUID id;
    private String name;
    private String email;
    private String address;
    private String phone;
    private String dni;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthDate;
}
