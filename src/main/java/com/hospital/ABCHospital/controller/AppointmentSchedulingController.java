package com.hospital.ABCHospital.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.aspectj.apache.bcel.ExceptionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.ABCHospital.Dto.AppointmentDTO;
import com.hospital.ABCHospital.exception.DuplicateRecordException;
import com.hospital.ABCHospital.exception.InvalidDataException;
import com.hospital.ABCHospital.exception.InvalidUserException;
import com.hospital.ABCHospital.exceptionHandler.ExceptionMessage;
import com.hospital.ABCHospital.exceptionHandler.ExceptionStatus;
import com.hospital.ABCHospital.service.IAppointmentService;

@RestController
@RequestMapping("/appointment")
public class AppointmentSchedulingController {

	@Autowired
	IAppointmentService appService;

	@PostMapping("/create")
	public ResponseEntity<AppointmentDTO> getAnAppointment(@RequestBody AppointmentDTO appDto) {

		AppointmentDTO createdAppointment = null;
		try {
			createdAppointment = appService.createAppointment(appDto);
		} catch (InvalidUserException e) {

			Link linkToRegisterAppointment = linkTo(RegisterController.class).slash("patient").withSelfRel();

			createdAppointment = new AppointmentDTO();
			createdAppointment.add(linkToRegisterAppointment);

			ExceptionMessage exp = new ExceptionMessage();
			exp.setErrorCode(HttpStatus.BAD_REQUEST.value());
			exp.setErrorMessage(ExceptionStatus.PATIENT_UNREGISTERED.getStatusMessage());
			createdAppointment.setErrorMessage(exp);
		} catch (InvalidDataException e) {

			createdAppointment = new AppointmentDTO();

			ExceptionMessage exp = new ExceptionMessage();
			exp.setErrorCode(HttpStatus.BAD_REQUEST.value());
			exp.setErrorMessage(ExceptionStatus.BACK_DATE.getStatusMessage());
			createdAppointment.setErrorMessage(exp);

		} catch (DuplicateRecordException e) {
			createdAppointment = new AppointmentDTO();

			ExceptionMessage exp = new ExceptionMessage();
			exp.setErrorCode(HttpStatus.BAD_REQUEST.value());
			exp.setErrorMessage(ExceptionStatus.DUPLICATE_APPOINTMENT.getStatusMessage());
			createdAppointment.setErrorMessage(exp);
		}
		// TODO create the link to fetch all the appointments for the patient.
		return ResponseEntity.status(HttpStatus.CREATED).body(createdAppointment);
	}
	
	@PutMapping("/checkIn")
	public ResponseEntity<AppointmentDTO> checkIn(@RequestBody AppointmentDTO appDto) throws Exception{
		
		if(appDto.getPatientId() == 0)
			throw new InvalidDataException(ExceptionStatus.MANDATORY_DATA_NOT_AVAILABLE.getStatusMessage());
		
		AppointmentDTO checkinAppointment = appService.checkIn(appDto);
		if(checkinAppointment == null) {
			throw new Exception(ExceptionStatus.INTERNAL_SERVER_ERROR.getStatusMessage());
		}
		Link link = linkTo(methodOn(FetchInformationController.class).getPatientAppointmentsByPatientIdAndDate(checkinAppointment.getPatientId(), checkinAppointment.getDate())).withSelfRel();
		checkinAppointment.add(link);
		return ResponseEntity.accepted().body(checkinAppointment);
	}
}
