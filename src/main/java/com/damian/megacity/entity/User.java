package com.damian.megacity.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private String userId;
    private String name;
    private String email;
    private String profilePicture;
}
