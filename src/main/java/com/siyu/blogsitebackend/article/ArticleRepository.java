package com.siyu.blogsitebackend.article;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.time.LocalDate;
import java.util.List;


public interface ArticleRepository extends JpaRepository<Article,Long>{

    List<Article> findAllByTags_id(Long id);

    Article findByComments_id(Long id);

    // @Query(
    //     value = "SELECT COUNT(*) AS count, DATE_FORMAT(`date`, '%Y-%m') AS month FROM `articles` GROUP BY month ORDER BY month DESC",
    //     nativeQuery = true)
    // Collection<Article> CountArticlesByMonthAndYear();
    @Query(value = "SELECT COUNT(*) as count,DATE_FORMAT(`publish_date`,'%Y-%m') AS month from articles " +
        "group by month " +
        "order by month desc", nativeQuery = true)
    List getArticlesByMonthAndYear();

    List<Article> findAllByPublishDateBetween(LocalDate startDate, LocalDate endDate);

    List<Article> findByContentContains(String str);

}
