package com.damian.megacity.repo;

import java.util.List;

public interface DAOService<T> {
    T add(T t);

    T update(T t);

    void delete(T t);

    T search(String id);

    List<T> getAll();
}
