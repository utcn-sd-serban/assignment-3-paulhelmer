package com.example.a1.repository.jdbc;


import com.example.a1.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "a1.repository-type", havingValue = "JDBC")
public class JdbcRepositoryFactory implements RepositoryFactory{
    private final JdbcTemplate template;

    @Override
    public AnswerRepository createAnswerRepository() {
        return new JdbcAnswerRepository(template);
    }

    @Override
    public QuestionRepository createQuestionRepository() {
        return new JdbcQuestionRepository(template);
    }

    @Override
    public TagRepository createTagRepository() {
        return new JdbcTagRepository(template);
    }

    @Override
    public UserRepository createUserRepository() {
        return new JdbcUserRepository(template);
    }

    @Override
    public VoteUserAnswerRepository createVoteUserAnswerRepository() {
        return new JdbcVoteUserAnswerRepository(template);
    }

    @Override
    public VoteUserQuestionRepository createVoteUserQuestionRepository() {
        return new JdbcVoteUserQuestionRepository(template);
    }
}
