package com.example.a1.repository;

import com.example.a1.entity.Answer;
import com.example.a1.entity.User;
import com.example.a1.entity.VoteUserAnswer;

import java.util.Optional;

public interface VoteUserAnswerRepository extends RepositoryInterface<VoteUserAnswer> {

    Optional <VoteUserAnswer> findByUserAndAnswer(User user, Answer answer);
    Long getUpVotes(Answer answer);
    Long getDownVotes(Answer answer);

}
