package com.example.authserverdemo.dao;

import com.example.authserverdemo.entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<OTP,String > {

    Optional<OTP> findOtpByUsername(String username);

}
