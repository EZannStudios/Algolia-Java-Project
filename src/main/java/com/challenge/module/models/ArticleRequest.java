package com.challenge.module.models;

import com.challenge.module.utils.HtmlDecodeDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class ArticleRequest {

    private String author;

    @JsonProperty("comment_text")
    @JsonDeserialize(using = HtmlDecodeDeserializer.class)
    private String commentText;

    @JsonProperty("created_at")
    private OffsetDateTime createdAt;

    @JsonProperty("story_title")
    private String storyTitle;

    @JsonProperty("story_url")
    private String storyUrl;

    @JsonProperty("_tags")
    private List<String> tags;
}
