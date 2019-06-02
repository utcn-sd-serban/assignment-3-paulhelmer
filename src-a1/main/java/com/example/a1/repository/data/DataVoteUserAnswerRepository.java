package com.example.a1.repository.data;

import com.example.a1.entity.Answer;
import com.example.a1.entity.User;
import com.example.a1.entity.VoteUserAnswer;
import com.example.a1.repository.VoteUserAnswerRepository;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface DataVoteUserAnswerRepository  extends Repository<VoteUserAnswer,Integer>, VoteUserAnswerRepository {

    List<VoteUserAnswer> findByAnswerIdAndUserId(Integer answerId, Integer userId);

    @Override
    default Optional<VoteUserAnswer> findByUserAndAnswer(User user, Answer answer){
        List<VoteUserAnswer> answers = findByAnswerIdAndUserId(answer.getId(), user.getId());
        return answers.isEmpty() ? Optional.empty() : Optional.of(answers.get(0));
    }

    Long countByAnswerIdAndVoteType(Integer answerId, String voteType);
    @Override
    default  Long getUpVotes(Answer answer){
        return countByAnswerIdAndVoteType(answer.getId(), "up");
    }

    @Override
    default Long getDownVotes(Answer answer){
        return countByAnswerIdAndVoteType(answer.getId(), "down");
    }
}
