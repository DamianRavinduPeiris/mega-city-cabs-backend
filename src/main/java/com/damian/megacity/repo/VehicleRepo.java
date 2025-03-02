package com.damian.megacity.repo;

import com.damian.megacity.dto.VehicleDTO;
import com.damian.megacity.exceptions.VehicleException;
import com.damian.megacity.repo.service.VehicleDAOService;
import com.damian.megacity.util.FactoryConfiguration;
import com.damian.megacity.util.mappers.Mapper;
import jakarta.servlet.http.Part;
import lombok.extern.java.Log;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Log
public class VehicleRepo implements VehicleDAOService {
    @Override
    public VehicleDTO add(VehicleDTO vehicleDTO) {
        var vehicle = Mapper.toVehicle(vehicleDTO);
        try (Connection conn = FactoryConfiguration.getFactoryConfiguration().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO vehicle (vehicle_id, vehicle_name, vehicle_make_year, vehicle_number_plate) VALUES (?, ?, ?, ?)")) {

            stmt.setString(1, vehicle.getVehicleId());
            stmt.setString(2, vehicle.getVehicleName());
            stmt.setString(3, vehicle.getVehicleMakeYear());
            stmt.setString(4, vehicle.getVehicleNumberPlate());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                log.info("Vehicle added successfully!");
                return vehicleDTO;
            } else {
                throw new VehicleException("Vehicle already exists!");
            }

        } catch (Exception e) {
            log.severe("An error occurred while adding vehicle: " + e.getMessage());
            throw new VehicleException("Insertion failed!");
        }

    }

    @Override
    public VehicleDTO update(VehicleDTO vehicleDTO) {
        var vehicle = Mapper.toVehicle(vehicleDTO);
        try (Connection conn = FactoryConfiguration.getFactoryConfiguration().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE vehicle SET vehicle_name = ?, vehicle_make_year = ?, vehicle_number_plate = ? WHERE vehicle_id = ?")) {

            stmt.setString(1, vehicle.getVehicleName());
            stmt.setString(2, vehicle.getVehicleMakeYear());
            stmt.setString(3, vehicle.getVehicleNumberPlate());
            stmt.setString(4, vehicle.getVehicleId());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                log.info("Vehicle updated successfully!");
                return vehicleDTO;
            } else {
                throw new VehicleException("Vehicle not found!");
            }

        } catch (Exception e) {
            log.severe("An error occurred while updating vehicle: " + e.getMessage());
            throw new VehicleException("Update failed!");
        }
    }

    @Override
    public void delete(String id) {
        try (Connection conn = FactoryConfiguration.getFactoryConfiguration().getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM vehicle WHERE vehicle_id = ?")) {

            stmt.setString(1, id);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                log.info("Vehicle deleted successfully!");
            } else {
                throw new VehicleException("Vehicle not found!");
            }

        } catch (Exception e) {
            log.severe("An error occurred while deleting vehicle: " + e.getMessage());
            throw new VehicleException("Deletion failed!");
        }
    }

    @Override
    public VehicleDTO search(String id) {
        try (Connection conn = FactoryConfiguration.getFactoryConfiguration().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM vehicle WHERE vehicle_id = ?")) {

            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new VehicleDTO(
                            rs.getString("vehicle_id"),
                            rs.getString("vehicle_name"),
                            rs.getString("vehicle_make_year"),
                            rs.getString("vehicle_number_plate"),
                            Base64.getEncoder().encodeToString(rs.getBytes("vehicle_image"))
                    );
                } else {
                    throw new VehicleException("Vehicle not found!");
                }
            }

        } catch (Exception e) {
            log.severe("An error occurred while searching for vehicle: " + e.getMessage());
            throw new VehicleException("Search failed!");
        }
    }

    @Override
    public List<VehicleDTO> getAll() {
        var vehicles = new ArrayList<VehicleDTO>();

        try (Connection conn = FactoryConfiguration.getFactoryConfiguration().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM vehicle");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                vehicles.add(new VehicleDTO(
                        rs.getString("vehicle_id"),
                        rs.getString("vehicle_name"),
                        rs.getString("vehicle_make_year"),
                        rs.getString("vehicle_number_plate"),
                        Base64.getEncoder().encodeToString(rs.getBytes("vehicle_image"))
                ));
            }
            return vehicles;

        } catch (Exception e) {
            log.severe("An error occurred while retrieving vehicles: " + e.getMessage());
            throw new VehicleException("Failed to retrieve vehicles!");
        }
    }


    @Override
    public VehicleDTO addVehicleWithImages(VehicleDTO vehicleDTO, Part imageData) {
        var vehicle = Mapper.toVehicle(vehicleDTO);
        try (InputStream fileContent = imageData.getInputStream();
             Connection conn = FactoryConfiguration.getFactoryConfiguration().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO vehicle (vehicle_id, vehicle_name, vehicle_make_year, vehicle_number_plate, vehicle_image) VALUES (?, ?, ?, ?, ?)")) {

            stmt.setString(1, vehicle.getVehicleId());
            stmt.setString(2, vehicle.getVehicleName());
            stmt.setString(3, vehicle.getVehicleMakeYear());
            stmt.setString(4, vehicle.getVehicleNumberPlate());
            stmt.setBinaryStream(5, fileContent, (int) imageData.getSize());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                log.info("Vehicle uploaded successfully!");
                return vehicleDTO;
            } else {
                log.info("Failed to upload vehicle.");
                throw new VehicleException("Failed to insert vehicle.");
            }

        } catch (Exception e) {
            log.severe("An error occurred while uploading vehicle: " + e.getMessage());
            throw new VehicleException("Vehicle already exists or insertion failed!");
        }
    }
}
