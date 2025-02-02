package com.damian.megacity.service;

import com.damian.megacity.dto.UserDTO;

import java.util.List;

public interface UserService <T extends UserDTO>{
    T add(T t);

    T update(T t);

    void delete(T t);

    T search(String id);

    List<T> getAll();
}
