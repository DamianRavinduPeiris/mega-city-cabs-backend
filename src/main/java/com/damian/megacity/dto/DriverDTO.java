package com.damian.megacity.dto;

import java.io.Serializable;

public record DriverDTO(
        String driverId,
        String driverName,
        String driverPhone,
        String driverEmail) implements Serializable {
}
