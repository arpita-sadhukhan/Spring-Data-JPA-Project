package com.hospital.ABCHospital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.ABCHospital.Dto.AppointmentDTO;
import com.hospital.ABCHospital.Dto.PatientDTO;
import com.hospital.ABCHospital.exception.InvalidDataException;
import com.hospital.ABCHospital.exception.InvalidUserException;
import com.hospital.ABCHospital.exceptionHandler.ExceptionMessage;
import com.hospital.ABCHospital.exceptionHandler.ExceptionStatus;
import com.hospital.ABCHospital.service.IReadPatientService;

@RestController
public class FetchInformationController {

	@Autowired
	IReadPatientService patientService;
	
	@GetMapping("/patient")
	public ResponseEntity<PatientDTO> getPatientDetailsByPatientId(@RequestParam Integer patientId){
		
		if(patientId != null) {
			try {
				PatientDTO patientDto = patientService.getPatientDetailsByPatientId(patientId);

				return ResponseEntity.status(HttpStatus.ACCEPTED).body(patientDto);
			} catch (InvalidUserException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@GetMapping("/appointments/patient/{patientId}")
	public ResponseEntity<AppointmentDTO> getPatientAppointmentsByPatientIdAndDate(@PathVariable Integer patientId, @RequestParam String date){
		
		AppointmentDTO appointment = null;
		try {
			appointment = patientService.getPatientAppointmentsByPatientId(patientId, date);
			
		} catch (InvalidDataException e) {

			appointment = new AppointmentDTO();

			ExceptionMessage exp = new ExceptionMessage();
			exp.setErrorCode(HttpStatus.BAD_REQUEST.value());
			exp.setErrorMessage(e.getMessage());
			appointment.setErrorMessage(exp);
		}
		return ResponseEntity.accepted().body(appointment);
	}
}
