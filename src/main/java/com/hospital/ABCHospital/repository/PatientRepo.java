package com.hospital.ABCHospital.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.hospital.ABCHospital.entity.Patient;

public interface PatientRepo extends CrudRepository<Patient, Integer> {

	public Optional<Patient> findByFirstName(String firstName);

	public Optional<Patient> findByFirstNameAndLastName(String firstName, String lastName);
}
