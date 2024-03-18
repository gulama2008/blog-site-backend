package com.siyu.blogsitebackend.tag;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siyu.blogsitebackend.article.Article;
import com.siyu.blogsitebackend.article.ArticleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ArticleRepository articleRepository;

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

    public Optional<List<Article>> getAllArticlesByTagId(Long id) {
        if (!this.tagRepository.existsById(id)) {
            return Optional.ofNullable(null);
        }
        List<Article> articles = this.articleRepository.findAllByTags_id(id);
        return Optional.of(articles);
    }
    
    public boolean deleteById(Long id) {
        if (!this.tagRepository.existsById(id)) {
            return false;
        }
        List<Article> articles = this.articleRepository.findAllByTags_id(id);
        for (Article article : articles) {
            article.removeTag(id);
        }
        this.tagRepository.deleteById(id);
        return true;
    }

}
