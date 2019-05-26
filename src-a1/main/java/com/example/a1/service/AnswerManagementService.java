package com.example.a1.service;

import com.example.a1.dto.AnswerDTO;
import com.example.a1.entity.Answer;
import com.example.a1.entity.Question;
import com.example.a1.entity.User;
import com.example.a1.events.AnswerCreatedEvent;
import com.example.a1.events.AnswerDeletedEvent;
import com.example.a1.events.AnswerUpdatedEvent;
import com.example.a1.exception.AnswerNotFoundException;
import com.example.a1.exception.NotAllowedException;
import com.example.a1.exception.QuestionNotFoundException;
import com.example.a1.exception.UserNotFoundException;
import com.example.a1.repository.RepositoryFactory;
import com.example.a1.repository.VoteUserAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerManagementService {

    private final RepositoryFactory repositoryFactory;
    private final ApplicationEventPublisher eventPublisher;
    private final ServiceHelper serviceHelper;

    @Transactional
    public List<AnswerDTO> listAnswersForQuestion(int questionId) {
        VoteUserAnswerRepository voteUserAnswerRepository = repositoryFactory.createVoteUserAnswerRepository();
        Question question = repositoryFactory.createQuestionRepository().findById(questionId).orElseThrow(QuestionNotFoundException::new);
        List<Answer> answers =  repositoryFactory.createAnswerRepository().findAnswersByQuestion(question);
        answers.forEach(t->t.setVoteCount(voteUserAnswerRepository.getUpVotes(t)-voteUserAnswerRepository.getDownVotes(t)));
        return answers.stream().sorted(Comparator.comparing(Answer::getVoteCount).reversed())
                .map(serviceHelper::getAnswerDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AnswerDTO addAnswer(int userId, int questionId, String text) {
        User user = repositoryFactory.createUserRepository().findById(userId).orElseThrow(UserNotFoundException::new);
        if (!user.isBanned() && user.isLogged()) {
            Question question = repositoryFactory.createQuestionRepository().findById(questionId).orElseThrow(QuestionNotFoundException::new);

            AnswerDTO answerDTO = serviceHelper.getAnswerDTO(repositoryFactory.createAnswerRepository().save(new Answer(userId, questionId, text)));
            eventPublisher.publishEvent(new AnswerCreatedEvent(answerDTO));
            return answerDTO;
        }
        return null;
    }

    @Transactional
    public AnswerDTO editAnswer(int answerId, int userId, String newText) {
        User user = repositoryFactory.createUserRepository().findById(userId).orElseThrow(UserNotFoundException::new);
        Answer answer = repositoryFactory.createAnswerRepository().findById(answerId).orElseThrow(AnswerNotFoundException::new);
        if (answer.getAuthorId().equals(userId) || user.isModerator()) {
            answer.setText(newText);

            AnswerDTO answerDTO = serviceHelper.getAnswerDTO(repositoryFactory.createAnswerRepository().save(answer));
            eventPublisher.publishEvent(new AnswerUpdatedEvent(answerDTO));
            return answerDTO;
        } else
            throw new NotAllowedException();
    }

    @Transactional
    public void deleteAnswer(int answerId, int userId) {
        User user = repositoryFactory.createUserRepository().findById(userId).orElseThrow(UserNotFoundException::new);
        Answer answer = repositoryFactory.createAnswerRepository().findById(answerId).orElseThrow(AnswerNotFoundException::new);
        if (answer.getAuthorId() == user.getId() || user.isModerator()) {
            repositoryFactory.createAnswerRepository().delete(answer);
            eventPublisher.publishEvent(new AnswerDeletedEvent(answerId));
        }
        else
            throw new NotAllowedException();
    }
}