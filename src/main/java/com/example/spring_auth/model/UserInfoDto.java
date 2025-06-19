package com.example.spring_auth.model;

import com.example.spring_auth.entities.UserInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserInfoDto extends UserInfo {


    private String userName;
    private String lastName;
    private long phoneNumber;
    private String email;

}
