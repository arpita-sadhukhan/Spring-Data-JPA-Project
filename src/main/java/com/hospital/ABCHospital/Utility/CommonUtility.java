package com.hospital.ABCHospital.Utility;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class CommonUtility {

	private Random random = new SecureRandom();
	private final String ALPHABETS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	public String generatePublicUserId(int length) {
		return getRandomString(length);
	}

	private String getRandomString(int length) {
		StringBuilder str = new StringBuilder();
		
		while(length > 0) {
			str.append(ALPHABETS.charAt(random.nextInt(ALPHABETS.length())));
			length--;
		}
		return str.toString();
	}
}
