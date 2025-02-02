package com.damian.megacity.repo;

import com.damian.megacity.dto.UserDTO;
import com.damian.megacity.util.FactoryConfiguration;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;

import java.util.List;

@Log
public class UserRepo implements DAOService<UserDTO> {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getFactoryConfiguration();

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public UserDTO add(UserDTO userDTO) {
        return null;
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        return null;
    }

    @Override
    public void delete(UserDTO userDTO) {

    }

    @Override
    public UserDTO search(String id) {
        return null;
    }

    @Override
    public List<UserDTO> getAll() {
        return List.of();
    }
}
