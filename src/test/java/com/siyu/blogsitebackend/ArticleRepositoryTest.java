package com.siyu.blogsitebackend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.siyu.blogsitebackend.article.Article;
import com.siyu.blogsitebackend.article.ArticleRepository;

@DataJpaTest
public class ArticleRepositoryTest {
    
    @Autowired
    private ArticleRepository underTest;

    // @Test
    // void getArticlesByMonthAndYear_ReturnCountOfArticlesByMonthAndYear() {
    //     LocalDate publishDate1 = LocalDate.parse("2024-03-01");
    //     Article article1 = new Article("article1", "for test1", publishDate1);
    //     LocalDate publishDate2 = LocalDate.parse("2024-03-10");
    //     Article article2 = new Article("article2", "for test3", publishDate2);
    //     LocalDate publishDate3 = LocalDate.parse("2024-02-10");
    //     Article article3 = new Article("article3", "for test3", publishDate3);
    //     this.underTest.save(article1);
    //     this.underTest.save(article2);
    //     this.underTest.save(article3);

    //     List<Object> result = this.underTest.getArticlesByMonthAndYear();
    //     assertEquals(2, result.size());

        


    // }
}
