package com.pm.patientservice.api.controllers.contracts;

import com.pm.patientservice.api.models.requests.PatientRequest;
import com.pm.patientservice.api.models.responses.PatientResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Interface for the PatientController.
 * This interface defines the contract for patient-related operations.
 *
 * @author caito
 *
 */
public interface PatientController {

    @PostMapping("/create")
    @SecurityRequirement(name = "security token")
    public ResponseEntity<?> createPatient(@RequestBody PatientRequest request);

    @GetMapping("/id/{id}")
    @SecurityRequirement(name = "security token")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable UUID id);

    @GetMapping
    @SecurityRequirement(name = "security token")
    public ResponseEntity<Page<PatientResponse>> getAllPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size);

    @GetMapping("/email/{email}")
    @SecurityRequirement(name = "security token")
    public ResponseEntity<PatientResponse> getPatientByEmail(@PathVariable String email);

    @GetMapping("/dni/{dni}")
    @SecurityRequirement(name = "security token")
    public ResponseEntity<PatientResponse> getPatientByDni(@PathVariable String dni);

    @GetMapping("/name/{name}")
    @SecurityRequirement(name = "security token")
    public ResponseEntity<List<PatientResponse>> getPatientsByName(@PathVariable String name);

    @DeleteMapping("/delete/{id}")
    @SecurityRequirement(name = "security token")
    public ResponseEntity<?> deletePatient(@PathVariable UUID id);

    @PutMapping("/update/{id}")
    @SecurityRequirement(name = "security token")
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable UUID id, @RequestBody PatientRequest request);
}
