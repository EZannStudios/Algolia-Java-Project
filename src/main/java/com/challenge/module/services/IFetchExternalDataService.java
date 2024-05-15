package com.challenge.module.services;

import org.springframework.stereotype.Service;

@Service
public interface IFetchExternalDataService {
    void fetchExternalArticles();
    String fetchData();
}
