package com.challenge.module.initializer;

import com.challenge.module.entities.Article;
import com.challenge.module.entities.Tag;
import com.challenge.module.repositories.ArticleRepository;
import com.challenge.module.repositories.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;

    public DataInitializer(final ArticleRepository articleRepository, final TagRepository tagRepository) {
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public void run(final String... args) {
        Tag tag1 = new Tag();
        tag1.setDescription("Java");

        Tag tag2 = new Tag();
        tag2.setDescription("Spring");

        tagRepository.save(tag1);
        tagRepository.save(tag2);

        Article article1 = new Article();
        article1.setAuthor("John Doe");
        article1.setCommentText("Great article!");
        article1.setCreatedAt(OffsetDateTime.now());
        article1.setStoryTitle("Spring Boot Tutorial");
        article1.setStoryUrl("https://example.com/spring-boot");
        List<Tag> tagsForArticle1 = new ArrayList<>();
        tagsForArticle1.add(tag1);
        tagsForArticle1.add(tag2);
        article1.setTags(tagsForArticle1);

        Article article2 = new Article();
        article2.setAuthor("Jane Smith");
        article2.setCommentText("Another great article!");
        article2.setCreatedAt(OffsetDateTime.now());
        article2.setStoryTitle("Hibernate Basics");
        article2.setStoryUrl("https://example.com/hibernate-basics");
        List<Tag> tagsForArticle2 = new ArrayList<>();
        tagsForArticle2.add(tag1);
        article2.setTags(tagsForArticle2);

        articleRepository.save(article1);
        articleRepository.save(article2);

        logger.info("Data initialization completed.");
    }
}

