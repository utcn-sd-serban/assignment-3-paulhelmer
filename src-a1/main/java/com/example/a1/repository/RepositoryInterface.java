package com.example.a1.repository;

import java.util.List;
import java.util.Optional;

public interface RepositoryInterface <T> {
    T save(T t);
    Optional<T> findById(Integer id);
    void delete(T t);
    List<T> findAll();

}
