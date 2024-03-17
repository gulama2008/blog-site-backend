package com.siyu.blogsitebackend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.siyu.blogsitebackend.article.Article;
import com.siyu.blogsitebackend.article.ArticleController;
import com.siyu.blogsitebackend.article.ArticleService;
import com.siyu.blogsitebackend.exceptions.NotFoundException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArticleController.class)
public class ArticleControllerTest {

    @Autowired
	private MockMvc mockMvc;

	@MockBean
    private ArticleService articleService;

    @Test
    void getAll_ReturnAllArticles() throws Exception {
        Article article1 = new Article("article title1", "article content1", LocalDate.parse("2024-01-01"));
        Article article2 = new Article("article title2", "article content2", LocalDate.parse("2024-02-01"));
        List<Article> articles = List.of(article1, article2);
        when(articleService.getAll()).thenReturn(articles);
        this.mockMvc.perform(get("/articles")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    @Test
    void getById_ExistingId_ReturnOptionalOfArticle() throws Exception {
        Long id = 123L;
        Article article = new Article("article title1", "article content1", LocalDate.parse("2024-01-01"));
        when(articleService.getById(id)).thenReturn(Optional.of(article));
        this.mockMvc.perform(get("/articles/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    @Test
    void getById_NonExistingId_ReturnOptionalOfArticle() throws Exception {
        Long id = 123L;
		when(articleService.getById(id)).thenReturn(Optional.empty());
        this.mockMvc.perform(
            get("/articles/{id}", id).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())       
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
            .andExpect(result -> assertEquals("Article with id: 123 does not exist",result.getResolvedException().getMessage()));
	}
    
}
