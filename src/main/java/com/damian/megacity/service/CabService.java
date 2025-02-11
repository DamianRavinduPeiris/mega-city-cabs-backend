package com.damian.megacity.service;

import java.util.List;

public interface CabService<T> {
    T add(T t);

    T update(T t);

    void delete(String id);

    T search(String id);

    List<T> getAll();
}
