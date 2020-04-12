package com.hospital.ABCHospital.Dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DoctorDTO {

	private Integer id;
	
	@NotNull
	@JsonProperty("FirstName")
	private String firstName;
	
	@NotNull
	@JsonProperty("LastName")
	private String lastname;
	
	@NotNull
	@JsonProperty("PhoneNumber")
	private String phone;
	
	private String InsuranceProvider;
	private double coverageLimit;

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getInsuranceProvider() {
		return InsuranceProvider;
	}

	public void setInsuranceProvider(String insuranceProvider) {
		InsuranceProvider = insuranceProvider;
	}

	public double getCoverageLimit() {
		return coverageLimit;
	}

	public void setCoverageLimit(double coverageLimit) {
		this.coverageLimit = coverageLimit;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
