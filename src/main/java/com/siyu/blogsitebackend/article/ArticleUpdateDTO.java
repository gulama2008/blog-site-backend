package com.siyu.blogsitebackend.article;

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

    public ArticleUpdateDTO() {
    };

    public ArticleUpdateDTO(String title,String content) {
        this.title = title;
        this.content = content;
    }
}
