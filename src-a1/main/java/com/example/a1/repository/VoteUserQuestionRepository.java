package com.example.a1.repository;

import com.example.a1.entity.*;

import java.util.Optional;

public interface VoteUserQuestionRepository extends RepositoryInterface<VoteUserQuestion> {
    Optional<VoteUserQuestion> findByUserAndQuestion(User user, Question question);
    Long getUpVotes(Question question);
    Long getDownVotes(Question question);
}
