package com.damian.megacity.repo;

import com.damian.megacity.dto.UserDTO;
import com.damian.megacity.exceptions.UserException;
import com.damian.megacity.repo.service.UserDAOService;
import com.damian.megacity.util.FactoryConfiguration;
import com.damian.megacity.util.mappers.Mapper;
import lombok.extern.java.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.damian.megacity.service.constants.UserConstants.*;

@Log
public class UserRepo implements UserDAOService {

    @Override
    public UserDTO add(UserDTO userDTO) {
        var user = Mapper.toUser(userDTO);
        var query = "INSERT INTO User (userId, name, email, picture) VALUES (?, ?, ?, ?)";

        try (var connection = FactoryConfiguration.getFactoryConfiguration().getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, user.getUserId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getProfilePicture());

            var rowsAffected = preparedStatement.executeUpdate();
            log.info(rowsAffected + " row(s) inserted.");

        } catch (SQLException e) {
            log.warning("An error occurred while adding a user: " + e.getMessage());
            throw new UserException("User already exists!");
        }
        return userDTO;
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        var user = Mapper.toUser(userDTO);
        var query = "UPDATE User SET name = ?, email = ?, picture = ? WHERE userId = ?";

        try (var connection = FactoryConfiguration.getFactoryConfiguration().getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getProfilePicture());
            preparedStatement.setString(4, user.getUserId());

            var rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                log.info(USER_WITH_USER_ID + user.getUserId() + " updated successfully.");
                return userDTO;
            } else {
                log.warning(USER_WITH_USER_ID + user.getUserId() + NOT_FOUND);
                throw new UserException(USER_NOT_FOUND);
            }

        } catch (SQLException e) {
            log.warning("An error occurred while updating the user: " + e.getMessage());
            throw new UserException("An error occurred while updating the user. ");
        }
    }

    @Override
    public void delete(String userId) {
        var query = "DELETE FROM User WHERE userId = ?";

        try (var connection = FactoryConfiguration.getFactoryConfiguration().getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userId);

            var rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                log.info(USER_WITH_USER_ID + userId + " deleted successfully.");
            } else {
                log.warning(USER_WITH_USER_ID + userId + NOT_FOUND);
                throw new UserException(USER_NOT_FOUND);
            }

        } catch (SQLException e) {
            log.warning("An error occurred while deleting the user: " + e.getMessage());
            throw new UserException("An error occurred while deleting the user.");
        }
    }

    @Override
    public UserDTO search(String idOrEmail) {
        var query = "SELECT userId, name, email, picture FROM User WHERE userId = ? OR email = ?";
        UserDTO user;

        try (var connection = FactoryConfiguration.getFactoryConfiguration().getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, idOrEmail);
            preparedStatement.setString(2, idOrEmail);

            log.info("Executing query: " + query + " with parameter: " + idOrEmail);

            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new UserDTO(
                            resultSet.getString("userId"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("picture")
                    );
                } else {
                    log.warning("User with userId or email " + idOrEmail + " not found.");
                    throw new UserException(USER_NOT_FOUND);
                }
            }

        } catch (SQLException e) {
            log.warning("An error occurred while searching for the user: " + e.getMessage());
            throw new UserException("An error occurred while searching for the user.");
        }

        return user;
    }

    @Override
    public List<UserDTO> getAll() {
        var query = "SELECT userId, name, email, picture FROM User";
        var users = new ArrayList<UserDTO>();

        try (var connection = FactoryConfiguration.getFactoryConfiguration().getConnection();
             var preparedStatement = connection.prepareStatement(query);
             var resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                users.add(new UserDTO(
                        resultSet.getString("userId"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("picture")
                ));
            }

        } catch (SQLException e) {
            log.warning("An error occurred while retrieving all users: " + e.getMessage());
            throw new UserException("An error occurred while retrieving all users.");
        }

        return users;
    }
}
