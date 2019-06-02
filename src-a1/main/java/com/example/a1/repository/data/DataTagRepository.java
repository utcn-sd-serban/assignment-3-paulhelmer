package com.example.a1.repository.data;

import com.example.a1.entity.Tag;
import com.example.a1.repository.TagRepository;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface DataTagRepository extends Repository<Tag,Integer>, TagRepository {

    List<Tag> findByTagName(String tagName);

    @Override
    default Optional<Tag> findByName(String tagName){
        return Optional.ofNullable(findByTagName(tagName).get(0));
    }
}
