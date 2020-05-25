package com.hospital.ABCHospital.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.ABCHospital.Dto.UserDTO;
import com.hospital.ABCHospital.Dto.UserSignUpRequest;
import com.hospital.ABCHospital.Dto.UserSignUpResponse;
import com.hospital.ABCHospital.service.UserSignupService;

@RestController
public class SignUpController {

	@Autowired
	UserSignupService userService;
	
	@Autowired
	ModelMapper mapper;
	
	@PostMapping("/signup")
	public ResponseEntity<UserSignUpResponse> signUp(@RequestBody UserSignUpRequest user){
		
		UserSignUpResponse response;
		if(user != null) {
			UserDTO userDto = mapper.map(user, UserDTO.class);
			UserDTO createdUser = userService.signup(userDto);
			response = new UserSignUpResponse();
			response = mapper.map(createdUser, UserSignUpResponse.class);
			
			return ResponseEntity.accepted().body(response);
		}
		return null;
	}
	@GetMapping("/get")
	public String get() {
		return "Hi";
	}
}
