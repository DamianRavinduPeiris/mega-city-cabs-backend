package com.damian.megacity.dto;

import java.io.Serializable;

public record UserDTO(
        String userId,
        String name,
        String email,
        String profilePicture) implements Serializable {
}
