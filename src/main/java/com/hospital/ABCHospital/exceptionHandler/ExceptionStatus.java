package com.hospital.ABCHospital.exceptionHandler;

public enum ExceptionStatus {

	DUPLICATE_DOCTOR(1000, "Doctor is already Registered"),
	DUPLICATE_PATIENT(1001, "Patient is already Registered"),
	DUPLICATE_APPOINTMENT(1002, "An appointment of the Patient with the same doctor is already registered"),
	INCOMPLETE_APPOINTMENT(1003, "Incomplete Request"),
	DOCTOR_UNREGISTERED(1004, "Requested Doctor is not Registered"),
	PATIENT_UNREGISTERED(1005, "Requested Patient is not Registered"),
	BACK_DATE(1006, "Back Dated processes are not supported"),
	NO_SLOTS_AVAILABLE(1007, "No Appointment slots are available on the requested date for this doctor"),
	NO_APPOINTMENTS_AVAILABLE(1008, "No Appointment are available on the requested date"),
	MANDATORY_DATA_NOT_AVAILABLE(1009, "Mandatory data is missing"),
	PAYMENT_DETAILS_MISSING(1010, "Payment Details are missing"),
	INTERNAL_SERVER_ERROR(1011, "Internal Server Error");
	
	

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
