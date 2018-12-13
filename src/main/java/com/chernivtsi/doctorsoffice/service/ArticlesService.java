package com.chernivtsi.doctorsoffice.service;

import com.chernivtsi.doctorsoffice.model.Article;
import com.chernivtsi.doctorsoffice.repository.ArticlesRepository;
import com.chernivtsi.doctorsoffice.service.base.DefaultCrudSupport;
import org.springframework.stereotype.Service;

@Service
public class ArticlesService extends DefaultCrudSupport<Article> {
    private ArticlesRepository articlesRepository;

    public ArticlesService(ArticlesRepository articlesRepository) {
        super(articlesRepository);
        this.articlesRepository = articlesRepository;
    }
}
