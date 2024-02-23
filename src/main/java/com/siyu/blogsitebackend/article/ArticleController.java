package com.siyu.blogsitebackend.article;

import java.util.List;
import java.util.Locale.Category;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siyu.blogsitebackend.exceptions.NotFoundException;
import com.siyu.blogsitebackend.tag.Tag;
import com.siyu.blogsitebackend.tag.TagAddDTO;
import com.siyu.blogsitebackend.tag.TagCreateDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping
    public ResponseEntity<List<Article>> getAll() {
        List<Article> allArticles = this.articleService.getAll();
        return new ResponseEntity<List<Article>>(allArticles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
	public ResponseEntity<Article> getById(@PathVariable Long id) {
		Optional<Article> found = this.articleService.getById(id);
		if(found.isPresent()) {
			return new ResponseEntity<Article>(found.get(), HttpStatus.OK);
		}
		throw new NotFoundException(String.format("Article with id: %d does not exist", id));
	}

    @PostMapping
    public ResponseEntity<Article> createArticle(@Valid @RequestBody ArticleCreateDTO data) {
        Article newArticle = this.articleService.createArticle(data);
        return new ResponseEntity<>(newArticle, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Article> deleteById(@PathVariable Long id) {
        boolean deleted = this.articleService.deleteById(id);
        if (deleted) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        throw new NotFoundException(String.format("Article with id %d does not exist, could not delete", id));
    }

    @PatchMapping("/{id}")
	public ResponseEntity<Article> updateById(@PathVariable Long id, 
            @Valid @RequestBody ArticleUpdateDTO data) {
        Optional<Article> updated = this.articleService.updateById(id, data);
        if (updated.isPresent()) {
            return new ResponseEntity<Article>(updated.get(), HttpStatus.OK);
        }
        throw new NotFoundException(String.format("Article with id %d does not exist, could not update", id));
    }
    
    @PostMapping("/{id}/tags")
    public ResponseEntity<Tag> addTag(@PathVariable Long id, @RequestBody TagCreateDTO tagData) {
        boolean added = articleService.addTag(id, tagData);
        if (added) {
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        }
        throw new NotFoundException(String.format("Cannot add tag", id));
    }
    
    //find tags by article id
    @GetMapping("/{id}/tags")
    public ResponseEntity<List<Tag>> getAllTagsByArticleId(@PathVariable Long id) {
        Optional<List<Tag>> tags = this.articleService.getAllTagsByArticleId(id);
        if (tags.isPresent()) {
            List<Tag> foundTags = tags.get();
            return new ResponseEntity<>(foundTags, HttpStatus.OK);
        }
        throw new NotFoundException(String.format("Cannot find any tags under article id %d", id));
    }

    //update tags for article with particular id
    @PatchMapping("/{id}/tags")
    public ResponseEntity<Article> updateTagsByArticleId(@PathVariable Long id, 
            @Valid @RequestBody ArticleUpdateTagDTO data) {
        Optional<Article> updated = this.articleService.updateTagsByArticleId(id, data);
        if (updated.isPresent()) {
            return new ResponseEntity<Article>(updated.get(), HttpStatus.OK);
        }
        throw new NotFoundException(String.format("Article with id %d does not exist, could not update", id));
    }

    @DeleteMapping("/{articleId}/tags/{tagId}")
    public ResponseEntity<Article> deleteTagFromArticle(@PathVariable(value = "articleId") Long articleId,
            @PathVariable(value = "tagId") Long tagId) {
        boolean deleted = articleService.deleteTagFromArticle(articleId, tagId);

        if (deleted) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        throw new NotFoundException(String.format("Article with id %d does not exist, could not delete", articleId));
    }
    
@GetMapping("/date")
    public ResponseEntity<List<Article>> getArticlesGroupByDate() {
        List<Article> allArticles = this.articleService.getArticlesGroupByDate();
        return new ResponseEntity<List<Article>>(allArticles, HttpStatus.OK);
    }
}
