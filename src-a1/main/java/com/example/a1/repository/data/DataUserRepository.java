package com.example.a1.repository.data;

import com.example.a1.entity.User;
import com.example.a1.repository.UserRepository;
import org.springframework.data.repository.Repository;

public interface DataUserRepository extends Repository<User,Integer>, UserRepository {
}
