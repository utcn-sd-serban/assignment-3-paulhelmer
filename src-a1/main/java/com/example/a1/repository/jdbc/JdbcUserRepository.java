package com.example.a1.repository.jdbc;

import com.example.a1.entity.User;
import com.example.a1.repository.UserRepository;
import com.example.a1.repository.jdbc.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcUserRepository implements UserRepository {
    private final JdbcTemplate template;


    @Override
    public User save(User user) {
        if(user.getId()==null)
            user.setId(insert(user));
        else
            update(user);
        return user;
    }

    @Override
    public Optional<User> findById(Integer id) {
        List<User> users = template.query("SELECT * FROM user WHERE id = ?", new UserMapper(), id);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public void delete(User user) {
        template.update("DELETE FROM user WHERE id = ?", user.getId());
    }

    @Override
    public List<User> findAll() {
        return template.query("SELECT * FROM user", new UserMapper());
    }

    private int insert(User user) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("user");
        insert.usingGeneratedKeyColumns("id");
        Map<String, Object> map = new HashMap<>();
        map.put("username", user.getUsername());
        map.put("password", user.getPassword());
        return insert.executeAndReturnKey(map).intValue();
    }
    private void update (User user){
        template.update("UPDATE user SET username = ?, password = ? WHERE id = ?", user.getUsername(), user.getPassword(), user.getId());
    }

    public Optional<User> findByUsername(String username){
        List<User> users = template.query("SELECT * FROM user WHERE username = ?", new UserMapper(), username);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }
}
