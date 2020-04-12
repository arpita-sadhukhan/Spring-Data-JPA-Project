package com.hospital.ABCHospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.ABCHospital.Dto.DoctorDTO;
import com.hospital.ABCHospital.service.IRegistrationService;

@RestController
@RequestMapping("/register")
public class RegisterController {
	
	@Autowired
	IRegistrationService service;

	@PutMapping("/doctor")
	public ResponseEntity<DoctorDTO> registerDoctor(@RequestBody DoctorDTO doctorDto){
		
		if(doctorDto != null) {
			DoctorDTO registeredDoctor = service.registerDoctor(doctorDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(registeredDoctor);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
}
