package com.damian.megacity.repo;

import com.damian.megacity.dto.RideBookingDTO;
import com.damian.megacity.exceptions.RideBookingException;
import com.damian.megacity.repo.service.RideBookingDAOService;
import com.damian.megacity.util.FactoryConfiguration;
import com.damian.megacity.util.mappers.Mapper;
import com.damian.megacity.entity.RideBooking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RideBookingRepo implements RideBookingDAOService<RideBookingDTO> {
    @Override
    public RideBookingDTO add(RideBookingDTO rideBookingDTO) {
        var rideBookingEntity = Mapper.toRideBooking(rideBookingDTO);
        try (Connection conn = FactoryConfiguration.getFactoryConfiguration().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO ridebooking (orderId, userId, userName, driverId, vehicle_id, vehicleName, vehicleNumberPlate, pickUpCity, destinationCity, duration, date,price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)")
        ) {
            stmt.setString(1, rideBookingEntity.getOrderId());
            stmt.setString(2, rideBookingEntity.getUserId());
            stmt.setString(3, rideBookingEntity.getUserName());
            stmt.setString(4, rideBookingEntity.getDriverId());
            stmt.setString(5, rideBookingEntity.getVehicleId());
            stmt.setString(6, rideBookingEntity.getVehicleName());
            stmt.setString(7, rideBookingEntity.getVehicleNumberPlate());
            stmt.setString(8, rideBookingEntity.getPickUpCity());
            stmt.setString(9, rideBookingEntity.getDestinationCity());
            stmt.setString(10, rideBookingEntity.getDuration());
            stmt.setString(11, rideBookingEntity.getDate());
            stmt.setDouble(12, rideBookingEntity.getPrice());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                return rideBookingDTO;
            } else {
                throw new RideBookingException("Failed to add the booking!");
            }
        } catch (SQLException e) {
            throw new RideBookingException("An error occurred while adding ride booking: " + e.getMessage());
        }
    }

    @Override
    public List<RideBookingDTO> getAll() {
        List<RideBookingDTO> rideBookings = new ArrayList<>();

        try (Connection conn = FactoryConfiguration.getFactoryConfiguration().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ridebooking");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                RideBooking rideBooking = new RideBooking(
                        rs.getString("orderId"),
                        rs.getString("userId"),
                        rs.getString("userName"),
                        rs.getString("driverId"),
                        rs.getString("vehicle_id"),
                        rs.getString("vehicleName"),
                        rs.getString("vehicleNumberPlate"),
                        rs.getString("pickUpCity"),
                        rs.getString("destinationCity"),
                        rs.getString("duration"),
                        rs.getString("date"),
                        rs.getDouble("price")
                );
                rideBookings.add(Mapper.toRideBookingDTO(rideBooking));
            }
            return rideBookings;
        } catch (SQLException e) {
            throw new RideBookingException("An error occurred while retrieving the bookings: " + e.getMessage());
        }
    }
}
