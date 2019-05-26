package com.example.a1.repository.memory;

import com.example.a1.entity.Tag;
import com.example.a1.entity.Question;
import com.example.a1.repository.TagRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryTagRepository implements TagRepository {
    private final Map<Integer, Tag> tagRepository = new ConcurrentHashMap<Integer, Tag>();
    private AtomicInteger currentId = new AtomicInteger(1);

    @Override
    public Tag save(Tag tag) {
        if (tag.getId() == 0) {
            tag.setId(currentId.getAndIncrement());
        }
        tagRepository.put(tag.getId(), tag);
        return tag;
    }

    @Override
    public Optional<Tag> findById(Integer id) {
        return Optional.ofNullable(tagRepository.get(id));
    }

    @Override
    public void delete(Tag tag) {
        tagRepository.remove(tag.getId());
    }

    @Override
    public List<Tag> findAll() {
        return new ArrayList<Tag>(tagRepository.values());
    }

    public Optional<Tag> findByName(String name) {
        return Optional.ofNullable(this.findAll().stream().filter(x->x.getTagName().equals(name)).collect(Collectors.toList()).get(0));
    }

    public void addTagToQuestion(Tag tag, Question question){
        question.getQuestionTags().add(tag);
    }

}
