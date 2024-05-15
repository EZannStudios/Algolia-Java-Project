package com.challenge.module.services.impl;

import com.challenge.module.entities.Article;
import com.challenge.module.entities.Tag;
import com.challenge.module.models.ArticleRequest;
import com.challenge.module.repositories.ArticleRepository;
import com.challenge.module.repositories.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ArticleServiceImplTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private ArticleServiceImpl articleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindArticlesByFilters() {
        String author = "John Doe";
        String title = "Test Article";
        List<String> tags = Arrays.asList("Java", "Spring");
        String month = "January";
        Pageable pageable = Pageable.unpaged();

        when(articleRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(Page.empty());

        Page<Article> result = articleService.findArticlesByFilters(author, title, tags, month, pageable);

        assertNotNull(result);
        verify(articleRepository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void testFindArticlesByFiltersAuthorIsNull() {
        String title = "Test Article";
        List<String> tags = Arrays.asList("Java", "Spring");
        String month = "January";
        Pageable pageable = Pageable.unpaged();

        when(articleRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(Page.empty());

        Page<Article> result = articleService.findArticlesByFilters(null, title, tags, month, pageable);

        assertNotNull(result);
        verify(articleRepository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void testFindArticlesByFiltersTitleIsNull() {
        String author = "John Doe";
        List<String> tags = Arrays.asList("Java", "Spring");
        String month = "January";
        Pageable pageable = Pageable.unpaged();

        when(articleRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(Page.empty());

        Page<Article> result = articleService.findArticlesByFilters(author, null, tags, month, pageable);

        assertNotNull(result);
        verify(articleRepository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void testSaveArticleSuccess() {
        ArticleRequest articleRequest = new ArticleRequest();
        articleRequest.setAuthor("John Doe");
        articleRequest.setCommentText("Interesting article about Spring Boot.");
        articleRequest.setStoryTitle("Spring Boot Basics");
        articleRequest.setStoryUrl("http://example.com");
        articleRequest.setTags(Arrays.asList("Programming", "Tutorial"));

        when(tagRepository.saveAll(anyList())).thenReturn(Arrays.asList(new Tag())); // Assuming Tag has an ID set by DB
        when(articleRepository.save(any(Article.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<String> response = articleService.saveArticle(articleRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(tagRepository).saveAll(anyList());
        verify(articleRepository).save(any(Article.class));
    }

    @Test
    void testSaveArticleFailure() {
        ArticleRequest articleRequest = new ArticleRequest();
        articleRequest.setTags(List.of("Java", "Spring"));

        when(tagRepository.saveAll(anyList())).thenThrow(new RuntimeException("DB error"));

        ResponseEntity<String> response = articleService.saveArticle(articleRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("problem"));
    }

    @Test
    void testDeleteArticle() {
        Long articleId = 1L;
        Article article = new Article();
        article.setId(articleId);

        article.setTags(getTags());
        when(articleRepository.getReferenceById(articleId)).thenReturn(article);

        ResponseEntity<String> response = articleService.deleteArticle(articleId);

        verify(articleRepository).deleteById(articleId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteArticleFailure() {
        Long articleId = 1L;
        when(articleRepository.getReferenceById(articleId)).thenThrow(new RuntimeException("Not found"));

        ResponseEntity<String> response = articleService.deleteArticle(articleId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    private List<Tag> getTags() {
        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setDescription("Java");
        tags.add(tag);

        Tag tag2 = new Tag();
        tag.setId(2L);
        tag.setDescription("Spring");
        tags.add(tag2);

        return tags;
    }
}
