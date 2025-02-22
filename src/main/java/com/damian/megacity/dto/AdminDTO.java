package com.damian.megacity.dto;

import java.io.Serializable;

public record AdminDTO(String id,
                       String username,
                       String password) implements Serializable {
}
