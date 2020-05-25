package com.hospital.ABCHospital.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.hospital.ABCHospital.service.IUserSignupService;

@EnableWebSecurity
public class WebSecurityUtils extends WebSecurityConfigurerAdapter{

	@Autowired
	IUserSignupService service;
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	@Override
	public void configure(HttpSecurity http)throws Exception{
		http.csrf().disable().authorizeRequests()
			.antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
			.permitAll()
			.anyRequest()
			.authenticated()
			.and().addFilter(getAuthenticationFilter())
			.addFilter(new AuthorizationFilter(authenticationManager()))
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(service).passwordEncoder(encoder);
	}
	
	public AuthenticationFilter getAuthenticationFilter()throws Exception{
		final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
		filter.setFilterProcessesUrl("/user/login");
		return filter;
	}
}
