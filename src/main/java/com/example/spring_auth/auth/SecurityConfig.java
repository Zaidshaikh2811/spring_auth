package com.example.spring_auth.auth;


import com.example.spring_auth.repository.UserRepo;
import com.example.spring_auth.services.UserDetailsServiceImp;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableMethodSecurity
@Data

public class SecurityConfig {


    @Autowired
    private final PasswordEncoder passwordEncoder;
    private  final UserDetailsServiceImp userDetailsService;


    @Bean
    @Autowired
    public UserDetailsService userDetailsService(UserRepo userRepo, PasswordEncoder passwordEncoder) {

        return new UserDetailsServiceImp(userRepo, passwordEncoder);

    }


    @Bean

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractConfigure).disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic()
                .and()
                .build();
    }



}
