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
        return userRepo.update(userDTO);
    }

    @Override
    public void delete(String userId) {
        userRepo.delete(userId);
    }

    @Override
    public UserDTO search(String userIdOrEmail) {
        return userRepo.search(userIdOrEmail);
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepo.getAll();
    }
}
