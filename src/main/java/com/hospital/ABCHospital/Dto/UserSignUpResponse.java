package com.hospital.ABCHospital.Dto;

import com.hospital.ABCHospital.exceptionHandler.ExceptionMessage;

public class UserSignUpResponse {

	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private ExceptionMessage execptionMessage;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ExceptionMessage getExecptionMessage() {
		return execptionMessage;
	}

	public void setExecptionMessage(ExceptionMessage execptionMessage) {
		this.execptionMessage = execptionMessage;
	}

}
