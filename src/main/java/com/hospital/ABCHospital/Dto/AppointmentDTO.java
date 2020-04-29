package com.hospital.ABCHospital.Dto;

import javax.validation.constraints.NotNull;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AppointmentDTO extends RepresentationModel<AppointmentDTO> {

	@NotNull(message = "Please provide appointment time")
	@JsonProperty("Appointment Time")
	private String time;
	
	@JsonProperty("Reason")
	private String reason;
	
	private boolean started;
	private boolean ended;
	
	@NotNull(message = "Please provide Patient Information")
	private PatientDTO patientDto;
	
	@NotNull(message = "Please provide Doctor Information")
	private DoctorDTO doctorDto;
	
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
	public PatientDTO getPatientDto() {
		return patientDto;
	}
	public void setPatientDto(PatientDTO patientDto) {
		this.patientDto = patientDto;
	}
	public DoctorDTO getDoctorDto() {
		return doctorDto;
	}
	public void setDoctorDto(DoctorDTO doctorDto) {
		this.doctorDto = doctorDto;
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
	
}
