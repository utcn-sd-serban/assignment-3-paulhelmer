package com.example.a1.repository.jdbc.mappers;

import com.example.a1.entity.Question;
import com.example.a1.entity.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.HashSet;

public class QuestionMapper implements RowMapper<Question> {
    @Override
    public Question mapRow(ResultSet rs, int rowNr) throws SQLException {
        return new Question(
                Integer.valueOf(rs.getInt("id")),
                Integer.valueOf(rs.getInt("author_d")),
                rs.getString("title"),
                rs.getString("text"),
                ZonedDateTime.parse(rs.getString("creation_date")),
                Long.valueOf(0),
                new HashSet<Tag>());
    }
}
