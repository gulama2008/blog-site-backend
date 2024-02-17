package com.siyu.blogsitebackend.article;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import com.siyu.blogsitebackend.tag.Tag;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ArticleCreateDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

     @NotBlank
     private LocalDate publishDate;
    
     private ArrayList<Tag> tags;
   
}
