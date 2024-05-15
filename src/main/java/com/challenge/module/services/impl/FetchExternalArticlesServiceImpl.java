package com.challenge.module.services.impl;

import com.challenge.module.entities.Article;
import com.challenge.module.entities.Tag;
import com.challenge.module.facades.IArticlesFacade;
import com.challenge.module.mappers.AlgoliaJsonToArticleMapper;
import com.challenge.module.repositories.ArticleRepository;
import com.challenge.module.repositories.TagRepository;
import com.challenge.module.services.IFetchExternalDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FetchExternalArticlesServiceImpl implements IFetchExternalDataService {

    private static final Logger logger = LoggerFactory.getLogger(FetchExternalArticlesServiceImpl.class);

    private final IArticlesFacade articlesFacade;

    private final TagRepository tagRepository;

    private final ArticleRepository articleRepository;

    @Autowired
    public FetchExternalArticlesServiceImpl(
            final IArticlesFacade articlesFacade,
            final TagRepository tagRepository,
            final ArticleRepository articleRepository) {
        this.articlesFacade = articlesFacade;
        this.tagRepository = tagRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    @Scheduled(fixedRate = 3600000)
    public void fetchExternalArticles() {
        logger.debug("Starting scheduled task");
        String jsonResponse = fetchData();
        AlgoliaJsonToArticleMapper mapper = new AlgoliaJsonToArticleMapper();
        try {
            List<Article> articles = mapper.mapJsonToArticleObjects(jsonResponse);
            for (Article article : articles) {
                List<Tag> tags = article.getTags();
                if (tags != null && !tags.isEmpty()) {
                    tagRepository.saveAll(tags);
                }
            }
            articleRepository.saveAll(articles);
        } catch (Exception e) {
            logger.error("There was a problem trying to save the articles: {}", e.getMessage());
        }
    }

    @Override
    public String fetchData() {
        logger.debug("Fetching articles from Algolia API");
        return articlesFacade.fetchArticles();
    }
}
