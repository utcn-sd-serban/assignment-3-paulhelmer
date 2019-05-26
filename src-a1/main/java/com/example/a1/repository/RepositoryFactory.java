package com.example.a1.repository;

public interface RepositoryFactory {
    QuestionRepository createQuestionRepository();
    AnswerRepository createAnswerRepository();
    TagRepository createTagRepository();
    UserRepository createUserRepository();
    VoteUserAnswerRepository createVoteUserAnswerRepository();
    VoteUserQuestionRepository createVoteUserQuestionRepository();
}
