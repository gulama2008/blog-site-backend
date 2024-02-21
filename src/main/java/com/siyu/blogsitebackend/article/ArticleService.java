package com.siyu.blogsitebackend.article;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siyu.blogsitebackend.tag.Tag;
import com.siyu.blogsitebackend.tag.TagCreateDTO;
import com.siyu.blogsitebackend.tag.TagRepository;
import com.siyu.blogsitebackend.tag.TagService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
@Transactional
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagService tagService;

    public List<Article> getAll() {
        return this.articleRepository.findAll();
    }

    public Optional<Article> getById(Long id) {
        Optional<Article> foundArticle = articleRepository.findById(id);
        if(foundArticle.isPresent()){
            Article article = foundArticle.get();
            Article updatedViewsArticle=this.updateViews(article);
            return Optional.of(updatedViewsArticle);
        }
		return Optional.ofNullable(null);
	}

    public Article createArticle(ArticleCreateDTO data) {
        String title = data.getTitle();
        String content = data.getContent();
        LocalDate publishDate = LocalDate.parse(data.getPublishDate());
        Article newArticle = new Article(title, content, publishDate);
        List<Tag> tags = data.getTags();
        Article created = this.articleRepository.save(newArticle);
        System.out.println(tags);
        if (tags != null) {
            for (Tag tag : tags) {
                TagCreateDTO newTagCreateDTO = new TagCreateDTO(tag.getId(), tag.getName());
                this.addTag(created.getId(), newTagCreateDTO);
            }
        }
        return created;
    }

    public boolean deleteById(Long id) {
        Optional<Article> foundArticle = this.articleRepository.findById(id);
        if (foundArticle.isPresent()) {
            this.articleRepository.delete(foundArticle.get());
            return true;
        }
        return false;
    }
    
    public Optional<Article> updateById(Long id, ArticleUpdateDTO data) {
        Optional<Article> foundArticle = this.getById(id);
        if (foundArticle.isPresent()) {
            Article toUpdate = foundArticle.get();
            toUpdate.setTitle(data.getTitle());
            toUpdate.setContent(data.getContent());
            Article updatedArticle = this.articleRepository.save(toUpdate);
            return Optional.of(updatedArticle);
        }
        return foundArticle;
    }
    
    public boolean addTag(Long id, TagCreateDTO tagData) {
        Optional<Article> article = this.getById(id);
        if (article.isPresent()) {
            Article toUpdate = article.get();
            Long tagId = tagData.getId();
            if (tagId != 0L) {
                Optional<Tag> tag = this.tagService.getById(tagId);
                if (tag.isPresent()) {
                    toUpdate.addTag(tag.get());
                    this.articleRepository.save(toUpdate);
                    return true;
                }
            } else {
                Tag created = this.tagService.createTag(tagData);
                toUpdate.addTag(created);
                return true;
            }
        }
        return false;
    }
    
    public Optional<List<Tag>> getAllTagsByArticleId(Long id) {
        if(!this.articleRepository.existsById(id)){
            return Optional.ofNullable(null);
        }
        List<Tag> tags = this.tagRepository.findAllByArticles_id(id);
        return Optional.of(tags);
    }

    public boolean deleteTagFromArticle(Long articleId, Long tagId) {
        Optional<Article> article = articleRepository.findById(articleId);
        if (article.isPresent()) {
            Article found = article.get();
            found.removeTag(tagId);
            articleRepository.save(found);
            return true;
        }
        return false;
    }

    public Article updateViews(Article article) {
        article.setViews(article.getViews() + 1);
        return this.articleRepository.save(article);
    }

    public Optional<Article> updateTagsByArticleId(Long id,ArticleUpdateTagDTO data) {
        Optional<Article> foundArticle = this.getById(id);
        if (foundArticle.isPresent()) {
            Article toUpdate = foundArticle.get();
            Set<Tag> existingTags = toUpdate.getTags();
            Iterator<Tag> it = existingTags.iterator();
            while (it.hasNext()) {
                Tag currentTag = it.next();
                currentTag.getArticles().remove(toUpdate);
            }
            existingTags.clear();
            List<Tag> tags = data.getTags();
            for (Tag tag : tags) {
                toUpdate.addTag(tag);
            }
            Article updatedArticle = this.articleRepository.save(toUpdate);
            return Optional.of(updatedArticle);
        }
        return foundArticle;
    }
}
