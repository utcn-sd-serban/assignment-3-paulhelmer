package com.example.a1.repository.jdbc.mappers;

import com.example.a1.entity.VoteUserAnswer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VoteUserAnswerMapper implements RowMapper<VoteUserAnswer> {
    @Override
    public VoteUserAnswer mapRow(ResultSet rs, int rowNr) throws SQLException {
        return new VoteUserAnswer(
                Integer.valueOf(rs.getInt("id")),
                Integer.valueOf(rs.getInt("user_id")),
                Integer.valueOf(rs.getInt("answer_id")),
                rs.getString("vote_type"));
    }
}