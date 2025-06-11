package com.pm.patientservice.services.impl;

import com.pm.patientservice.api.exceptions.customs.BadRequestException;
import com.pm.patientservice.api.exceptions.customs.NotFoundException;
import com.pm.patientservice.api.models.requests.PatientRequest;
import com.pm.patientservice.api.models.responses.PatientResponse;
import com.pm.patientservice.persistence.repositories.PatientRepository;
import com.pm.patientservice.services.contracts.PatientService;
import com.pm.patientservice.services.helpers.ValidationHelper;
import com.pm.patientservice.utils.logs.WriteLog;
import com.pm.patientservice.utils.mappers.PatientMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of the PatientService interface.
 * This class provides methods to create a patient, retrieve a patient by ID,
 * and retrieve a paginated list of patients.
 *
 * @author caito
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    /**
     * Creates a new patient.
     *
     * @param request the patient request containing patient details
     * @throws BadRequestException if the patient request is invalid
     */
    @Override
    @Transactional
    public void createPatient(PatientRequest request) {
        log.info(WriteLog.logInfo("Creating patient service"));
        validatePatient(request);
        patientRepository.save(PatientMapper.mapToEntity(request));
    }



    /**
     * Retrieves a patient by its ID.
     *
     * @param id the ID of the patient to retrieve
     * @return the patient response containing patient details
     * @throws NotFoundException if the patient is not found
     */
    @Override
    @Transactional(readOnly = true)
    public PatientResponse getPatientById(UUID id) {
        log.info(WriteLog.logInfo("Retrieving patient by ID: " + id));
        return PatientMapper.mapToDto(patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient not found with ID: " + id)));

    }

    /**
     * Retrieves a paginated list of patients.
     *
     * @param page the page number to retrieve
     * @param size the number of patients per page
     * @return a page of patient responses
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PatientResponse> getPatients(int page, int size) {
        log.info(WriteLog.logInfo("Retrieving patients with pagination: page " + page + ", size " + size));
        PageRequest pr = PageRequest.of(page, size);
        return patientRepository.findAll(pr)
                .map(PatientMapper::mapToDto);
    }

    /**
     * Retrieves a patient by its email.
     *
     * @param email the email of the patient to retrieve
     * @return the patient response containing patient details
     * @throws NotFoundException if the patient is not found
     */
    @Override
    @Transactional(readOnly = true)
    public PatientResponse getPatientByEmail(String email) {
        log.info(WriteLog.logInfo("Retrieving patient by email: " + email));
        return PatientMapper.mapToDto(patientRepository.findByEmail(email)
        .orElseThrow(() -> new NotFoundException("Patient not found with email: " + email)));
    }


    /**
     * Retrieves a patient by its DNI.
     *
     * @param dni the DNI of the patient to retrieve
     * @return the patient response containing patient details
     * @throws NotFoundException if the patient is not found
     */
    @Override
    @Transactional(readOnly = true)
    public PatientResponse getPatientByDni(String dni) {
        log.info(WriteLog.logInfo("Retrieving patient by DNI: " + dni));
        return PatientMapper.mapToDto(patientRepository.findByDni(dni)
        .orElseThrow(() -> new NotFoundException("Patient not found with DNI: " + dni)));
    }

    /**
     * Retrieves a list of patients by their name.
     *
     * @param name the name to search for
     * @return a list of patient responses containing patient details
     */
    @Override
    @Transactional(readOnly = true)
    public List<PatientResponse> getPatientsByName(String name) {
        log.info(WriteLog.logInfo("Retrieving patients by name: " + name));
        return patientRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(PatientMapper::mapToDto)
                .toList();
    }

    /**
     * Deletes a patient by its ID.
     *
     * @param id the ID of the patient to delete
     * @throws NotFoundException if the patient is not found
     */
    @Override
    @Transactional
    public void deletePatient(UUID id) {
        log.info(WriteLog.logInfo("Deleting patient with ID: " + id));
        var patient = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient not found with ID: " + id));
        if (patient != null)
            patientRepository.delete(patient);
    }

    /**
     * Updates a patient with the provided request.
     *
     * @param id      the ID of the patient to update
     * @param request the patient request containing updated details
     * @return the updated patient response
     * @throws NotFoundException if the patient is not found
     * @throws BadRequestException if the request is invalid
     */
    @Override
    @Transactional
    public PatientResponse updatePatient(UUID id, PatientRequest request) {
        log.info(WriteLog.logInfo("Updating patient with ID: " + id));
        var patient = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient not found with ID: " + id));
        if (!(request.getName() == null) && !request.getName().isEmpty()) {
            patient.setName(request.getName());
        }
        if (!(request.getEmail() == null) && !request.getEmail().isEmpty()) {
           if (patientRepository.findEmailIsUsed(request.getEmail(), id)) {
                throw new BadRequestException(List.of("Email is in use by another patient"));
            }
            if (!ValidationHelper.validateEmail(request.getEmail())) {
                throw new BadRequestException(List.of("Invalid email format"));
            }
            patient.setEmail(request.getEmail());
        }
        if (!(request.getAddress() == null) && !request.getAddress().isEmpty()) {
            patient.setAddress(request.getAddress());
        }
        if (!(request.getDni() == null) && !request.getDni().isEmpty()) {
            if (patientRepository.findDniIsUsed(request.getDni(), id)) {
                throw new BadRequestException(List.of("DNI is in use by another patient"));
            }
            patient.setDni(request.getDni());
        }
        if (!(request.getPhone() == null) && !request.getPhone().isEmpty()) {
            patient.setPhone(request.getPhone());
        }
        if (!(request.getBirthDate() == null)) {
            patient.setBirthDate(request.getBirthDate());
        }
        patientRepository.save(patient);
        return PatientMapper.mapToDto(patient);
    }

    /**
     * Validates the patient request.
     *
     * @param request the patient request to validate
     * @throws BadRequestException if the request is invalid
     */
    private void validatePatient(PatientRequest request) {
        log.info(WriteLog.logInfo("Validating patient..."));
        List<String> errors = new ArrayList<>();

        //validate name
        if (request.getName() == null || request.getName().isEmpty()) {
            errors.add("Name cannot be null or empty");
        }
        //validate email
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            errors.add("Email cannot be null or empty");
        } else if (patientRepository.existsByEmail(request.getEmail())) {
            errors.add("Email already exists");
        } else if (!ValidationHelper.validateEmail(request.getEmail())) {
            errors.add("Invalid email format");
        }
        //validate address
        if (request.getAddress() == null || request.getAddress().isEmpty()) {
            errors.add("Address cannot be null or empty");
        }
        //validate dni
        if (request.getDni() == null || request.getDni().isEmpty()) {
            errors.add("DNI cannot be null or empty");
        } else if (patientRepository.existsByDni(request.getDni())) {
            errors.add("DNI already exists");
        }
        if (!errors.isEmpty()) {
            log.error(WriteLog.logError("Validation errors: " + String.join(", ", errors)));
            throw new BadRequestException(errors);
        }
    }
}
