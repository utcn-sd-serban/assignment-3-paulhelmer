package com.example.a1.service;

import com.example.a1.dto.UserDTO;
import com.example.a1.dto.UserLoggedInDTO;
import com.example.a1.entity.User;
import com.example.a1.events.UserBannedEvent;
import com.example.a1.exception.AlreadyBannedException;
import com.example.a1.exception.LoginFailedException;
import com.example.a1.exception.NotAllowedException;
import com.example.a1.exception.UserNotFoundException;
import com.example.a1.repository.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserManagementService implements UserDetailsService {
    private final RepositoryFactory repositoryFactory;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public User login (String username, String password, PasswordEncoder passwordEncoder){

    User user = repositoryFactory.createUserRepository().findByUsername(username).orElseThrow(UserNotFoundException::new);
        if (!passwordEncoder.matches(password, user.getPassword())) throw new LoginFailedException();
        //computeScore(user);
        user.setLogged(true);
        repositoryFactory.createUserRepository().save(user);
        return user;
    }

    @Transactional
    public List<UserLoggedInDTO> listAllUsers(int requesterUserId) {
        User requesterUser = repositoryFactory.createUserRepository().findById(requesterUserId).orElseThrow(UserNotFoundException::new);
        return repositoryFactory.createUserRepository().findAll().stream()
                .map(UserLoggedInDTO::ofEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDTO getUserDTO(int userId) {
        User user = repositoryFactory.createUserRepository().findById(userId).orElseThrow(UserNotFoundException::new);

        return UserDTO.ofEntity(user);
    }

    @Transactional
    public UserLoggedInDTO getLoggedInUserDTO(int userId) {
        User user = repositoryFactory.createUserRepository().findById(userId).orElseThrow(UserNotFoundException::new);

        return UserLoggedInDTO.ofEntity(user);
    }

    @Transactional
    public void banUser(int moderatorId, int userId) {
        User moderator = repositoryFactory.createUserRepository().findById(moderatorId).orElseThrow(UserNotFoundException::new);
        User user = repositoryFactory.createUserRepository().findById(userId).orElseThrow(UserNotFoundException::new);

        if(!moderator.isModerator() || !moderator.isLogged() || moderator.isBanned()) throw new NotAllowedException();
        if(user.isBanned()) throw new AlreadyBannedException();

        user.setBanned(true);
        repositoryFactory.createUserRepository().save(user);
        eventPublisher.publishEvent(new UserBannedEvent(userId));
    }

    /*public void computeScore(User user) {
        user.setScore(0);
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repositoryFactory.createUserRepository().findAll().stream().filter(u -> u.getUsername().equals(username)).findAny()
                .orElseThrow(() -> new UsernameNotFoundException("Unknown user"));

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        if (!user.isBanned()) grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (user.isModerator()) grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_MODERATOR"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

    @Transactional
    public User loadCurrentUser() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return repositoryFactory.createUserRepository().findByUsername(name).orElseThrow(UserNotFoundException::new);
    }



}
