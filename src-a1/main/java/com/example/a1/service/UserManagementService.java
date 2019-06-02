package com.example.a1.service;

import com.example.a1.entity.User;
import com.example.a1.exception.AlreadyBannedException;
import com.example.a1.exception.LoginFailedException;
import com.example.a1.exception.NotAllowedException;
import com.example.a1.exception.UserNotFoundException;
import com.example.a1.repository.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserManagementService {
    private final RepositoryFactory repositoryFactory;

    public User login (String username, String password) {
        User user = repositoryFactory.createUserRepository().findByUsername(username).orElseThrow(UserNotFoundException::new);
        if (!user.getPassword().equals(password)) throw new LoginFailedException();
        //computeScore(user);
        user.setLogged(true);
        repositoryFactory.createUserRepository().save(user);
        return user;
    }

    public void banUser(int moderatorId, int userId) {
        User moderator = repositoryFactory.createUserRepository().findById(moderatorId).orElseThrow(UserNotFoundException::new);
        User user = repositoryFactory.createUserRepository().findById(userId).orElseThrow(UserNotFoundException::new);

        if(!moderator.isModerator() || !moderator.isLogged() || moderator.isBanned()) throw new NotAllowedException();
        if(user.isBanned()) throw new AlreadyBannedException();

        user.setBanned(true);
        repositoryFactory.createUserRepository().save(user);
    }

    /*public void computeScore(User user) {
        user.setScore(0);
    }*/



}
