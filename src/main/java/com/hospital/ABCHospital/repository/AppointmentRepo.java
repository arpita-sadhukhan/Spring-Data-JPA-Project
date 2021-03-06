package com.hospital.ABCHospital.repository;

import java.sql.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hospital.ABCHospital.entity.Appointment;

public interface AppointmentRepo extends CrudRepository<Appointment, Integer> {

	@Query(value = "from Appointment where patient_id = :patientId and doctor_id = :doctorId and rownum = 1 order by date_of_appointment desc")
	Optional<Appointment> findByPatientIdAndDoctorId(Integer patientId, Integer doctorId);

	Optional<Appointment> findByPatientIdAndDateOfAppointment(Integer patientId, Date reqDate);
	
	Optional<Appointment> findByPatientIdAndDoctorIdAndDateOfAppointment(Integer patientId, Integer doctorId, Date reqDate);
}
