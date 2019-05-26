package com.example.a1.repository.memory;

import com.example.a1.repository.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "a1.repository-type", havingValue = "MEMORY")
public class InMemoryRepositoryFactory implements RepositoryFactory {
    private final InMemoryAnswerRepository answerRepository = new InMemoryAnswerRepository();
    private final InMemoryQuestionRepository questionRepository = new InMemoryQuestionRepository();
    private final InMemoryTagRepository tagRepository = new InMemoryTagRepository();
    private final InMemoryUserRepository userRepository = new InMemoryUserRepository();
    private final InMemoryVoteUserAnswerRepository voteUserAnswerRepository = new InMemoryVoteUserAnswerRepository();
    private final InMemoryVoteUserQuestionRepository voteUserQuestionRepository = new InMemoryVoteUserQuestionRepository();

    @Override
    public QuestionRepository createQuestionRepository() {
        return questionRepository;
    }

    @Override
    public AnswerRepository createAnswerRepository() {
        return answerRepository;
    }

    @Override
    public TagRepository createTagRepository() {
        return tagRepository;
    }

    @Override
    public UserRepository createUserRepository() {
        return userRepository;
    }

    @Override
    public VoteUserAnswerRepository createVoteUserAnswerRepository() {
        return voteUserAnswerRepository;
    }

    @Override
    public VoteUserQuestionRepository createVoteUserQuestionRepository() {
        return voteUserQuestionRepository;
    }
}
