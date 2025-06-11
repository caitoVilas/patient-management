package com.pm.patientservice.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity class representing a Patient in the system.
 * This class is mapped to the "patients" table in the database.
 * It contains fields for the patient's ID and other relevant information.
 *
 * @author caito
 *
 */
@Entity
@Table(name = "patients")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, length = 75)
    private String name;
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    @Column(length = 250)
    private String address;
    @Column(length = 75)
    private String phone;
    @Column(nullable = false, unique = true, length = 9)
    private String dni;
    private LocalDate birthDate;
    @CreationTimestamp
    private LocalDateTime registrationDate;
    @UpdateTimestamp
    private LocalDateTime lastUpdateDate;
}
