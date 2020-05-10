package com.hospital.ABCHospital.service;

import java.sql.Date;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.ABCHospital.Dto.AppointmentDTO;
import com.hospital.ABCHospital.Dto.InsuranceDTO;
import com.hospital.ABCHospital.Dto.PatientDTO;
import com.hospital.ABCHospital.entity.Appointment;
import com.hospital.ABCHospital.entity.Patient;
import com.hospital.ABCHospital.exception.InvalidDataException;
import com.hospital.ABCHospital.exception.InvalidUserException;
import com.hospital.ABCHospital.exceptionHandler.ExceptionStatus;
import com.hospital.ABCHospital.repository.AppointmentRepo;
import com.hospital.ABCHospital.repository.PatientRepo;

@Service
public class ReadPatientService implements IReadPatientService {

	@Autowired
	PatientRepo patientRepo;
	
	@Autowired
	AppointmentRepo appRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public PatientDTO getPatientDetailsByPatientId(Integer patientId) throws InvalidUserException {
		
		Optional<Patient> patient = patientRepo.findById(patientId);
		patient.orElseThrow(() -> new InvalidUserException(ExceptionStatus.PATIENT_UNREGISTERED.getStatusMessage()));
		
		PatientDTO patientDTO = modelMapper.map(patient.get(), PatientDTO.class);
		InsuranceDTO insurance = modelMapper.map(patient.get().getProviderName(), InsuranceDTO.class);
		patientDTO.setInsurance(insurance);
		return patientDTO;
	}

	@Override
	public AppointmentDTO getPatientAppointmentsByPatientId(Integer patientId, String date) throws InvalidDataException {

		Date reqDate = Date.valueOf(date);
		Optional<Appointment> appointment = appRepo.findByPatientIdAndDateOfAppointment(patientId, reqDate);
		appointment.orElseThrow(() -> new InvalidDataException(ExceptionStatus.NO_APPOINTMENTS_AVAILABLE.getStatusMessage()));
		
		return modelMapper.map(appointment.get(), AppointmentDTO.class);
	}

	
}
