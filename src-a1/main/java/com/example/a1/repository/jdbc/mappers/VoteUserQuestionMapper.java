package com.example.a1.repository.jdbc.mappers;

import com.example.a1.entity.VoteUserQuestion;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VoteUserQuestionMapper implements RowMapper<VoteUserQuestion> {
    @Override
    public VoteUserQuestion mapRow(ResultSet rs, int rowNr) throws SQLException {
        return new VoteUserQuestion(
                Integer.valueOf(rs.getInt("id")),
                Integer.valueOf(rs.getInt("user_id")),
                Integer.valueOf(rs.getInt("question_id")),
                rs.getString("vote_type"));
    }
}