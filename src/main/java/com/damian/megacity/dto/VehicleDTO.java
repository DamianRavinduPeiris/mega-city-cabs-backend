package com.damian.megacity.dto;

import java.io.Serializable;

public record VehicleDTO(String vehicleId,
                         String vehicleName,
                         String vehicleMakeYear,
                         String vehicleNumberPlate,
                         String vehicleImage) implements Serializable {
}
