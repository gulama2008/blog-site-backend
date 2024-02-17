package com.siyu.blogsitebackend.comment;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentUpdateDTO {
    @NotBlank
    private String content;
}
