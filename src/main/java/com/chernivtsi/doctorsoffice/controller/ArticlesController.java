package com.chernivtsi.doctorsoffice.controller;

import com.chernivtsi.doctorsoffice.model.Article;
import com.chernivtsi.doctorsoffice.service.ArticlesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller

@RequestMapping("/articles")
public class ArticlesController {

    private ArticlesService articlesService;

    public ArticlesController(ArticlesService articlesService) {
        this.articlesService = articlesService;
    }

    @GetMapping
    public ModelAndView getArticles() {
        List<Article> articles = articlesService.findAll();
        ModelAndView modelAndView = new ModelAndView("articles");
        modelAndView.addObject("articles", articles);
        return modelAndView;
    }
}
