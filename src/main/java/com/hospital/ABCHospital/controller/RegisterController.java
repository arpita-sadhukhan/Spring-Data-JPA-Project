package com.hospital.ABCHospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.ABCHospital.Dto.DoctorDTO;
import com.hospital.ABCHospital.Dto.PatientDTO;
import com.hospital.ABCHospital.service.IRegistrationService;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/register")
public class RegisterController {
	
	@Autowired
	IRegistrationService service;

	@PostMapping("/doctor")
	public ResponseEntity<DoctorDTO> registerDoctor(@RequestBody DoctorDTO doctorDto) {
		
		if(doctorDto != null) {
			DoctorDTO registeredDoctor = service.registerDoctor(doctorDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(registeredDoctor);
		}
		return null;
	}
	
	@PostMapping("/patient")
	public ResponseEntity<PatientDTO> registerPatient(@RequestBody PatientDTO patientDto){
		
		PatientDTO dto = service.registerPatient(patientDto);
		if(dto != null) {
			Link link = linkTo(RegisterController.class).withSelfRel();
			dto.add(link);
			return ResponseEntity.status(HttpStatus.CREATED).body(dto);
		}
		return null;
	}
	
}
