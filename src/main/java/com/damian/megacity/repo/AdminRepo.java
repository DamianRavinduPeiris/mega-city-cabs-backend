package com.damian.megacity.repo;

import com.damian.megacity.dto.AdminDTO;
import com.damian.megacity.exceptions.AdminException;
import com.damian.megacity.exceptions.UserException;
import com.damian.megacity.util.FactoryConfiguration;
import lombok.extern.java.Log;

import java.sql.SQLException;

import static com.damian.megacity.service.impl.constants.AdminConstants.ADMIN_NOT_FOUND;

@Log
public class AdminRepo implements AdminDAOService {
    @Override
    public AdminDTO searchByUserName(String userName) {
        var query = "SELECT id, username, password FROM admin WHERE username = ?";
        AdminDTO adminDTO = null;

        try (var connection = FactoryConfiguration.getFactoryConfiguration().getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userName);

            log.info("Executing query: " + query + " with parameter: " + userName);

            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    adminDTO = new AdminDTO(
                            resultSet.getString("id"),
                            resultSet.getString("username"),
                            resultSet.getString("password")
                    );
                } else {
                    log.warning("Admin with username " + userName + " not found.");
                    throw new AdminException(ADMIN_NOT_FOUND);
                }
            }

        } catch (SQLException e) {
            log.warning("An error occurred while searching for the admin: " + e.getMessage());
            throw new UserException("An error occurred while searching for the admim.");
        }

        return adminDTO;
    }
}
