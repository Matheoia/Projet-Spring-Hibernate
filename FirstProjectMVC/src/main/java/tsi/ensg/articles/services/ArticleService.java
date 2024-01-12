package tsi.ensg.articles.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tsi.ensg.articles.models.Article;
import tsi.ensg.articles.repositories.ArticleRepo;
import tsi.ensg.articles.repositories.AuthorRepo;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepo repository;

    public ArticleService(ArticleRepo repository) {
        this.repository = repository;
    }

    public List<Article> findAll() {
        return (List<Article>) repository.findAll();
    }

    public void save(Article article) {
        repository.save(article);
    }

    public Optional<Article> findById(Long id) {
        return repository.findById(id);
    }

    public void delete(Article article) {
        repository.delete(article);
    }
}
