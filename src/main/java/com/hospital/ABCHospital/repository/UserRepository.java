package com.hospital.ABCHospital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hospital.ABCHospital.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

	@Query("select u from UserEntity u join u.userDetail d where d.email = :email")
	Optional<UserEntity> findByEmail(String email);
}
