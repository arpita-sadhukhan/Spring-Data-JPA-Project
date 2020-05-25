package com.hospital.ABCHospital.service;

import java.util.ArrayList;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hospital.ABCHospital.Dto.UserDTO;
import com.hospital.ABCHospital.Utility.CommonUtility;
import com.hospital.ABCHospital.entity.UserDetail;
import com.hospital.ABCHospital.entity.UserEntity;
import com.hospital.ABCHospital.repository.UserRepository;

@Service
public class UserSignupService implements IUserSignupService {

	@Autowired
	ModelMapper mapper;
	
	@Autowired
	UserRepository repo;
	
	@Autowired
	CommonUtility utils;
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	@Override
	public UserDTO signup(UserDTO user) {

		UserEntity userEntity = mapper.map(user, UserEntity.class);
		UserDetail userDetail = mapper.map(user, UserDetail.class);
		
		userEntity.setUserDetail(userDetail);
		userEntity.setUserId(utils.generatePublicUserId(30));
		userEntity.setEncryptedPassword(encoder.encode(user.getPassword()));
		
		UserEntity createdUser = repo.save(userEntity);
		UserDTO userCreatedDto = mapper.map(createdUser, UserDTO.class);
		userCreatedDto = mapper.map(createdUser.getUserDetail(), UserDTO.class);
		
		return userCreatedDto;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<UserEntity> user = repo.findByEmail(username);
		
		user.orElseThrow(() -> new UsernameNotFoundException(username));
		UserEntity userEntity = (UserEntity)user.get();
		UserDetail userDetail = userEntity.getUserDetail();
		return new User(userDetail.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDTO getUser(String email) {
		Optional<UserEntity> user = repo.findByEmail(email);
		
		user.orElseThrow(() -> new UsernameNotFoundException(email));
		UserEntity userEntity = (UserEntity)user.get();
		
		UserDTO userDto = mapper.map(userEntity, UserDTO.class);
		return userDto;
	}

}
