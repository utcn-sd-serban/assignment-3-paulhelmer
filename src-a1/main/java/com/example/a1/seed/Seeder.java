package com.example.a1.seed;

import com.example.a1.entity.Question;
import com.example.a1.entity.Tag;
import com.example.a1.entity.User;
import com.example.a1.repository.AnswerRepository;
import com.example.a1.repository.QuestionRepository;
import com.example.a1.repository.RepositoryFactory;
import com.example.a1.repository.UserRepository;
import com.example.a1.service.AnswerManagementService;
import com.example.a1.service.QuestionManagementService;
import com.example.a1.service.UserManagementService;
import com.example.a1.service.VoteManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)

public class Seeder implements CommandLineRunner {
    private final RepositoryFactory factory;
    private  final VoteManagementService voteManagementService;
    private final UserManagementService userManagementService;
    private final QuestionManagementService questionManagementService;
    private final AnswerManagementService answerManagementService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        UserRepository userRepository = factory.createUserRepository();
        if(userRepository.findAll().size() <= 4) {
            userRepository.save(new User( "u5",passwordEncoder.encode("pass")));
            userRepository.save(new User( "u6",passwordEncoder.encode("pass")));
            userRepository.save(new User( "u7",passwordEncoder.encode("pass")));
            userRepository.save(new User( "u8",passwordEncoder.encode("pass")));
        }
        QuestionRepository questionRepository = factory.createQuestionRepository();
        User user = userRepository.findAll().get(0);
        user.setLogged(true);
        if(questionRepository.findAll().isEmpty()) {
            questionManagementService.addQuestion(user.getId(),"question 1","ana are 1 mar",new HashSet<>(Arrays.asList(new Tag("tag1"),new Tag("tag2"),new Tag("tag3"))));
            questionManagementService.addQuestion(user.getId(),"question 2","ana are 2 mere",new HashSet<>(Arrays.asList(new Tag("tag2"))));
            questionManagementService.addQuestion(user.getId(),"question 3","ana are 3 mere",new HashSet<>(Arrays.asList(new Tag("tag3"))));
        }
        AnswerRepository answerRepository = factory.createAnswerRepository();
        Question question = factory.createQuestionRepository().findAll().get(0);

        User user2 = userRepository.findAll().get(1);
        user2.setLogged(true);
        if(answerRepository.findAll().isEmpty()) {
            answerManagementService.addAnswer(user.getId(), question.getId(), "answer 1");
            answerManagementService.addAnswer(user2.getId(), question.getId(), "answer 2");
            answerManagementService.addAnswer(user.getId(), question.getId(), "answer 3");
        }
    }
}
