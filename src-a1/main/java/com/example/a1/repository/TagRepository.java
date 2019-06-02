package com.example.a1.repository;

import com.example.a1.entity.Question;
import com.example.a1.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends RepositoryInterface<Tag> {
    Optional<Tag> findByName(String name);
}
