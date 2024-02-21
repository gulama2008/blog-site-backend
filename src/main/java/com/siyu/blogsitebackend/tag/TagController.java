package com.siyu.blogsitebackend.tag;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siyu.blogsitebackend.article.Article;
import com.siyu.blogsitebackend.exceptions.NotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public ResponseEntity<List<Tag>> getAll() {
        List<Tag> allTags = this.tagService.getAll();
        return new ResponseEntity<>(allTags, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getById(@PathVariable Long id) {
        Optional<Tag> found = this.tagService.getById(id);

        if (found.isPresent()) {
            return new ResponseEntity<Tag>(found.get(), HttpStatus.OK);
        }
        throw new NotFoundException(String.format("Tag with id: %d does not exist", id));
    }
    
    @PostMapping
    public ResponseEntity<Tag> createTag(@Valid @RequestBody TagCreateDTO data) {
        Tag newTag = this.tagService.createTag(data);
        return new ResponseEntity<>(newTag, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/articles")
    public ResponseEntity<List<Article>> getAllArticlesByTagId(@PathVariable Long id) {
        Optional<List<Article>> articles = this.tagService.getAllArticlesByTagId(id);
        if (articles.isPresent()) {
            List<Article> foundArticles = articles.get();
            return new ResponseEntity<>(foundArticles, HttpStatus.OK);
        }
        throw new NotFoundException(String.format("Cannot find any articles"));
    }

    //
    // @GetMapping("/articles")
    // public ResponseEntity<List<Article>> getAllArticlesByTagId(@PathVariable Long id) {
    //     Optional<List<Article>> articles = this.tagService.getAllArticlesByTagId(id);
    //     if (articles.isPresent()) {
    //         List<Article> foundArticles = articles.get();
    //         return new ResponseEntity<>(foundArticles, HttpStatus.OK);
    //     }
    //     throw new NotFoundException(String.format("Cannot find any articles"));
    // }

    @DeleteMapping("/{id}")
    public ResponseEntity<Tag> deleteById(@PathVariable Long id) {
        boolean deleted = this.tagService.deleteById(id);
        if (deleted) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        throw new NotFoundException(String.format("Tag with id %d does not exist, could not delete", id));
    }
}
