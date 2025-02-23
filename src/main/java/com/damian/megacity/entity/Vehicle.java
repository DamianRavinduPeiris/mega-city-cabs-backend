package com.damian.megacity.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Vehicle {
    private String vehicleId;
    private String vehicleName;
    private String vehicleMakeYear;
    private String vehicleNumberPlate;
    private byte[] vehicleImage;
}
