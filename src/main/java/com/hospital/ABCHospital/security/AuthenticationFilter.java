package com.hospital.ABCHospital.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.ABCHospital.SpringApplicationContext;
import com.hospital.ABCHospital.Dto.UserDTO;
import com.hospital.ABCHospital.Dto.UserLoginRequest;
import com.hospital.ABCHospital.service.IUserSignupService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private final AuthenticationManager manager;
	
	public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.manager = authenticationManager;
    }
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException{
		try {
			UserLoginRequest creds = new ObjectMapper().readValue(req.getInputStream(), UserLoginRequest.class);
			
			return manager.authenticate(new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));
		}catch(IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, AuthenticationException{
		
		String userName = ((User)auth.getPrincipal()).getUsername();
		
		String token = Jwts.builder()
							.setSubject(userName)
							.setExpiration(new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
							.signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET)
							.compact();
		
		IUserSignupService userService = (IUserSignupService)SpringApplicationContext.getBean("userSignupService");
		UserDTO userDto = userService.getUser(userName);
		
		res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
		res.addHeader("UserId", userDto.getUserId());
	}

}
