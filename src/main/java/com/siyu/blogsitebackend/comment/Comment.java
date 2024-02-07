package com.siyu.blogsitebackend.comment;

import java.time.LocalDate;
import java.util.Locale.Category;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.siyu.blogsitebackend.blog.Blog;

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

    @Column
    private String nickname;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate commentDate;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;

    public Comment() {
    }
    
    public Comment(String nickname, String content,LocalDate commentDate,Blog blog) {
        this.nickname = nickname;
        this.content = content;
        this.commentDate = commentDate;
        this.blog = blog;
    }

}
