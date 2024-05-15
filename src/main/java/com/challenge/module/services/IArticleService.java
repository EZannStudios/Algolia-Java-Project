package com.challenge.module.services;

import com.challenge.module.entities.Article;
import com.challenge.module.models.ArticleRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IArticleService {

    Page<Article> findArticlesByFilters(String author, String title, List<String> tags, String month,
                                        Pageable pageable);

    ResponseEntity<String> saveArticle(ArticleRequest articleRequest);

    ResponseEntity<String> deleteArticle(Long id);
}
