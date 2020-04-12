package com.hospital.ABCHospital.repository;

import org.springframework.data.repository.CrudRepository;

import com.hospital.ABCHospital.entity.Doctor;

public interface DoctorRepo extends CrudRepository<Doctor, Integer> {

}
