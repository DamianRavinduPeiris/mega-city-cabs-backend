package com.damian.megacity.dto;

import java.io.Serializable;

public record RideBookingDTO(
        String orderId,
        String userId,
        String userName,
        String driverId,
        String vehicleId,
        String vehicleName,
        String vehicleNumberPlate,
        String pickUpCity,
        String destinationCity,
        String duration,
        String date

) implements Serializable {

}
