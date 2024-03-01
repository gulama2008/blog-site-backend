package com.siyu.blogsitebackend.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.siyu.blogsitebackend.comment.Comment;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails{
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
    
     @Column(unique = true)
     private String username;

     private String password;

     @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
     @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role=Role.USER;

     public User() {
     }

     public User(String username, String password) {
         this.username = username;
         this.password = password;
     }
     
     public User(Long id, String username, String password) {
         this.id = id;
         this.username = username;
         this.password = password;
     }

     public User(String username, String password,Role role) {
         this.username = username;
         this.password = password;
         this.role = Role.ADMIN;
     }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
       return true;
    }

    @Override
    public boolean isAccountNonLocked() {
       return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
       return true;
    }

    @Override
    public boolean isEnabled() {
       return true;
    }
}
