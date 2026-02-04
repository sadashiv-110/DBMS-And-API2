package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
