package com.damian.megacity.repo;

import com.damian.megacity.dto.UserDTO;
import com.damian.megacity.exceptions.UserAlreadyExistsException;
import com.damian.megacity.exceptions.UserNotFoundException;
import com.damian.megacity.util.FactoryConfiguration;
import lombok.extern.java.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log
public class UserRepo implements DAOService<UserDTO> {

    @Override
    public UserDTO add(UserDTO userDTO) {
        var query = "INSERT INTO User (userId, name, email, profilePicture) VALUES (?, ?, ?, ?)";

        try (var connection = FactoryConfiguration.getFactoryConfiguration().getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userDTO.userId());
            preparedStatement.setString(2, userDTO.name());
            preparedStatement.setString(3, userDTO.email());
            preparedStatement.setString(4, userDTO.profilePicture());

            var rowsAffected = preparedStatement.executeUpdate();
            log.info(rowsAffected + " row(s) inserted.");

        } catch (SQLException e) {
            log.warning("An error occurred while adding a user: " + e.getMessage());
            throw new UserAlreadyExistsException("User already exists!");
        }
        return userDTO;
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        var query = "UPDATE User SET name = ?, email = ?, profilePicture = ? WHERE userId = ?";

        try (var connection = FactoryConfiguration.getFactoryConfiguration().getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userDTO.name());
            preparedStatement.setString(2, userDTO.email());
            preparedStatement.setString(3, userDTO.profilePicture());
            preparedStatement.setString(4, userDTO.userId());

            var rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                log.info("User with userId " + userDTO.userId() + " updated successfully.");
                return userDTO;
            } else {
                log.warning("User with userId " + userDTO.userId() + " not found.");
                throw new UserNotFoundException("User not found!");
            }

        } catch (SQLException e) {
            log.warning("An error occurred while updating the user: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void delete(UserDTO userDTO) {
        var query = "DELETE FROM User WHERE userId = ?";

        try (var connection = FactoryConfiguration.getFactoryConfiguration().getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userDTO.userId());

            var rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                log.info("User with userId " + userDTO.userId() + " deleted successfully.");
            } else {
                log.warning("User with userId " + userDTO.userId() + " not found.");
                throw new UserNotFoundException("User not found!");
            }

        } catch (SQLException e) {
            log.warning("An error occurred while deleting the user: " + e.getMessage());
        }
    }

    @Override
    public UserDTO search(String id) {
        var query = "SELECT userId, name, email, profilePicture FROM User WHERE userId = ?";
        UserDTO user = null;

        try (var connection = FactoryConfiguration.getFactoryConfiguration().getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, id);

            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new UserDTO(
                            resultSet.getString("userId"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("profilePicture")
                    );
                } else {
                    log.warning("User with userId " + id + " not found.");
                    throw new UserNotFoundException("User not found!");
                }
            }

        } catch (SQLException e) {
            log.warning("An error occurred while searching for the user: " + e.getMessage());
        }

        return user;
    }

    @Override
    public List<UserDTO> getAll() {
        var query = "SELECT userId, name, email, profilePicture FROM User";
        List<UserDTO> users = new ArrayList<>();

        try (var connection = FactoryConfiguration.getFactoryConfiguration().getConnection();
             var preparedStatement = connection.prepareStatement(query);
             var resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                users.add(new UserDTO(
                        resultSet.getString("userId"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("profilePicture")
                ));
            }

        } catch (SQLException e) {
            log.warning("An error occurred while retrieving all users: " + e.getMessage());
        }

        return users;
    }
}
