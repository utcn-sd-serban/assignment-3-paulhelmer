package com.example.a1.repository;

import com.example.a1.entity.Answer;
import com.example.a1.entity.User;

import java.util.Optional;

public interface UserRepository extends RepositoryInterface<User>{

    Optional<User> findByUsername(String username);
}
