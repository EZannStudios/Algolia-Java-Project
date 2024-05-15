package com.challenge.module.mappers;

import com.challenge.module.entities.Article;
import com.challenge.module.testutils.JsonFileReader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlgoliaJsonToArticleMapperTest {

    private AlgoliaJsonToArticleMapper mapper;

    private String jsonContent;

    @BeforeEach
    public void setup() {
        mapper = new AlgoliaJsonToArticleMapper();
        jsonContent = JsonFileReader.getMockedJsonStream();
    }

    @Test
    public void testMapJsonToArticleObjects() throws IOException {
        List<Article> articles = mapper.mapJsonToArticleObjects(jsonContent);

        assertFalse(articles.isEmpty(), "The list of articles should not be empty");
        assertEquals("ExampleAuthor", articles.get(0).getAuthor(),
                "The author of the first article should match");
    }

    @Test
    public void testMapHitToArticle() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonContent);
        JsonNode hitNode = rootNode.get("hits").get(0);

        Article article = mapper.mapHitToArticle(hitNode);

        assertNotNull(article, "The article should not be null");
        assertEquals("ExampleAuthor", article.getAuthor(), "The author should match");
        assertTrue(article.getTags().stream().anyMatch(tag -> tag.getDescription().equals("author_ExampleAuthor")),
                "Should contain expected tag");
    }
}
