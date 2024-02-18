package com.siyu.blogsitebackend.comment;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siyu.blogsitebackend.article.Article;
import com.siyu.blogsitebackend.article.ArticleCreateDTO;
import com.siyu.blogsitebackend.article.ArticleUpdateDTO;
import com.siyu.blogsitebackend.exceptions.NotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<List<Comment>> getAll() {
        List<Comment> allComments = this.commentService.getAll();
        return new ResponseEntity<>(allComments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
	public ResponseEntity<Comment> getById(@PathVariable Long id) {
		Optional<Comment> found = this.commentService.getById(id);
		if(found.isPresent()) {
			return new ResponseEntity<Comment>(found.get(), HttpStatus.OK);
		}
		throw new NotFoundException(String.format("Comment with id: %d does not exist", id));
	}

    @PostMapping
    public ResponseEntity<Comment> createComment(@Valid @RequestBody CommentCreateDTO data) {
        Comment newComment = this.commentService.createComment(data);
        if(newComment!=null){
            return new ResponseEntity<>(newComment, HttpStatus.CREATED);
        }
        throw new NotFoundException(String.format("Article does not exist"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Comment> deleteById(@PathVariable Long id) {
        boolean deleted = this.commentService.deleteById(id);
        if (deleted == true) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        throw new NotFoundException(String.format("Comment with id %d does not exist, could not delete", id));
    }

    @PutMapping("/{id}")
	public ResponseEntity<Comment> updateById(@PathVariable Long id, 
            @Valid @RequestBody CommentUpdateDTO data) {
        Optional<Comment> updated = this.commentService.updateById(id, data);
        if (updated.isPresent()) {
            return new ResponseEntity<Comment>(updated.get(), HttpStatus.OK);
        }
        throw new NotFoundException(String.format("Comment with id %d does not exist, could not update", id));
    }
    
    //find article by comment id
    @GetMapping("/{id}/article")
	public ResponseEntity<Article> getArticleByCommentId(@PathVariable Long id) {
		Optional<Article> found = this.commentService.getArticleByCommentId(id);
        if (found.isPresent()) {
            return new ResponseEntity<Article>(found.get(), HttpStatus.OK);
        }
        System.out.println("testtest=========");
		throw new NotFoundException(String.format("Cound not find article with comment id %d", id));
	}
    
}
