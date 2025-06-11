package com.pm.patientservice.services.contracts;

import com.pm.patientservice.api.models.requests.PatientRequest;
import com.pm.patientservice.api.models.responses.PatientResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing patient operations.
 * This interface defines methods for creating a patient, retrieving a patient by ID,
 * and getting a paginated list of patients.
 *
 * @author caito
 *
 */
public interface PatientService {
    void createPatient(PatientRequest request);
    PatientResponse getPatientById(UUID id);
    Page<PatientResponse> getPatients(int page, int size);
    PatientResponse getPatientByEmail(String email);
    PatientResponse getPatientByDni(String dni);
    List<PatientResponse> getPatientsByName(String name);
    void deletePatient(UUID id);
    PatientResponse updatePatient(UUID id, PatientRequest request);
}
