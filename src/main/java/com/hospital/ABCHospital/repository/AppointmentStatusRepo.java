package com.hospital.ABCHospital.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hospital.ABCHospital.entity.AppointmentStatus;
import com.hospital.ABCHospital.entity.AppointmentStatusEmbeddedId;

@Repository
public interface AppointmentStatusRepo extends CrudRepository<AppointmentStatus, AppointmentStatusEmbeddedId> {

}
