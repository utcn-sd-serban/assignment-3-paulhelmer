package com.example.a1.repository.jdbc.mappers;

import com.example.a1.entity.Answer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;

public class AnswerMapper implements RowMapper<Answer> {
    @Override
    public Answer mapRow(ResultSet rs, int rowNr) throws SQLException {
        return new Answer(
                Integer.valueOf(rs.getInt("id")),
                Integer.valueOf(rs.getInt("author_id")),
                Integer.valueOf(rs.getInt("question_id")),
                rs.getString("text"),
                ZonedDateTime.parse(rs.getString("creation_date")),
                Long.valueOf(0)
        );
    }
}
