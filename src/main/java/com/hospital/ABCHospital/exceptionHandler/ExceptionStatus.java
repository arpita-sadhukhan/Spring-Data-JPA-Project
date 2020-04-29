package com.hospital.ABCHospital.exceptionHandler;

public enum ExceptionStatus {

	DUPLICATE_DOCTOR(1000, "Doctor is already Registered"),
	DUPLICATE_PATIENT(1001, "Patient is already Registered"),
	DUPLICATE_APPOINTMENT(1002, "An appointment of the Patient with the same doctor is already registered"),
	INCOMPLETE_APPOINTMENT(1003, "Incomplete Request");
	
	

	private int statusCode;
	private String statusMessage;

	ExceptionStatus(int statusCode, String statusMessage) {
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	
}
