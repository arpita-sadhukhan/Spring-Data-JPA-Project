package com.hospital.ABCHospital.exception;

public class DuplicateRecordException extends RuntimeException {

	private static final long serialVersionUID = 8088745223165350349L;

	public DuplicateRecordException(String message) {
		super(message);
	}

}
