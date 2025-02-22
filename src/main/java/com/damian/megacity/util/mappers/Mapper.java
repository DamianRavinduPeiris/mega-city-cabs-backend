package com.damian.megacity.util.mappers;

import com.damian.megacity.dto.AdminDTO;
import com.damian.megacity.dto.DriverDTO;
import com.damian.megacity.dto.UserDTO;
import com.damian.megacity.entity.Admin;
import com.damian.megacity.entity.Driver;
import com.damian.megacity.entity.User;

public class Mapper {
    public static User toUser(UserDTO userDto) {
        return new User(userDto.userId(), userDto.name(), userDto.email(), userDto.picture());
    }

    public static Driver toDriver(DriverDTO driverDTO) {
        return new Driver(driverDTO.driverId(), driverDTO.driverName(), driverDTO.driverPhone(), driverDTO.driverEmail());
    }

    public static Admin toAdmin(AdminDTO adminDTO) {
        return new Admin(adminDTO.id(), adminDTO.username(), adminDTO.password());
    }

    public static AdminDTO toAdminDTO(Admin admin) {
        return new AdminDTO(admin.getId(), admin.getUsername(), admin.getPassword());
    }
}
