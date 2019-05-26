package com.example.a1.service;

import com.example.a1.entity.*;
import com.example.a1.events.VotedAnswerEvent;
import com.example.a1.events.VotedQuestionEvent;
import com.example.a1.exception.AnswerNotFoundException;
import com.example.a1.exception.NotAllowedException;
import com.example.a1.exception.QuestionNotFoundException;
import com.example.a1.exception.UserNotFoundException;
import com.example.a1.repository.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoteManagementService {
    private final RepositoryFactory repositoryFactory;
    private final ApplicationEventPublisher eventPublisher;
    private final ServiceHelper serviceHelper;

    @Transactional
    public void voteQuestion(int userId, int questionId, String voteType){
        User user = repositoryFactory.createUserRepository().findById(userId).orElseThrow(UserNotFoundException::new);
        Question question = repositoryFactory.createQuestionRepository().findById(questionId).orElseThrow(QuestionNotFoundException::new);

        if(user.isBanned() || !user.isLogged() || userId == question.getAuthorId()) throw new NotAllowedException();
        VoteUserQuestion voteUserQuestion = repositoryFactory.createVoteUserQuestionRepository().findByUserAndQuestion(user, question).orElse(null);

        if(voteUserQuestion == null) {
             repositoryFactory.createVoteUserQuestionRepository().save(new VoteUserQuestion(userId, questionId, voteType));

        }
        else {
            voteUserQuestion.setVoteType(voteType);
            repositoryFactory.createVoteUserQuestionRepository().save(voteUserQuestion);
        }
        eventPublisher.publishEvent(new VotedQuestionEvent(serviceHelper.getQuestionDTO(
                repositoryFactory.createQuestionRepository().findById(questionId).orElseThrow(QuestionNotFoundException::new))));
    }

    public void voteAnswer(int userId, int answerId, String voteType){
        User user = repositoryFactory.createUserRepository().findById(userId).orElseThrow(UserNotFoundException::new);
        Answer answer = repositoryFactory.createAnswerRepository().findById(answerId).orElseThrow(AnswerNotFoundException::new);

        if(user.isBanned() || !user.isLogged() || userId == answer.getAuthorId()) throw  new NotAllowedException();

        VoteUserAnswer voteUserAnswer = repositoryFactory.createVoteUserAnswerRepository().findByUserAndAnswer(user, answer).orElse(null);
        if(voteUserAnswer == null)
            repositoryFactory.createVoteUserAnswerRepository().save(new VoteUserAnswer(userId, answerId, voteType));
        else {
            voteUserAnswer.setVoteType(voteType);
            repositoryFactory.createVoteUserAnswerRepository().save(voteUserAnswer);
        }
        eventPublisher.publishEvent(new VotedAnswerEvent(serviceHelper.getAnswerDTO(
                repositoryFactory.createAnswerRepository().findById(answerId).orElseThrow(QuestionNotFoundException::new))));

    }


}
