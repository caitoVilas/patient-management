package com.pm.patientservice.utils.mappers;

import com.pm.patientservice.api.models.requests.PatientRequest;
import com.pm.patientservice.api.models.responses.PatientResponse;
import com.pm.patientservice.persistence.entities.Patient;

/**
 * Mapper class for converting PatientRequest DTOs to Patient entities.
 * This class provides methods to map the properties of PatientRequest to Patient.
 *
 * @author caito
 *
 */
public class PatientMapper {

    /**
     * Maps a PatientRequest DTO to a Patient entity.
     *
     * @param request the PatientRequest DTO to be mapped
     * @return a Patient entity containing the mapped properties
     */
    public static Patient mapToEntity(PatientRequest request){
        return Patient.builder()
                .name(request.getName())
                .email(request.getEmail())
                .address(request.getAddress())
                .phone(request.getPhone())
                .dni(request.getDni())
                .birthDate(request.getBirthDate())
                .build();
    }

    /**
     * Maps a Patient entity to a PatientResponse DTO.
     *
     * @param patient the Patient entity to be mapped
     * @return a PatientResponse DTO containing the mapped properties
     */
    public static PatientResponse mapToDto(Patient patient) {
        return PatientResponse.builder()
                .id(patient.getId())
                .name(patient.getName())
                .email(patient.getEmail())
                .address(patient.getAddress())
                .phone(patient.getPhone())
                .dni(patient.getDni())
                .birthDate(patient.getBirthDate())
                .build();
    }
}
