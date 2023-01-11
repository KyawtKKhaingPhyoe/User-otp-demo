package com.example.authserverdemo.service;

import com.example.authserverdemo.dao.OtpRepository;
import com.example.authserverdemo.dao.UserRepository;
import com.example.authserverdemo.entity.OTP;
import com.example.authserverdemo.entity.User;
import com.example.authserverdemo.util.GenerateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpRepository otpRepository;

    public void addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public boolean check(OTP otpToValidate){
        Optional<OTP> userOtp =
                otpRepository.findOtpByUsername(otpToValidate.getUsername());
        if(userOtp.isPresent()){
            OTP otp = userOtp.get();
            if(otpToValidate.getCode().equals(otp.getCode())){
                return true;
            }
        }
        return false;
    }

    public void auth(User user){
        Optional<User> userOptional =
                userRepository.findUserByUsername(user.getUsername());
        if (userOptional.isPresent()){
            User u = userOptional.get();
            if (passwordEncoder.matches(user.getPassword(),
                    u.getPassword())){
                renewOtp(u);
            } else {
                throw new BadCredentialsException("Bad Credential!");
            }
        }else {
            throw new BadCredentialsException("Bad Credential!");
        }
    }

    private void renewOtp(User u){
        String code = GenerateCodeUtil.generatedCode();
        Optional<OTP> userOtp =
                otpRepository.findOtpByUsername(u.getUsername());
        if(userOtp.isPresent()){
            OTP otp = userOtp.get();
            otp.setCode(code);
        } else {
            OTP otp = new OTP();
            otp.setUsername(u.getUsername());
            otp.setCode(code);
            otpRepository.save(otp);
        }
    }

}
