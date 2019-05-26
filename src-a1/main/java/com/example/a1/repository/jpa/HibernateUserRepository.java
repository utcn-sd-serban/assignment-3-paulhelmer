package com.example.a1.repository.jpa;

import com.example.a1.entity.User;
import com.example.a1.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class HibernateUserRepository implements UserRepository {
    private final EntityManager entityManager;

    @Override
    public User save(User user) {
        if(user.getId() == null) {
            entityManager.persist(user);
            return user;
        }
        else
            return entityManager.merge(user);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public void delete(User user) {
        entityManager.remove(user);

    }

    @Override
    public List<User> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        query.select(query.from(User.class));
        return entityManager.createQuery(query).getResultList();

    }

    @Override
    public Optional<User> findByUsername(String username) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        query.select(query.from(User.class));
        query.where(builder.equal(query.from(User.class).get("username"), username));
        List<User> users = entityManager.createQuery(query).getResultList();
        return Optional.ofNullable(users.get(0));
    }
}
