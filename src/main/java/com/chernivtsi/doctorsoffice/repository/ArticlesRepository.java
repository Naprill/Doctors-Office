package com.chernivtsi.doctorsoffice.repository;

import com.chernivtsi.doctorsoffice.model.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticlesRepository extends CrudRepository<Article, Long> {
}
