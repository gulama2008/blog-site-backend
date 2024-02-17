package com.siyu.blogsitebackend.article;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale.Category;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siyu.blogsitebackend.tag.Tag;
import com.siyu.blogsitebackend.tag.TagAddDTO;
import com.siyu.blogsitebackend.tag.TagCreateDTO;
import com.siyu.blogsitebackend.tag.TagRepository;
import com.siyu.blogsitebackend.tag.TagService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TagService tagService;

    public List<Article> getAll() {
        return this.articleRepository.findAll();
    }

    public Optional<Article> getById(Long id) {
		Optional<Article> foundArticle = articleRepository.findById(id);
		return foundArticle;
	}

    public Optional<Article> createArticle(ArticleCreateDTO data) {
        String title = data.getTitle();
        String content = data.getContent();
        LocalDate publishDate = data.getPublishDate();
        Article newArticle = new Article(title, content, publishDate);
        List<Tag> tags = data.getTags();
        Article created = this.articleRepository.save(newArticle);
        if (tags != null) {
            for (int i=0;i<tags.size();i++) {
                TagCreateDTO newTagCreateDTO = new TagCreateDTO(tags.get(i).getName());
                boolean added = this.addTag(created.getId(), newTagCreateDTO);
                if (!added) {
                    return Optional.of(null);
                }
            }
        }
        
        return Optional.of(created);
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
    
    public boolean addTag(Long id,TagCreateDTO tagData) {
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
                Tag created=this.tagService.createTag(tagData);
                toUpdate.addTag(created);
                return true;
            }
        }
        return false;
    }
}
