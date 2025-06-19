package com.example.spring_auth.repository;

import com.example.spring_auth.entities.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends CrudRepository<RefreshToken,Integer>
{

    Optional<RefreshToken> findByToken(String token);



}
