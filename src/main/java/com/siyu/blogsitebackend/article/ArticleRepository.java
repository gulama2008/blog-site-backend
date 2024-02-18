package com.siyu.blogsitebackend.article;

import org.springframework.data.jpa.repository.JpaRepository;
import com.siyu.blogsitebackend.tag.Tag;
import java.util.List;


public interface ArticleRepository extends JpaRepository<Article,Long>{

    List<Article> findAllByTags_id(Long id);

    Article findByComments_id(Long id);
}
