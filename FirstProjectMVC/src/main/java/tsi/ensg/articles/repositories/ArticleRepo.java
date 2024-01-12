package tsi.ensg.articles.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tsi.ensg.articles.models.Article;

@Repository
public interface ArticleRepo extends CrudRepository<Article, Long> {

}
