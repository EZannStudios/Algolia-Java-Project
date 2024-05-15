package com.challenge.module.mappers;

import com.challenge.module.entities.Article;
import com.challenge.module.entities.Tag;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlgoliaJsonToArticleMapper {

    private static final Logger logger = LoggerFactory.getLogger(AlgoliaJsonToArticleMapper.class);

    private static final String NODE_ROOT = "hits";
    private static final String NODE_AUTHOR = "author";
    private static final String NODE_COMMENT_TEXT = "comment_text";
    private static final String NODE_CREATED_AT = "created_at";
    private static final String NODE_STORY_TITLE = "story_title";
    private static final String NODE_STORY_URL = "story_url";
    private static final String NODE_TAGS = "_tags";

    public final List<Article> mapJsonToArticleObjects(final String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new ParameterNamesModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        JsonNode root = mapper.readTree(json);
        JsonNode hitsNode = root.get(NODE_ROOT);

        List<Article> articles = new ArrayList<>();
        if (hitsNode instanceof ArrayNode) {
            addArticles(hitsNode, articles);
        }
        return articles;
    }

    public Article mapHitToArticle(final JsonNode hitNode) {
        Article article;

        JsonNode tagsNode;
        try {
            article = populateArticleFromJson(hitNode);
            tagsNode = getJsonNode(hitNode, NODE_TAGS);
            logger.debug("Json object read");
        } catch (Exception e) {
            logger.error("There was a problem reading the json object: {}", e.getMessage());
            return null;
        }

        List<Tag> tags = new ArrayList<>();
        if (tagsNode instanceof ArrayNode) {
            addTagsNodes(tagsNode, tags);
        }
        article.setTags(tags);

        return article;
    }

    private JsonNode getJsonNode(final JsonNode json, final String NodeName) {
        return Optional.ofNullable(json)
                .map(j -> j.get(NodeName))
                .orElseThrow(() -> new IllegalArgumentException("Invalid JSON Object"));
    }

    private void addArticles(final JsonNode hitsNode, final List<Article> articles) {
        ArrayNode hitsArray = (ArrayNode) hitsNode;
        for (JsonNode hitNode : hitsArray) {
            Article article = mapHitToArticle(hitNode);
            if (article != null) {
                articles.add(article);
            }
        }
    }

    private Article populateArticleFromJson(final JsonNode hitNode) {
        Article article = new Article();
        article.setAuthor(getJsonNode(hitNode, NODE_AUTHOR).asText());
        article.setCommentText(getJsonNode(hitNode, NODE_COMMENT_TEXT).asText());
        article.setCreatedAt(OffsetDateTime.parse(getJsonNode(hitNode, NODE_CREATED_AT).asText()));
        article.setStoryTitle(getJsonNode(hitNode, NODE_STORY_TITLE).asText());
        article.setStoryUrl(getJsonNode(hitNode, NODE_STORY_URL).asText());
        return article;
    }

    private void addTagsNodes(final JsonNode tagsNode, final List<Tag> tags) {
        ArrayNode tagsArray = (ArrayNode) tagsNode;
        for (JsonNode tag : tagsArray) {
            Tag tagObject = new Tag();
            tagObject.setDescription(tag.asText());
            tags.add(tagObject);
        }
    }
}
