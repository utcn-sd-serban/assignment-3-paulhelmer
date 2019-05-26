package com.example.a1.repository.jpa;

import com.example.a1.entity.Tag;
import com.example.a1.repository.TagRepository;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class HibernateTagRepository implements TagRepository {
    private final EntityManager entityManager;

    @Override
    public Tag save(Tag tag) {
        if (tag.getId() == null) {
            entityManager.persist(tag);
            return tag;
        } else
            return entityManager.merge(tag);
    }

    @Override
    public Optional<Tag> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public void delete(Tag tag) {
        entityManager.remove(tag);

    }

    @Override
    public List<Tag> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> query = builder.createQuery(Tag.class);
        query.select(query.from(Tag.class));
        return entityManager.createQuery(query).getResultList();

    }

    @Override
    public Optional<Tag> findByName(String name) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> query = builder.createQuery(Tag.class);
        query.select(query.from(Tag.class));
        query.where(builder.equal(query.from(Tag.class).get("tag_name"),name));
        List<Tag> tags = entityManager.createQuery(query).getResultList();
        return Optional.ofNullable(tags.get(0));
    }
}
