package com.challenge.module.controllers;

import com.challenge.module.entities.Article;
import com.challenge.module.models.ArticleRequest;
import com.challenge.module.services.IArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Articles", description = "The Articles API")
public class ArticleController {

    private final IArticleService articleService;

    @GetMapping("/articles")
    @Operation(summary = "Get a list of articles",
            description = "Returns a paginated list of articles with filtering options",
            responses = {
                    @ApiResponse(description = "Successful Operation", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Article.class))),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content)
            })
    @PageableAsQueryParam
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<Page<Article>> getArticles(
            @RequestParam(required = false) final String author,
            @RequestParam(required = false) final String title,
            @RequestParam(required = false) final List<String> tags,
            @RequestParam(required = false) final String month,
            @PageableDefault(size = 5) @ParameterObject final Pageable pageable) {

        Page<Article> articlePage = articleService.findArticlesByFilters(author, title, tags, month, pageable);
        return ResponseEntity.ok(articlePage);
    }

    @Autowired
    public ArticleController(final IArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/articles")
    @Operation(summary = "Save an article", description = "Saves a new article to the database",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Article saved successfully", content = @Content),
                    @ApiResponse(responseCode = "400",
                            description = "Invalid article data supplied", content = @Content),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized", content = @Content)
            })
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<String> saveArticle(@RequestBody final ArticleRequest articleRequest) {
        return articleService.saveArticle(articleRequest);
    }

    @DeleteMapping("/articles/{id}")
    @Operation(summary = "Delete an article",
            description = "Deletes an article from the database based on the article ID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Article deleted successfully", content = @Content),
                    @ApiResponse(responseCode = "404",
                            description = "Article not found", content = @Content),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized", content = @Content)
            })
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<String> deleteArticle(
            @Parameter(description = "ID of the article to be deleted", required = true)
            @PathVariable("id") final Long id) {
        return articleService.deleteArticle(id);
    }
}
