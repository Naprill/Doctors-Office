package com.chernivtsi.doctorsoffice.controller;

import com.chernivtsi.doctorsoffice.model.Article;
import com.chernivtsi.doctorsoffice.service.ArticlesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/articles")
public class ArticlesController {

	private static final String REDIRECT_ARTICLES =  "redirect:/articles";
	private static final String STATUS = "status";
    private ArticlesService articlesService;

    public ArticlesController(ArticlesService articlesService) {
        this.articlesService = articlesService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/add")
    public String getCreateArticlePage(){
        return "add-article";
    }

    @ModelAttribute("article")
    public Article newArticle() {
        return new Article();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String createArticle(@ModelAttribute("article") @Valid Article article,
                                BindingResult result,
                                final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "add-article";
        } else {
            articlesService.create(article);
            log.trace("createArticle(): {}", article.toString());
            redirectAttributes.addFlashAttribute(STATUS, "success");
            return REDIRECT_ARTICLES;
        }

    }

    @GetMapping
    public ModelAndView getArticles() {
        List<Article> articles = articlesService.findAll();
        log.trace("articles: {}", articles);
        ModelAndView modelAndView = new ModelAndView("articles");
        modelAndView.addObject("articles", articles);
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable Long id) {
        Article article = articlesService.findById(id).orElseThrow(EntityNotFoundException::new);
        log.info("getArticle: {}", article);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update")
    public String updateArticle(@ModelAttribute("article") @Valid Article article,
                                BindingResult result,
                                final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(STATUS, "failure");
            return REDIRECT_ARTICLES;
        } else {
            articlesService.update(article);
            log.trace("updateArticle(): {}", article.toString());
            redirectAttributes.addFlashAttribute(STATUS, "success");
            return REDIRECT_ARTICLES;
        }

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public @ResponseBody String deleteArticle(@PathVariable Long id) {
        articlesService.delete(id);
        return REDIRECT_ARTICLES;
    }
}
