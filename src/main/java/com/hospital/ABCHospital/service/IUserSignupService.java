package com.hospital.ABCHospital.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.hospital.ABCHospital.Dto.UserDTO;

public interface IUserSignupService extends UserDetailsService{

	public UserDTO signup(UserDTO user);
	
	public UserDTO getUser(String email);
}
