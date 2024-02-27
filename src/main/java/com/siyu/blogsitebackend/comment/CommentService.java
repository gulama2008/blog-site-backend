package com.siyu.blogsitebackend.comment;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siyu.blogsitebackend.article.Article;
import com.siyu.blogsitebackend.article.ArticleRepository;
import com.siyu.blogsitebackend.article.ArticleService;
import com.siyu.blogsitebackend.user.User;
import com.siyu.blogsitebackend.user.UserRepository;
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
    private ArticleRepository articleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public List<Comment> getAll() {
        List<Comment> comments = this.commentRepository.findAll();
        return comments.stream().map(
                comment -> {
                    Comment copy=new Comment(comment.getId(), comment.getUser(), comment.getContent(), comment.getCommentDate(),
                            comment.getArticle());
                    copy.setUser(new User(comment.getUser().getId(),comment.getUser().getUsername(), null));
                    return copy;
            }
        ).collect(Collectors.toList());

    }

    public Optional<Comment> getById(Long id) {
		Optional<Comment> foundComment = commentRepository.findById(id);
		return foundComment;
	}

    public Comment createComment(CommentCreateDTO data) {
        String content = data.getContent();
        LocalDate commentDate = LocalDate.parse(data.getCommentDate());
        Optional<Article> article = this.articleService.getById(data.getArticleId());
        Optional<User> user = this.userService.getById(data.getUserId());
        if (article.isPresent()) {
            Comment newComment = new Comment(user.get(), content, commentDate, article.get());
            Comment created = this.commentRepository.save(newComment);
            article.get().getComments().add(newComment);
            return created;
        }
        return null;
    }

    public boolean deleteById(Long id) {
        Optional<Comment> foundComment = this.commentRepository.findById(id);
        if (foundComment.isPresent()) {
            Optional<Article> foundArticle = this.getArticleByCommentId(id);
            if (foundArticle.isPresent()) {
                foundArticle.get().getComments().remove(foundComment.get());
                this.commentRepository.delete(foundComment.get());
                return true;
            } 
        }
        return false;
    }
    
    public Optional<Comment> updateById(Long id, CommentUpdateDTO data) {
        Optional<Comment> foundComment = this.getById(id);
        if (foundComment.isPresent()) {
            Comment toUpdate = foundComment.get();
            if (data.getBlocked() != null) {
                toUpdate.setBlocked(data.getBlocked());
            }
            if (data.getContent()!=null) {
                toUpdate.setContent(data.getContent());
            }
            Comment updatedComment = this.commentRepository.save(toUpdate);
            return Optional.of(updatedComment);
        }
        return Optional.ofNullable(null);
    }

    public Optional<Article> getArticleByCommentId(Long id) {
        if(this.commentRepository.existsById(id)){
            Article article = this.articleRepository.findByComments_id(id);
            if (article != null) {
                return Optional.of(article);
            }
        }
        return Optional.ofNullable(null);
    }

    public Optional<User> getUserByCommentId(Long id) {
        if(this.commentRepository.existsById(id)){
            User user = this.userRepository.findByComments_id(id);
            if (user != null) {
                return Optional.of(user);
            }
        }
        return Optional.ofNullable(null);
    }
}
