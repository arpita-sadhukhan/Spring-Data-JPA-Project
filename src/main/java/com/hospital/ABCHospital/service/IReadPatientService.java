package com.hospital.ABCHospital.service;

import java.util.List;

import com.hospital.ABCHospital.Dto.AppointmentDTO;
import com.hospital.ABCHospital.Dto.PatientDTO;
import com.hospital.ABCHospital.exception.InvalidUserException;

public interface IReadPatientService {

	PatientDTO getPatientDetailsByPatientId(Integer patientId) throws InvalidUserException;

	List<AppointmentDTO> getPatientAppointmentsByPatientId(Integer patientId);

}
