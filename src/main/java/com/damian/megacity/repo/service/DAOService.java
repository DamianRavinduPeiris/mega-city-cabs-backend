package com.damian.megacity.repo.service;

import java.util.List;

public interface DAOService<T> {
    T add(T t);

    T update(T t);

    void delete(String id);

    T search(String id);

    List<T> getAll();
}
