package com.pm.patientservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.pm.patientservice.api.exceptions.customs.NotFoundException;
import com.pm.patientservice.api.models.requests.PatientRequest;
import com.pm.patientservice.api.models.responses.PatientResponse;
import com.pm.patientservice.persistence.entities.Patient;
import com.pm.patientservice.persistence.repositories.PatientRepository;
import com.pm.patientservice.services.impl.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Unit tests for the PatientServiceImpl class.
 * This class tests the methods of the PatientServiceImpl to ensure they behave as expected.
 *
 * @author caito
 *
 */
@ExtendWith(MockitoExtension.class)
public class PatientServiceTests {
    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    private PatientRequest validPatientRequest;
    private Patient patientEntity;
    private PatientResponse patientResponse;
    private UUID testId;
    private String testEmail;
    private String testDni;
    private String testName;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();
        testEmail = "patient@example.com";
        testDni = "12345678A";
        testName = "John Doe";

        validPatientRequest = new PatientRequest();
        validPatientRequest.setName(testName);
        validPatientRequest.setEmail(testEmail);
        validPatientRequest.setDni(testDni);
        validPatientRequest.setAddress("123 Main St");

        patientEntity = new Patient();
        patientEntity.setId(testId);
        patientEntity.setName(testName);
        patientEntity.setEmail(testEmail);
        patientEntity.setDni(testDni);
        patientEntity.setAddress("123 Main St");

        patientResponse = new PatientResponse();
        patientResponse.setId(testId);
        patientResponse.setName(testName);
        patientResponse.setEmail(testEmail);
        patientResponse.setDni(testDni);
        patientResponse.setAddress("123 Main St");
    }

    @Test
    void createPatient_WithValidRequest_ShouldSavePatient() {
        // Arrange
        when(patientRepository.existsByEmail(testEmail)).thenReturn(false);
        when(patientRepository.existsByDni(testDni)).thenReturn(false);
        when(patientRepository.save(any(Patient.class))).thenReturn(patientEntity);

        // Act
        patientService.createPatient(validPatientRequest);

        // Assert
        verify(patientRepository, times(1)).save(any(Patient.class));
    }



    @Test
    void getPatientById_WithValidId_ShouldReturnPatient() {
        // Arrange
        when(patientRepository.findById(testId)).thenReturn(Optional.of(patientEntity));

        // Act
        PatientResponse result = patientService.getPatientById(testId);

        // Assert
        assertNotNull(result);
        assertEquals(testId, result.getId());
        assertEquals(testName, result.getName());
        assertEquals(testEmail, result.getEmail());
    }

    @Test
    void getPatientById_WithInvalidId_ShouldThrowNotFoundException() {
        // Arrange
        when(patientRepository.findById(testId)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> patientService.getPatientById(testId));
        assertEquals("Patient not found with ID: " + testId, exception.getMessage());
    }

    @Test
    void getPatients_ShouldReturnPaginatedResults() {
        // Arrange
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Patient> patients = Collections.singletonList(patientEntity);
        Page<Patient> patientPage = new PageImpl<>(patients, pageRequest, patients.size());

        when(patientRepository.findAll(pageRequest)).thenReturn(patientPage);

        // Act
        Page<PatientResponse> result = patientService.getPatients(page, size);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(testName, result.getContent().get(0).getName());
    }

    @Test
    void getPatientByEmail_WithValidEmail_ShouldReturnPatient() {
        // Arrange
        when(patientRepository.findByEmail(testEmail)).thenReturn(Optional.of(patientEntity));

        // Act
        PatientResponse result = patientService.getPatientByEmail(testEmail);

        // Assert
        assertNotNull(result);
        assertEquals(testEmail, result.getEmail());
    }

    @Test
    void getPatientByEmail_WithInvalidEmail_ShouldThrowNotFoundException() {
        // Arrange
        when(patientRepository.findByEmail(testEmail)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> patientService.getPatientByEmail(testEmail));
        assertEquals("Patient not found with email: " + testEmail, exception.getMessage());
    }

    @Test
    void getPatientByDni_WithValidDni_ShouldReturnPatient() {
        // Arrange
        when(patientRepository.findByDni(testDni)).thenReturn(Optional.of(patientEntity));

        // Act
        PatientResponse result = patientService.getPatientByDni(testDni);

        // Assert
        assertNotNull(result);
        assertEquals(testDni, result.getDni());
    }

    @Test
    void getPatientByDni_WithInvalidDni_ShouldThrowNotFoundException() {
        // Arrange
        when(patientRepository.findByDni(testDni)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> patientService.getPatientByDni(testDni));
        assertEquals("Patient not found with DNI: " + testDni, exception.getMessage());
    }

    @Test
    void getPatientsByName_ShouldReturnMatchingPatients() {
        // Arrange
        String searchName = "John";
        List<Patient> patients = Collections.singletonList(patientEntity);

        when(patientRepository.findByNameContainingIgnoreCase(searchName)).thenReturn(patients);

        // Act
        List<PatientResponse> result = patientService.getPatientsByName(searchName);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).getName().contains(searchName));
    }

    @Test
    void getPatientsByName_WithNoMatches_ShouldReturnEmptyList() {
        // Arrange
        String searchName = "Nonexistent";
        when(patientRepository.findByNameContainingIgnoreCase(searchName)).thenReturn(Collections.emptyList());

        // Act
        List<PatientResponse> result = patientService.getPatientsByName(searchName);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
