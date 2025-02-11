package com.damian.megacity.util.filters.mappers;

import com.damian.megacity.dto.DriverDTO;
import com.damian.megacity.dto.UserDTO;
import com.damian.megacity.entity.Driver;
import com.damian.megacity.entity.User;

public class Mapper {
    public static User toUser(UserDTO userDto) {
        return new User(userDto.userId(), userDto.name(), userDto.email(), userDto.picture());
    }

    public static Driver toDriver(DriverDTO driverDTO) {
        return new Driver(driverDTO.driverId(), driverDTO.driverName(), driverDTO.driverPhone(), driverDTO.driverEmail());
    }
}
