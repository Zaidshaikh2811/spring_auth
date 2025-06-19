package com.example.spring_auth.services;

import com.example.spring_auth.entities.UserInfo;
import com.example.spring_auth.model.UserInfoDto;
import com.example.spring_auth.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
@Service
public class UserDetailsServiceImp implements UserDetailsService {

    // Implement the methods required by UserDetailsService
    // For example, loadUserByUsername(String username) method


    private final UserRepo userRepo; // Assuming you have a UserRepo to fetch user details


    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailsServiceImp(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }





    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserInfo user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new CustomerUserDetails(user);
    }


    public void checkIfUserAlreadyExists(String username) {
        UserInfo user = userRepo.findByUsername(username);
        if (user != null) {
            throw new RuntimeException("User already exists with username: " + username);
        }
    }

    public Boolean SingUpUser(UserInfoDto userInfoDto) {

        userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));

        if (Objects.nonNull(userInfoDto.getUsername())) {
            checkIfUserAlreadyExists(userInfoDto.getUsername());
        }
        String userId = UUID.randomUUID().toString();


        userRepo.save(new UserInfo(userId ,userInfoDto.getUsername(), userInfoDto.getPassword(), userInfoDto.getRoles()));
        return true;
    }
    // Additional methods can be added as needed
}
