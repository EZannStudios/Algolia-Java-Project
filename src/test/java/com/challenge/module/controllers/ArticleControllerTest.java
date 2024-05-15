package com.challenge.module.controllers;

import com.challenge.module.entities.Article;
import com.challenge.module.models.ArticleRequest;
import com.challenge.module.services.IArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArticleControllerTest {

    @Mock
    private IArticleService articleService;

    @InjectMocks
    private ArticleController articleController;

    private ArticleRequest articleRequest;

    private Article article;

    @BeforeEach
    void setUp() {
        article = new Article();
        article.setId(1L);
        article.setAuthor("Test Author");

        articleRequest = new ArticleRequest();
        articleRequest.setAuthor("Test Author");
    }

    @Test
    void getArticles() {
        Page<Article> articles = new PageImpl<>(Collections.singletonList(article));
        when(articleService.findArticlesByFilters(anyString(), anyString(), anyList(), anyString(), any())).thenReturn(articles);

        ResponseEntity<Page<Article>> response = articleController.getArticles("Test Author", "test title", List.of("java", "spring"), "april", PageRequest.of(0, 5));
        assertThat(response.getBody()).isEqualTo(articles);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);

        verify(articleService).findArticlesByFilters(anyString(), anyString(), anyList(), anyString(), any());
    }

    @Test
    void saveArticle() {
        when(articleService.saveArticle(any(ArticleRequest.class))).thenReturn(ResponseEntity.ok("Article saved successfully"));

        ResponseEntity<String> response = articleController.saveArticle(articleRequest);
        assertThat(response.getBody()).isEqualTo("Article saved successfully");
        assertThat(response.getStatusCodeValue()).isEqualTo(200);

        verify(articleService).saveArticle(any(ArticleRequest.class));
    }

    @Test
    void deleteArticle() {
        when(articleService.deleteArticle(anyLong())).thenReturn(ResponseEntity.ok("Article deleted successfully"));

        ResponseEntity<String> response = articleController.deleteArticle(1L);
        assertThat(response.getBody()).isEqualTo("Article deleted successfully");
        assertThat(response.getStatusCodeValue()).isEqualTo(200);

        verify(articleService).deleteArticle(anyLong());
    }
}
