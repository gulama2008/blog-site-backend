package com.siyu.blogsitebackend.tag;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.siyu.blogsitebackend.article.Article;

public interface TagRepository extends JpaRepository<Tag,Long>{
   
}
