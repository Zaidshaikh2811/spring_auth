package com.example.spring_auth.services;


import com.example.spring_auth.entities.RefreshToken;
import com.example.spring_auth.entities.UserInfo;
import com.example.spring_auth.repository.RefreshTokenRepo;
import com.example.spring_auth.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepo refreshTokenRepo;

    @Autowired
    UserRepo userRepo;


    public RefreshToken createRefreshTokenRepo(String username) {

        UserInfo user = userRepo.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        RefreshToken refreshToken = RefreshToken
                .builder()
                .user(user)
                .token(java.util.UUID.randomUUID().toString())
                .expiryDate(java.time.Instant.now().plusSeconds(60 * 60 * 24)) // 24 hours
                .build();
        return refreshTokenRepo.save(refreshToken);

    }


    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().isBefore(java.time.Instant.now())) {
           refreshTokenRepo.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }
        return refreshToken;
    }


    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepo.findByToken(token);
    }


}
