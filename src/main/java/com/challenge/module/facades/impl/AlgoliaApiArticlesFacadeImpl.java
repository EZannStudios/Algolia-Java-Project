package com.challenge.module.facades.impl;

import com.challenge.module.facades.IArticlesFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class AlgoliaApiArticlesFacadeImpl implements IArticlesFacade {

    private static final Logger logger = LoggerFactory.getLogger(AlgoliaApiArticlesFacadeImpl.class);

    private static final String HACKER_NEWS_API_URL = "https://hn.algolia.com/api/v1/search_by_date?query=java";

    @Override
    public String fetchArticles() {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(HACKER_NEWS_API_URL);
            logger.debug("Get articles from: {}", url);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(HttpMethod.GET.name());

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            conn.disconnect();
        } catch (IOException e) {
            logger.error("Failed to get articles: {}", e.getMessage());
        }

        return response.toString();
    }
}
