package com.siyu.blogsitebackend.comment;

import java.time.LocalDate;
import java.util.Locale.Category;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.siyu.blogsitebackend.article.Article;
import com.siyu.blogsitebackend.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition="TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate commentDate;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    private boolean blocked = false;

    public Comment() {
    }
    
    public Comment(User user, String content,LocalDate commentDate,Article article) {
        this.user = user;
        this.content = content;
        this.commentDate = commentDate;
        this.article = article;
    }

}
