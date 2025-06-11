package com.pm.patientservice.persistence.repositories;

import com.pm.patientservice.persistence.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing Patient entities.
 * This interface extends JpaRepository to provide CRUD operations and custom query methods.
 * It includes a method to check if a patient exists by their email address.
 *
 * @author caito
 *
 */
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    boolean existsByEmail(String email);
    boolean existsByDni(String dni);
    Optional<Patient> findByEmail(String email);
    Optional<Patient> findByDni(String dni);
    List<Patient> findByNameContainingIgnoreCase(String name);
    @Query("SELECT COUNT(p) > 0 FROM Patient p WHERE p.email = :email AND p.id <> :id")
    boolean findEmailIsUsed(String email, UUID id);
    @Query("SELECT COUNT(p) > 0 FROM Patient p WHERE p.dni = :dni AND p.id <> :id")
    Boolean findDniIsUsed(String dni, UUID id);
}
