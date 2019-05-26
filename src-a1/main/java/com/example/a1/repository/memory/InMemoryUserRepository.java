package com.example.a1.repository.memory;

import com.example.a1.entity.User;
import com.example.a1.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryUserRepository implements UserRepository {
    private final Map<Integer, User> userRepository = new ConcurrentHashMap<Integer, User>();
    private AtomicInteger currentId = new AtomicInteger(1);

    @Override
    public User save(User user) {
        if (user.getId() == 0) {
            user.setId(currentId.getAndIncrement());
        }
        userRepository.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.ofNullable(userRepository.get(id));
    }

    @Override
    public void delete(User user) {
        userRepository.remove(user.getId());
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<User>(userRepository.values());
    }

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(this.findAll().stream().filter(x->x.getUsername().equals(username)).collect(Collectors.toList()).get(0));
    }
}
