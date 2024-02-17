package com.siyu.blogsitebackend.comment;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.siyu.blogsitebackend.article.Article;
import com.siyu.blogsitebackend.user.User;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CommentCreateDTO {

    @NotBlank
    private String content;
    @NotBlank
    private Long articleId;
    @NotBlank
    private LocalDate commentDate;
    @NotBlank
    private Long userId;
}
