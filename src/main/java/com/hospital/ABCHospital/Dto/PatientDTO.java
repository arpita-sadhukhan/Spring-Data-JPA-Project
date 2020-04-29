package com.hospital.ABCHospital.Dto;

import javax.validation.constraints.NotNull;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PatientDTO extends RepresentationModel<PatientDTO>{

	private Integer id;
	
	@NotNull(message = "Title cant be null")
	@JsonProperty("Title")
	private String title;
	
	@NotNull(message = "First Name cant be null")
	@JsonProperty("First Name")
	private String firstName;
	
	@JsonProperty("Last Name")
	private String lastName;
	
	@JsonProperty("Phone")
	private String phone;
	
	@JsonProperty("Insurance")
	private InsuranceDTO insurance;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public InsuranceDTO getInsurance() {
		return insurance;
	}
	public void setInsurance(InsuranceDTO insurance) {
		this.insurance = insurance;
	}
	
}
