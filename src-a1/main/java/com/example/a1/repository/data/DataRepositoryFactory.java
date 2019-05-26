package com.example.a1.repository.data;

import com.example.a1.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "a1.repository-type", havingValue = "DATA")
public class DataRepositoryFactory  implements RepositoryFactory {
    private final DataAnswerRepository answerRepository;
    private final DataQuestionRepository questionRepository;
    private final DataTagRepository tagRepository;
    private final DataUserRepository userRepository;
    private final DataVoteUserAnswerRepository voteUserAnswerRepository;
    private final DataVoteUserQuestionRepository voteUserQuestionRepository;

    @Override
    public AnswerRepository createAnswerRepository() {
        return answerRepository;
    }

    @Override
    public QuestionRepository createQuestionRepository() {
        return questionRepository;
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
