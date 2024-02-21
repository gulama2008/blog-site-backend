package com.siyu.blogsitebackend.article;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.siyu.blogsitebackend.tag.Tag;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleUpdateTagDTO {
    private ArrayList<Tag> tags;
}
