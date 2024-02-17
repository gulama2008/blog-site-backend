package com.siyu.blogsitebackend.article;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.siyu.blogsitebackend.comment.Comment;
import com.siyu.blogsitebackend.tag.Tag;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition="TEXT")
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate publishDate;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinTable(name = "ARTICLE_TAG_MAPPING", joinColumns = @JoinColumn(name = "article_id"), 
        	inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags=new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    private int views=0;

    public Article() {
    }

    public Article(String title, String content, LocalDate publishDate) {
        this.title = title;
        this.content = content;
        this.publishDate = publishDate;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
        tag.getArticles().add(this);
    }
  
  public void removeTag(long tagId) {
    Tag tag = this.tags.stream().filter(t -> t.getId() == tagId).findFirst().orElse(null);
    if (tag != null) {
      this.tags.remove(tag);
      tag.getArticles().remove(this);
    }
  }
}
