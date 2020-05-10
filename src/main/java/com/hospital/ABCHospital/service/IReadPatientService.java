package com.hospital.ABCHospital.service;

import java.sql.Date;
import java.util.List;

import com.hospital.ABCHospital.Dto.AppointmentDTO;
import com.hospital.ABCHospital.Dto.PatientDTO;
import com.hospital.ABCHospital.exception.InvalidDataException;
import com.hospital.ABCHospital.exception.InvalidUserException;

public interface IReadPatientService {

	PatientDTO getPatientDetailsByPatientId(Integer patientId) throws InvalidUserException;

	//List<AppointmentDTO> getPatientAppointmentsByPatientId(Integer patientId);

	AppointmentDTO getPatientAppointmentsByPatientId(Integer patientId, String date) throws InvalidDataException;

}
