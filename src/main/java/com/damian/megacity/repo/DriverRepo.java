package com.damian.megacity.repo;

import com.damian.megacity.dto.DriverDTO;
import com.damian.megacity.exceptions.DriverException;
import com.damian.megacity.repo.service.DriverDAOService;
import com.damian.megacity.util.FactoryConfiguration;
import com.damian.megacity.util.mappers.Mapper;
import lombok.extern.java.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.damian.megacity.service.constants.DistanceConstants.DRIVER_WITH_DRIVER_ID;
import static com.damian.megacity.service.constants.DriverConstants.DRIVER_NOT_FOUND;

@Log
public class DriverRepo implements DriverDAOService {

    @Override
    public DriverDTO add(DriverDTO driverDTO) {
        var driver = Mapper.toDriver(driverDTO);
        var query = "INSERT INTO Driver (driverId, driverName, driverPhone, driverEmail) VALUES (?, ?, ?, ?)";

        try (var connection = FactoryConfiguration.getFactoryConfiguration().getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, driver.getDriverId());
            preparedStatement.setString(2, driver.getDriverName());
            preparedStatement.setString(3, driver.getDriverPhone());
            preparedStatement.setString(4, driver.getDriverEmail());

            var rowsAffected = preparedStatement.executeUpdate();
            log.info(rowsAffected + " row(s) inserted.");

        } catch (SQLException e) {
            log.warning("An error occurred while adding a driver: " + e.getMessage());
            throw new DriverException("Driver already exists!");
        }
        return driverDTO;
    }

    @Override
    public DriverDTO update(DriverDTO driverDTO) {
        var driver = Mapper.toDriver(driverDTO);
        var query = "UPDATE Driver SET driverName = ?, driverPhone = ?, driverEmail = ? WHERE driverId = ?";

        try (var connection = FactoryConfiguration.getFactoryConfiguration().getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, driver.getDriverName());
            preparedStatement.setString(2, driver.getDriverPhone());
            preparedStatement.setString(3, driver.getDriverEmail());
            preparedStatement.setString(4, driver.getDriverId());

            var rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                log.info(DRIVER_WITH_DRIVER_ID + driver.getDriverId() + " updated successfully.");
                return driverDTO;
            } else {
                log.warning(DRIVER_WITH_DRIVER_ID + driver.getDriverId() + DRIVER_NOT_FOUND);
                throw new DriverException(DRIVER_NOT_FOUND);
            }

        } catch (SQLException e) {
            log.warning("An error occurred while updating the driver: " + e.getMessage());
            throw new DriverException("An error occurred while updating the driver.");
        }
    }

    @Override
    public void delete(String driverId) {
        var query = "DELETE FROM Driver WHERE driverId = ?";

        try (var connection = FactoryConfiguration.getFactoryConfiguration().getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, driverId);

            var rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                log.info("Driver with driverId " + driverId + " deleted successfully.");
            } else {
                log.warning("Driver with driverId " + driverId + " not found.");
                throw new DriverException(DRIVER_NOT_FOUND);
            }

        } catch (SQLException e) {
            log.warning("An error occurred while deleting the driver: " + e.getMessage());
            throw new DriverException("An error occurred while deleting the driver.");
        }
    }

    @Override
    public DriverDTO search(String idOrEmail) {
        var query = "SELECT driverId, driverName, driverPhone, driverEmail FROM Driver WHERE driverId = ? OR driverEmail = ?";
        DriverDTO driver;

        try (var connection = FactoryConfiguration.getFactoryConfiguration().getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, idOrEmail);
            preparedStatement.setString(2, idOrEmail);

            log.info("Executing query: " + query + " with parameter: " + idOrEmail);

            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    driver = new DriverDTO(
                            resultSet.getString("driverId"),
                            resultSet.getString("driverName"),
                            resultSet.getString("driverPhone"),
                            resultSet.getString("driverEmail")
                    );
                } else {
                    log.warning("Driver with driverId or email " + idOrEmail + " not found.");
                    throw new DriverException(DRIVER_NOT_FOUND);
                }
            }

        } catch (SQLException e) {
            log.warning("An error occurred while searching for the driver: " + e.getMessage());
            throw new DriverException("An error occurred while searching for the driver.");
        }

        return driver;
    }

    @Override
    public List<DriverDTO> getAll() {
        var query = "SELECT driverId, driverName, driverPhone, driverEmail FROM Driver";
        var drivers = new ArrayList<DriverDTO>();

        try (var connection = FactoryConfiguration.getFactoryConfiguration().getConnection();
             var preparedStatement = connection.prepareStatement(query);
             var resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                drivers.add(new DriverDTO(
                        resultSet.getString("driverId"),
                        resultSet.getString("driverName"),
                        resultSet.getString("driverPhone"),
                        resultSet.getString("driverEmail")
                ));
            }

        } catch (SQLException e) {
            log.warning("An error occurred while retrieving all drivers: " + e.getMessage());
            throw new DriverException("An error occurred while retrieving all drivers.");
        }

        return drivers;
    }
}
