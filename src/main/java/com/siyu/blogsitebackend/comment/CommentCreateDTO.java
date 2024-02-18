package com.siyu.blogsitebackend.comment;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.siyu.blogsitebackend.article.Article;
import com.siyu.blogsitebackend.user.User;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CommentCreateDTO {

    @NotBlank
    private String content;

    @NotNull
    @Positive
    private Long articleId;

    @NotBlank
    private String commentDate;

    @NotNull
    @Positive
    private Long userId;
}
