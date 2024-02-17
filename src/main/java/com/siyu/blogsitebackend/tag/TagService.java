package com.siyu.blogsitebackend.tag;

import java.util.List;
import java.util.Locale.Category;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public List<Tag> getAll() {
        return this.tagRepository.findAll();
    }

    public Optional<Tag> getById(Long id) {
        Optional<Tag> foundTag = tagRepository.findById(id);
        return foundTag;
    }
    
    public Tag createTag(TagCreateDTO data) {
        String name = data.getName();
        Tag newTag = new Tag(name);
        Tag created = this.tagRepository.save(newTag);
        return created;
    }

}
