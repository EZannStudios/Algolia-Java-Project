package com.challenge.module.facades.impl;

import com.challenge.module.facades.HackerNewsClient;
import com.challenge.module.facades.IArticlesFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlgoliaApiArticlesFacadeImpl implements IArticlesFacade {

    private static final Logger logger = LoggerFactory.getLogger(AlgoliaApiArticlesFacadeImpl.class);

    private final HackerNewsClient hackerNewsClient;

    @Autowired
    public AlgoliaApiArticlesFacadeImpl(final HackerNewsClient hackerNewsClient) {
        this.hackerNewsClient = hackerNewsClient;
    }

    @Override
    public String fetchArticles() {
        try {
            logger.debug("Fetching articles from Hacker News API");
            return hackerNewsClient.fetchArticles();
        } catch (Exception e) {
            logger.error("Failed to get articles: {}", e.getMessage());
            return null;
        }
    }
}
