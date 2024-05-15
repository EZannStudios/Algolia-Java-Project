package com.challenge.module.services.impl;

import com.challenge.module.entities.Article;
import com.challenge.module.entities.Tag;
import com.challenge.module.models.ArticleRequest;
import com.challenge.module.repositories.ArticleRepository;
import com.challenge.module.repositories.TagRepository;
import com.challenge.module.services.IArticleService;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ArticleServiceImpl implements IArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
    private static final String AUTHOR_CRITERIA = "author";
    private static final String STORY_TITLE_CRITERIA = "storyTitle";
    private static final String DESCRIPTION_CRITERIA = "description";
    private static final String TAGS_CRITERIA = "tags";
    private static final String CREATED_AT_CRITERIA = "createdAt";

    private final ArticleRepository articleRepository;

    private final TagRepository tagRepository;

    @Autowired
    public ArticleServiceImpl(
            final ArticleRepository articleRepository,
            final TagRepository tagRepository) {
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public Page<Article> findArticlesByFilters(final String author, final String title, final List<String> tags,
                                               final String month, final Pageable pageable) {
        return articleRepository.findAll((Specification<Article>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (author != null && !author.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get(AUTHOR_CRITERIA), author));
            }

            if (title != null && !title.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get(STORY_TITLE_CRITERIA), "%" + title + "%"));
            }

            if (tags != null && !tags.isEmpty()) {
                predicates.add(root.join(TAGS_CRITERIA).get(DESCRIPTION_CRITERIA).in(tags));
            }

            Integer monthNumber = convertMonthToNumber(month);
            if (monthNumber != null) {
                Expression<Integer> monthExtract = criteriaBuilder.function(
                        "date_part", Integer.class,
                        criteriaBuilder.literal("month"),
                        root.get(CREATED_AT_CRITERIA)
                );
                predicates.add(criteriaBuilder.equal(monthExtract, monthNumber));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }

    @Override
    public ResponseEntity<String> saveArticle(final ArticleRequest articleRequest) {
        Article article = populateArticle(articleRequest);
        List<Tag> tags = getTagList(articleRequest.getTags());
        article.setTags(tags);

        try {
            tagRepository.saveAll(tags);
            articleRepository.save(article);
        } catch (Exception e) {
            logger.error("Failed to create the article: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .body("There was a problem trying to create the article.");
        }

        return ResponseEntity.ok("Article created.");
    }

    @Override
    @Transactional
    public ResponseEntity<String> deleteArticle(final Long id) {
        try {
            Article article = articleRepository.getReferenceById(id);
            if (article != null) {
                article.getTags().clear();
                articleRepository.deleteById(id);
            }
        } catch (Exception e) {
            logger.error("Failed to delete article {}: {}", id, e.getMessage());
            return ResponseEntity
                    .internalServerError()
                    .body("Failed to process your request due to an internal error.");
        }
        return ResponseEntity.ok("Article deleted successfully.");
    }

    private Article populateArticle(final ArticleRequest articleRequest) {
        Article article = new Article();
        article.setAuthor(articleRequest.getAuthor());
        article.setCommentText(articleRequest.getCommentText());
        article.setCreatedAt(articleRequest.getCreatedAt());
        article.setStoryTitle(articleRequest.getStoryTitle());
        article.setStoryUrl(articleRequest.getStoryUrl());
        return article;
    }

    private List<Tag> getTagList(final List<String> articleTags) {
        List<Tag> tags = new ArrayList<>();
        for (String tagDescription : articleTags) {
            Tag tag = new Tag();
            tag.setDescription(tagDescription);
            tags.add(tag);
        }
        return tags;
    }

    private Integer convertMonthToNumber(final String monthName) {
        return isValidMonth(monthName) ? Month.valueOf(monthName.toUpperCase()).getValue() : null;
    }

    private boolean isValidMonth(final String monthName) {
        List<String> months = Arrays.stream(Month.values())
                .map(Month::name)
                .toList();
        return monthName != null && months.contains(monthName.toUpperCase());
    }
}
