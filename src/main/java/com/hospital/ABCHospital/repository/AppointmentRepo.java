package com.hospital.ABCHospital.repository;

import org.springframework.data.repository.CrudRepository;

import com.hospital.ABCHospital.entity.Appointment;

public interface AppointmentRepo extends CrudRepository<Appointment, Integer> {

}
