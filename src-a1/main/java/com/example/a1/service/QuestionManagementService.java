package com.example.a1.service;

import com.example.a1.entity.Question;
import com.example.a1.entity.Tag;
import com.example.a1.entity.User;
import com.example.a1.exception.NotAllowedException;
import com.example.a1.exception.QuestionNotFoundException;
import com.example.a1.exception.UserNotFoundException;
import com.example.a1.repository.RepositoryFactory;
import com.example.a1.repository.VoteUserQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionManagementService {

    private final RepositoryFactory repositoryFactory;

    @Transactional
    public List<Question> listQuestions() {
        VoteUserQuestionRepository voteUserQuestionRepository = repositoryFactory.createVoteUserQuestionRepository();
        List<Question> questions = repositoryFactory.createQuestionRepository().findAll().stream()
                .sorted(Comparator.comparing(Question::getCreationDate).reversed())
                .collect(Collectors.toList());
        questions.forEach(t->t.setVoteCount(voteUserQuestionRepository.getUpVotes(t) - voteUserQuestionRepository.getDownVotes(t)));
        return questions;
    }

    @Transactional
    public List<Question> searchQuestionsByTag(Set<Tag>tags){
        VoteUserQuestionRepository voteUserQuestionRepository = repositoryFactory.createVoteUserQuestionRepository();
        List<Question> questions = repositoryFactory.createQuestionRepository().findAll().stream()
                .filter(x->x.getQuestionTags().containsAll(tags))
                .sorted(Comparator.comparing(Question::getCreationDate).reversed())
                .collect(Collectors.toList());
        questions.forEach(t->t.setVoteCount(voteUserQuestionRepository.getUpVotes(t) - voteUserQuestionRepository.getDownVotes(t)));
        return questions;
    }

    @Transactional
    public List<Question> searchQuestionsByTitle(String title){
        VoteUserQuestionRepository voteUserQuestionRepository = repositoryFactory.createVoteUserQuestionRepository();
        List<Question> questions = repositoryFactory.createQuestionRepository().findByTitle(title).stream()
                .sorted(Comparator.comparing(Question::getCreationDate).reversed())
                .collect(Collectors.toList());
        questions.forEach(t->t.setVoteCount(voteUserQuestionRepository.getUpVotes(t) - voteUserQuestionRepository.getDownVotes(t)));
        return questions;
    }

    @Transactional
    public void addQuestion(int userId, String title, String text, Set<Tag> tags ){
        User user = repositoryFactory.createUserRepository().findById(userId).orElseThrow(UserNotFoundException::new);
        if(user.isBanned() || !user.isLogged()) throw new NotAllowedException();
        for(Tag tag: tags)
            repositoryFactory.createTagRepository().save(tag);
        repositoryFactory.createQuestionRepository().save(new Question(userId, title,text, tags));
    }

    @Transactional
    public void updateQuestion(int userId, int questionId, String newTitle, String newText, Set<Tag> tags){
        User user = repositoryFactory.createUserRepository().findById(userId).orElseThrow(UserNotFoundException::new);
        Question question = repositoryFactory.createQuestionRepository().findById(questionId).orElseThrow(QuestionNotFoundException::new);

        if(!user.isLogged() || user.isBanned()) throw new NotAllowedException();
        if(!user.isModerator() && userId!=question.getAuthorId()) throw new NotAllowedException();
        question.setText(newText);
        question.setTitle(newTitle);
        question.setQuestionTags(tags);
        question.setCreationDate(ZonedDateTime.now());

        for(Tag tag : tags)
            repositoryFactory.createTagRepository().save(tag);

        repositoryFactory.createQuestionRepository().save(question);
    }

    @Transactional
    public void deleteQuestion(int userId, int questionId){
        User user = repositoryFactory.createUserRepository().findById(userId).orElseThrow(UserNotFoundException::new);
        Question question = repositoryFactory.createQuestionRepository().findById(questionId).orElseThrow(QuestionNotFoundException::new);

        if(!user.isLogged() || user.isBanned()) throw new NotAllowedException();
        if(!user.isModerator() && userId!=question.getAuthorId()) throw new NotAllowedException();

        repositoryFactory.createQuestionRepository().delete(question);
    }



}
