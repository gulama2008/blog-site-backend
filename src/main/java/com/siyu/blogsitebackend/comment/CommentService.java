package com.siyu.blogsitebackend.comment;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siyu.blogsitebackend.article.Article;
import com.siyu.blogsitebackend.article.ArticleService;
import com.siyu.blogsitebackend.user.User;
import com.siyu.blogsitebackend.user.UserService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    public List<Comment> getAll() {
        return this.commentRepository.findAll();
    }

    public Optional<Comment> getById(Long id) {
		Optional<Comment> foundComment = commentRepository.findById(id);
		return foundComment;
	}

    public Comment createComment(CommentCreateDTO data) {
        String content = data.getContent();
        LocalDate commentDate = data.getCommentDate();
        Optional<Article> article = this.articleService.getById(data.getArticleId());
        Optional<User> user = this.userService.getById(data.getUserId());
        if (article.isPresent()) {
            Comment newComment = new Comment(user.get(), content, commentDate, article.get());
            Comment created = this.commentRepository.save(newComment);
            return created;
        }
        return null;
    }

    public boolean deleteById(Long id) {
        Optional<Comment> foundComment = this.commentRepository.findById(id);
        if (foundComment.isPresent()) {
            this.commentRepository.delete(foundComment.get());
            return true;
        }
        return false;
    }
    
    public Optional<Comment> updateById(Long id, CommentUpdateDTO data) {
        Optional<Comment> foundComment = this.getById(id);
        if(foundComment.isPresent()) {
            Comment toUpdate = foundComment.get();
            toUpdate.setContent(data.getContent());
            Comment updatedComment = this.commentRepository.save(toUpdate);
            return Optional.of(updatedComment);
        }
        return foundComment;
    }
}
