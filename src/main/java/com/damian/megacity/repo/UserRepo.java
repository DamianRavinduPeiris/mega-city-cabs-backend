package com.damian.megacity.repo;

import com.damian.megacity.dto.UserDTO;
import com.damian.megacity.util.FactoryConfiguration;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import java.util.List;


public class UserRepo implements DAOService<UserDTO> {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getFactoryConfiguration();
    @Inject
    private ModelMapper modelMapper;

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
