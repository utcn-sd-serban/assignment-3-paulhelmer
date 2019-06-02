package com.example.a1;

import com.example.a1.entity.Answer;
import com.example.a1.entity.User;
import com.example.a1.exception.AnswerNotFoundException;
import com.example.a1.exception.UserNotFoundException;
import com.example.a1.repository.RepositoryFactory;
import com.example.a1.repository.memory.InMemoryRepositoryFactory;
import com.example.a1.service.AnswerManagementService;
import org.junit.Assert;
import org.junit.Test;

import java.time.ZonedDateTime;

public class AnswerManagementServiceTest {

    private static RepositoryFactory createMokedFactory(){
        RepositoryFactory factory = new InMemoryRepositoryFactory();
        factory.createUserRepository().save(new User(1, "paul", "helmer", false, true, false ));
        factory.createAnswerRepository().save(new Answer(1, 1, 1, "Answer1", ZonedDateTime.now(), Long.valueOf(0)));
        factory.createAnswerRepository().save(new Answer(2,1, 1, "Answer2", ZonedDateTime.now(), Long.valueOf(0)));
        return factory;
    }

    @Test
    public void testDeleteWorkWithExistingID(){
        RepositoryFactory factory = createMokedFactory();
        AnswerManagementService answerManagementService = new AnswerManagementService(factory);

        answerManagementService.deleteAnswer(1, 1);

        Assert.assertEquals(1, factory.createAnswerRepository().findAll().size());
        Assert.assertTrue(factory.createAnswerRepository().findById(2).isPresent());
    }

    @Test(expected = AnswerNotFoundException.class)
    public void testDeleteThrowsExceptionNonExistingAnswerId(){
        RepositoryFactory factory = createMokedFactory();
        AnswerManagementService answerManagementService = new AnswerManagementService(factory);

        answerManagementService.deleteAnswer(5, 1);
    }

    @Test(expected = UserNotFoundException.class)
    public void testDeleteThrowsExceptionNonExistingUserId(){
        RepositoryFactory factory = createMokedFactory();
        AnswerManagementService answerManagementService = new AnswerManagementService(factory);

        answerManagementService.deleteAnswer(5, 2);
    }



}
