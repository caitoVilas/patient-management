package com.pm.patientservice.api.controllers.impl;

import com.pm.patientservice.api.controllers.contracts.PatientController;
import com.pm.patientservice.api.models.requests.PatientRequest;
import com.pm.patientservice.api.models.responses.PatientResponse;
import com.pm.patientservice.services.contracts.PatientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Implementation of the PatientController interface.
 * This class handles patient-related operations such as creating a patient and retrieving patient information.
 *
 * @author caito
 *
 */
@RestController
@RequestMapping("/v1/Patients")
@RequiredArgsConstructor
@Tag(name = "Patient API", description = "Controller for managing patients")
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class PatientCotrollerImpl implements PatientController {
    private final PatientService patientService;

    @Override
    public ResponseEntity<?> createPatient(PatientRequest request) {
        patientService.createPatient(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<PatientResponse> getPatientById(UUID id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @Override
    public ResponseEntity<Page<PatientResponse>> getAllPatients(int page, int size) {
        Page<PatientResponse> patients = patientService.getPatients(page, size);
        if (patients.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(patients);
    }

    @Override
    public ResponseEntity<PatientResponse> getPatientByEmail(String email) {
        return ResponseEntity.ok(patientService.getPatientByEmail(email));
    }

    @Override
    public ResponseEntity<PatientResponse> getPatientByDni(String dni) {
        return ResponseEntity.ok(patientService.getPatientByDni(dni));
    }

    @Override
    public ResponseEntity<List<PatientResponse>> getPatientsByName(String name) {
        var patients = patientService.getPatientsByName(name);
        if (patients.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(patients);
    }

    @Override
    public ResponseEntity<?> deletePatient(UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<PatientResponse> updatePatient(UUID id, PatientRequest request) {
        return ResponseEntity.ok(patientService.updatePatient(id, request));
    }
}
