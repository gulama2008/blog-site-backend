package com.siyu.blogsitebackend.article;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleUpdateDTO {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
