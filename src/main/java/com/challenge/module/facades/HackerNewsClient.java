package com.challenge.module.facades;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "hackerNewsClient", url = "https://hn.algolia.com")
public interface HackerNewsClient {

    @GetMapping("/api/v1/search_by_date?query=java")
    String fetchArticles();
}
