package com.siyu.blogsitebackend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import com.siyu.blogsitebackend.article.Article;
import com.siyu.blogsitebackend.article.ArticleCreateDTO;
import com.siyu.blogsitebackend.article.ArticleRepository;
import com.siyu.blogsitebackend.article.ArticleService;
import com.siyu.blogsitebackend.article.ArticleUpdateDTO;
import com.siyu.blogsitebackend.tag.Tag;
import com.siyu.blogsitebackend.tag.TagCreateDTO;
import com.siyu.blogsitebackend.tag.TagRepository;
import com.siyu.blogsitebackend.tag.TagService;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagService tagService;

    @InjectMocks
    private ArticleService underTest;

    @Test
    void getAll_ReturnsAllData() {
        underTest.getAll();
        Mockito.verify(articleRepository).findAll(Sort.by(Sort.Direction.DESC, "publishDate"));
    }

    @Test
    void getById_ReturnsOptionalOfArticle() {
        Long id = 123L;
        this.underTest.getById(id);
        Mockito.verify(articleRepository,times(1)).findById(id);
    }
    
    @Test
    void createArticle_ValidArticle_AddsArticleToDB() {
        ArticleCreateDTO dto = new ArticleCreateDTO("article title", "article content", "2024-01-01");
        Article newArticle = new Article("article title", "article content", LocalDate.parse("2024-01-01"));
        this.underTest.createArticle(dto);
        ArgumentCaptor<Article> articleArgument = ArgumentCaptor.forClass(Article.class);
        Mockito.verify(articleRepository).save(articleArgument.capture());
        assertNotEquals(null, articleArgument.getValue().getTitle());
        assertEquals(newArticle.getContent(), articleArgument.getValue().getContent());
    }
    
    @Test
    void deleteById_ExistingId_ReturnsTrue() {
        Long id = 123L;
        Article article = new Article("article title", "article content", LocalDate.parse("2024-01-01"));
        BDDMockito.given(articleRepository.findById(id)).willReturn(Optional.of(article));
        Boolean deleted = this.underTest.deleteById(id);
        ArgumentCaptor<Article> articleArg = ArgumentCaptor.forClass(Article.class);
        Mockito.verify(articleRepository).delete(articleArg.capture());
        assertTrue(deleted);
        assertEquals(article, articleArg.getValue());
    }
    
    @Test
    void deleteById_NonExistingId_ReturnsFalse() {
        Long id = 123l;
        BDDMockito.given(articleRepository.findById(id)).willReturn(Optional.empty());
        Boolean deleted = this.underTest.deleteById(id);
        ArgumentCaptor<Article> articleArg = ArgumentCaptor.forClass(Article.class);
        Mockito.verify(articleRepository, never()).delete(articleArg.capture());
        assertFalse(deleted);
    }
    
    @Test
    void updateById_ExistingId_ReturnArticle() {
        Long id = 123L;
        ArticleUpdateDTO dto = new ArticleUpdateDTO("new article title", "new article content");
        Article article = new Article("article title", "article content", LocalDate.parse("2024-01-01"));
        Article updatedArticle = new Article("new article title", "new article content", LocalDate.parse("2024-01-01"));
        BDDMockito.given(articleRepository.findById(id)).willReturn(Optional.of(article));
        BDDMockito.given(articleRepository.save(any())).willReturn(updatedArticle);
        this.underTest.updateById(id, dto);
        ArgumentCaptor<Article> articleArg = ArgumentCaptor.forClass(Article.class);
        Mockito.verify(articleRepository, times(1)).save(articleArg.capture());
        assertEquals(article.getTitle(), dto.getTitle());
        assertEquals(article.getContent(), dto.getContent());
    }
    
    @Test
    void addTag_ExistingArticleIdAndTag_ReturnTrue() {
        Long id = 123L;
        Article article = new Article("article title", "article content", LocalDate.parse("2024-01-01"));
        BDDMockito.given(articleRepository.findById(id)).willReturn(Optional.of(article));
        TagCreateDTO dto = new TagCreateDTO(456L, "tag");
        // Article updatedArticle = new Article("new article title", "new article content", LocalDate.parse("2024-01-01"));
        Tag tag = new Tag("tag");
        BDDMockito.given(tagService.getById(dto.getId())).willReturn(Optional.of(tag));
        Boolean result = this.underTest.addTag(id, dto);
        ArgumentCaptor<Article> articleArg = ArgumentCaptor.forClass(Article.class);
        Mockito.verify(articleRepository, times(1)).save(articleArg.capture());
        assertTrue(result);
    }
    
    @Test
    void addTag_NonExistingArticleId_ReturnFalse() {
        Long id = 123L;
        BDDMockito.given(articleRepository.findById(id)).willReturn(Optional.empty());
        TagCreateDTO dto = new TagCreateDTO(456L, "tag");
        Boolean result = this.underTest.addTag(id, dto);
        ArgumentCaptor<Article> articleArg = ArgumentCaptor.forClass(Article.class);
        Mockito.verify(articleRepository, never()).save(articleArg.capture());
        assertFalse(result);
    }
    
    @Test
    void getAllTagsByArticleId_ExistingArticleId_ReturnTrue() {
        Long id = 123L;
        BDDMockito.given(articleRepository.existsById(id)).willReturn(true);
        this.underTest.getAllTagsByArticleId(id);
        Mockito.verify(tagRepository, times(1)).findAllByArticles_id(id);
    }

    @Test
    void deleteTagFromArticle_ExistingArticleAndTagId_ReturnTrue() {
        Long articleId = 123L;
        Article article = new Article("article title", "article content", LocalDate.parse("2024-01-01"));
        Long tagId = 456L;
        Tag tag = new Tag(tagId, "tag");
        article.addTag(tag);
        BDDMockito.given(articleRepository.findById(articleId)).willReturn(Optional.of(article));
        Boolean result=this.underTest.deleteTagFromArticle(articleId, tagId);
        Mockito.verify(articleRepository, times(1)).save(article);
        assertTrue(result);
    }
    
    @Test
    void deleteTagFromArticle_NonExistingArticleId_ReturnFalse() {
        Long articleId = 123L;
        Long tagId = 456L;
        BDDMockito.given(articleRepository.findById(articleId)).willReturn(Optional.empty());
        Boolean result = this.underTest.deleteTagFromArticle(articleId, tagId);
        ArgumentCaptor<Article> articleArg = ArgumentCaptor.forClass(Article.class);
        Mockito.verify(articleRepository, never()).save(articleArg.capture());
        assertFalse(result);
    }
}
