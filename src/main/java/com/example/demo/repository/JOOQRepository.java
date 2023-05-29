package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

public interface JOOQRepository<T> {
    Optional<T> findByEmail(String email);

    T save(T t);

    Optional<T> findById(Integer id);

    boolean deleteById(Integer id);

    List<T> findAll();
}
