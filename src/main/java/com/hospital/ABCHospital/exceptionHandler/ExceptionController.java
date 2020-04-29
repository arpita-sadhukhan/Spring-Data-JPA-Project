package com.hospital.ABCHospital.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hospital.ABCHospital.exception.DuplicateRecordException;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler{

	@ExceptionHandler(DuplicateRecordException.class)
	public ResponseEntity<ExceptionMessage> handleExceptions(Exception ex, WebRequest request) {
		
		ExceptionMessage exMsg = new ExceptionMessage();
		exMsg.setErrorMessage(ex.getMessage());
		HttpStatus conflict = null;
		if(ex instanceof DuplicateRecordException) {
			conflict = HttpStatus.CONFLICT;
			exMsg.setErrorCode(conflict.value());
		}
		
		return new ResponseEntity<>(exMsg, conflict);
	}
}
