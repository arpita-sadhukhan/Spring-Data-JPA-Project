package com.hospital.ABCHospital.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.ABCHospital.Dto.AppointmentDTO;
import com.hospital.ABCHospital.exception.DuplicateRecordException;
import com.hospital.ABCHospital.exception.InvalidDataException;
import com.hospital.ABCHospital.exception.InvalidUserException;
import com.hospital.ABCHospital.exceptionHandler.ExceptionMessage;
import com.hospital.ABCHospital.exceptionHandler.ExceptionStatus;
import com.hospital.ABCHospital.service.AppointmentService;

@RestController
public class AppointmentSchedulingController {

	@Autowired
	AppointmentService appService;

	@PostMapping("/createAnAppointment")
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
}
