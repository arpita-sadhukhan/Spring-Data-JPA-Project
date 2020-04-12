package com.hospital.ABCHospital.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.hospital.ABCHospital.entity.Doctor;

public interface DoctorRepo extends CrudRepository<Doctor, Integer> {
	
	public Optional<Doctor> findByFirstName(String firstname);

}
