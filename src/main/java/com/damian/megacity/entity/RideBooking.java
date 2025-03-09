package com.damian.megacity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RideBooking {
    private String orderId;
    private String userId;
    private String userName;
    private String driverId;
    private String vehicleId;
    private String vehicleName;
    private String vehicleNumberPlate;
    private String pickUpCity;
    private String destinationCity;
    private String duration;
    private String date;
    private double price;
}
