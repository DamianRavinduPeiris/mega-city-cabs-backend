package com.damian.megacity.util.mappers;

import com.damian.megacity.dto.DriverDTO;
import com.damian.megacity.dto.RideBookingDTO;
import com.damian.megacity.dto.UserDTO;
import com.damian.megacity.dto.VehicleDTO;
import com.damian.megacity.entity.Driver;
import com.damian.megacity.entity.RideBooking;
import com.damian.megacity.entity.User;
import com.damian.megacity.entity.Vehicle;

public class Mapper {
    private Mapper() {

    }

    public static User toUser(UserDTO userDto) {
        return new User(userDto.userId(), userDto.name(), userDto.email(), userDto.picture());
    }

    public static Driver toDriver(DriverDTO driverDTO) {
        return new Driver(driverDTO.driverId(), driverDTO.driverName(), driverDTO.driverPhone(), driverDTO.driverEmail());
    }

    public static Vehicle toVehicle(VehicleDTO vehicleDTO) {
        return new Vehicle(vehicleDTO.vehicleId(), vehicleDTO.vehicleName(), vehicleDTO.vehicleMakeYear(), vehicleDTO.vehicleNumberPlate(), vehicleDTO.vehicleImage());
    }

    public static RideBooking toRideBooking(RideBookingDTO rideBookingDTO) {
        return new RideBooking(
                rideBookingDTO.orderId(),
                rideBookingDTO.userId(),
                rideBookingDTO.userName(),
                rideBookingDTO.driverId(),
                rideBookingDTO.vehicleId(),
                rideBookingDTO.vehicleName(),
                rideBookingDTO.vehicleNumberPlate(),
                rideBookingDTO.pickUpCity(),
                rideBookingDTO.destinationCity(),
                rideBookingDTO.duration(),
                rideBookingDTO.date(),
                rideBookingDTO.price()
        );
    }

    public static RideBookingDTO toRideBookingDTO(RideBooking rideBooking) {
        return new RideBookingDTO(
                rideBooking.getOrderId(),
                rideBooking.getUserId(),
                rideBooking.getUserName(),
                rideBooking.getDriverId(),
                rideBooking.getVehicleId(),
                rideBooking.getVehicleName(),
                rideBooking.getVehicleNumberPlate(),
                rideBooking.getPickUpCity(),
                rideBooking.getDestinationCity(),
                rideBooking.getDuration(),
                rideBooking.getDate(),
                rideBooking.getPrice()
        );
    }
}
