package com.hospital.ABCHospital.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InsuranceDTO {

	@JsonProperty("Provider Name")
	private String provider;
	
	@JsonProperty("Coverage")
	private double coPay;
	
	public double getCoPay() {
		return coPay;
	}
	public void setCoPay(double coPay) {
		this.coPay = coPay;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}

}
