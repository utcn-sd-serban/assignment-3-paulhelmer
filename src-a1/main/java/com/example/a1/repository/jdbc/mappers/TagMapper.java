package com.example.a1.repository.jdbc.mappers;

import com.example.a1.entity.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet rs, int i) throws SQLException {
        return new Tag(
                Integer.valueOf(rs.getInt("id")),
                rs.getString("tag_name")
        );
    }
}