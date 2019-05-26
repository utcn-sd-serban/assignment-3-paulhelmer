package com.example.a1.repository.jdbc.mappers;

import com.example.a1.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
        return new User(
                Integer.valueOf(rs.getInt("id")),
                rs.getString("username"),
                rs.getString("password"),
                false,
                false,
                false
        );

    }

}
