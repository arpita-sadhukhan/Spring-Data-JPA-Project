package com.hospital.ABCHospital.Dto;

import javax.validation.constraints.NotNull;

import org.modelmapper.spi.ErrorMessage;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hospital.ABCHospital.exceptionHandler.ExceptionMessage;

public class AppointmentDTO extends RepresentationModel<AppointmentDTO> {

	@NotNull(message = "Please provide appointment time")
	@JsonProperty("Appointment Time")
	private String time;

	@NotNull(message = "Please provide appointment date")
	@JsonProperty("Appointment date")
	private String date;

	private int appointment_id;

	@JsonProperty("Reason")
	private String reason;

	private boolean started;
	private boolean ended;

	@NotNull(message = "Please provide Patient Information")
	@JsonProperty("Patient Id")
	private int patientId;

	@NotNull(message = "Please provide Doctor Information")
	@JsonProperty("Doctor Id")
	private int doctorId;

	private ExceptionMessage errorMessage;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public boolean isEnded() {
		return ended;
	}

	public void setEnded(boolean ended) {
		this.ended = ended;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getAppointment_id() {
		return appointment_id;
	}

	public void setAppointment_id(int appointment_id) {
		this.appointment_id = appointment_id;
	}

	public ExceptionMessage getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(ExceptionMessage errorMessage) {
		this.errorMessage = errorMessage;
	}

}
