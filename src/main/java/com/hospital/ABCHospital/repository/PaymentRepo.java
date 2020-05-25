package com.hospital.ABCHospital.repository;

import org.springframework.data.repository.CrudRepository;

import com.hospital.ABCHospital.entity.Payment;

public interface PaymentRepo extends CrudRepository<Payment, Double> {

}
