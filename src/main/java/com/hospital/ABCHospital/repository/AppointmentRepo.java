package com.hospital.ABCHospital.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.hospital.ABCHospital.entity.Appointment;

public interface AppointmentRepo extends CrudRepository<Appointment, Integer> {

	Optional<List<Appointment>> findByPatientIdAndDoctorId(Integer patientId, Integer doctorId);
}
