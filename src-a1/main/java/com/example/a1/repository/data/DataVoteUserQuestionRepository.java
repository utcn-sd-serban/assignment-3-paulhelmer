package com.example.a1.repository.data;

import com.example.a1.entity.Question;
import com.example.a1.entity.User;
import com.example.a1.entity.VoteUserQuestion;
import com.example.a1.repository.VoteUserQuestionRepository;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface DataVoteUserQuestionRepository  extends Repository<VoteUserQuestion,Integer>, VoteUserQuestionRepository {

    List<VoteUserQuestion> findByQuestionIdAndUserId(Integer questionId, Integer userId);

    @Override
    default Optional<VoteUserQuestion> findByUserAndQuestion(User user, Question question){
        List<VoteUserQuestion> questions = findByQuestionIdAndUserId(question.getId(), user.getId());
        return questions.isEmpty() ? Optional.empty() : Optional.of(questions.get(0));
    }

    Long countByQuestionIdAndVoteType(Integer questionId, String voteType);
    @Override
    default  Long getUpVotes(Question question){
        return countByQuestionIdAndVoteType(question.getId(), "up");
    }

    @Override
    default Long getDownVotes(Question question){
        return countByQuestionIdAndVoteType(question.getId(), "down");
    }
}
