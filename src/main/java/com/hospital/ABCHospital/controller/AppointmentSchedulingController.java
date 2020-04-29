package com.hospital.ABCHospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.ABCHospital.Dto.AppointmentDTO;
import com.hospital.ABCHospital.service.AppointmentService;

@RestController
@RequestMapping("/appointment")
public class AppointmentSchedulingController {

	@Autowired
	AppointmentService appService;
	
	@PostMapping("/createAnAppointment")
	public ResponseEntity<AppointmentDTO> getAnAppointment(@RequestParam AppointmentDTO appDto){
		
		AppointmentDTO createdAppointment = appService.createAppointment(appDto);
		//TODO create the link to fetch all the appointments for the patient.
		return ResponseEntity.status(HttpStatus.CREATED).body(createdAppointment);
	}
}
