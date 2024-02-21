package com.siyu.blogsitebackend.tag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.siyu.blogsitebackend.article.Article;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "tags", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.REFRESH })
    private Set<Article> articles = new HashSet<>();;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tag id=" + id + ", name=" + name;
    }
    
    public void addArticle(Article article) {
        this.articles.add(article);
        article.getTags().add(this);
    }
  
    public void removeArticle(Long articleId) {
      Article article = this.articles.stream().filter(t -> t.getId() == articleId).findFirst().orElse(null);
      if (article != null) {
        this.articles.remove(article);
        article.getTags().remove(this);
      }
    }
}
