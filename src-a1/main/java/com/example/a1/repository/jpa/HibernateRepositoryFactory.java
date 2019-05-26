package com.example.a1.repository.jpa;

import com.example.a1.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "a1.repository-type", havingValue = "JPA")
public class HibernateRepositoryFactory implements RepositoryFactory {
    private final EntityManager entityManager;

    @Override
    public QuestionRepository createQuestionRepository() {
        return new HibernateQuestionRepository(entityManager);
    }

    @Override
    public AnswerRepository createAnswerRepository() {
        return new HibernateAnswerRepository(entityManager);
    }

    @Override
    public TagRepository createTagRepository() {
        return new HibernateTagRepository(entityManager);
    }

    @Override
    public UserRepository createUserRepository() {
        return new HibernateUserRepository(entityManager);
    }

    @Override
    public VoteUserAnswerRepository createVoteUserAnswerRepository() {
        return new HibernateVoteUserAnswerRepository(entityManager);
    }

    @Override
    public VoteUserQuestionRepository createVoteUserQuestionRepository() {
        return new HibernateVoteUserQuestionRepository(entityManager);
    }
}
