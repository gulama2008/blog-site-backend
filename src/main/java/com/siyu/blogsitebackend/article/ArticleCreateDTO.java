package com.siyu.blogsitebackend.article;

import java.util.ArrayList;

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
     private String publishDate;
    
     private ArrayList<Tag> tags;

     public ArticleCreateDTO(String title,String content, String publishDate) {
         this.title = title;
         this.content = content;
         this.publishDate = publishDate;
     }
   
}
