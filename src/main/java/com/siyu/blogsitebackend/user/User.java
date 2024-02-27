package com.siyu.blogsitebackend.user;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.siyu.blogsitebackend.comment.Comment;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
    
     private String username;
     private String password;

     @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
     @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

     public User() {
     }

     public User(String username, String password) {
         this.username = username;
         this.password = password;
     }
     
     public User(Long id, String username, String password) {
        this.id=id;
         this.username = username;
         this.password = password;
     }
}
