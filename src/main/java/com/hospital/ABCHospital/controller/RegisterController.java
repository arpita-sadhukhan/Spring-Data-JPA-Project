package com.hospital.ABCHospital.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.ABCHospital.Dto.DoctorDTO;
import com.hospital.ABCHospital.Dto.PatientDTO;
import com.hospital.ABCHospital.exception.DuplicateRecordException;
import com.hospital.ABCHospital.exceptionHandler.ExceptionMessage;
import com.hospital.ABCHospital.exceptionHandler.ExceptionStatus;
import com.hospital.ABCHospital.service.IRegistrationService;

@RestController
@RequestMapping("/register")
public class RegisterController {
	
	@Autowired
	IRegistrationService service;
	
	@PostMapping("/doctor")
	public ResponseEntity<DoctorDTO> registerDoctor(@RequestBody DoctorDTO doctorDto) {
		
		if(doctorDto != null) {
			DoctorDTO registeredDoctor = null;
			try {
				registeredDoctor = service.registerDoctor(doctorDto);
			} catch (DuplicateRecordException e) {
				
				registeredDoctor = new DoctorDTO();
				
				ExceptionMessage exp = new ExceptionMessage();
				exp.setErrorCode(HttpStatus.BAD_REQUEST.value());
				exp.setErrorMessage(ExceptionStatus.DUPLICATE_DOCTOR.getStatusMessage());
				registeredDoctor.setExecptionMessage(exp);
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(registeredDoctor);
		}
		return null;
	}
	
	@PostMapping("/patient")
	public ResponseEntity<PatientDTO> registerPatient(@RequestBody PatientDTO patientDto){
		
		PatientDTO dto = null;
		try {
			dto = service.registerPatient(patientDto);
			if(dto != null) {
				Link linkToSelf = linkTo(methodOn(FetchInformationController.class).getPatientDetailsByPatientId(dto.getId())).withSelfRel();
				
				dto.add(linkToSelf);
			}
		} catch (DuplicateRecordException e) {
			
			dto = new PatientDTO();
			
			ExceptionMessage exp = new ExceptionMessage();
			exp.setErrorCode(HttpStatus.BAD_REQUEST.value());
			exp.setErrorMessage(ExceptionStatus.DUPLICATE_DOCTOR.getStatusMessage());
			dto.setExceptionMessage(exp);
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
	}
	
	@GetMapping("/getAppointment")
	public String get() {
		return "Hi";
	}
	
}
