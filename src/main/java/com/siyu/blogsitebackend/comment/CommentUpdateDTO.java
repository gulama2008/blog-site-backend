package com.siyu.blogsitebackend.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentUpdateDTO {

    
    private String content;

    private Boolean blocked;

}
