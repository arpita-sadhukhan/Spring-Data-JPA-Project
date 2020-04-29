package com.hospital.ABCHospital.Dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DoctorDTO {

	private Integer id;
	
	@NotNull
	@JsonProperty("Title")
	private String title;
	
	@NotNull(message = "First Name must not be null")
	@JsonProperty("FirstName")
	private String firstName;
	
	@JsonProperty("LastName")
	private String lastname;
	
	@NotNull(message = "Phone No nust not be null")
	@JsonProperty("PhoneNumber")
	private String phoneNumber;
	
	@NotNull(message = "Speciality must not be null")
	@JsonProperty("Speciality")
	private String speciality;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
