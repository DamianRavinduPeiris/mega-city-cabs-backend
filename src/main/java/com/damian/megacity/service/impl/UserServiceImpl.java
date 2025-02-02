package com.damian.megacity.service.impl;

import com.damian.megacity.dto.UserDTO;
import com.damian.megacity.repo.DAOService;
import com.damian.megacity.repo.UserRepo;
import com.damian.megacity.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService<UserDTO> {
    private final DAOService<UserDTO> userRepo = new UserRepo();

    @Override
    public UserDTO add(UserDTO userDTO) {
        return userRepo.add(userDTO);
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
