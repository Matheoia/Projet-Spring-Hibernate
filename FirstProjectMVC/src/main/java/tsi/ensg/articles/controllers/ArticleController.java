package tsi.ensg.articles.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import tsi.ensg.articles.models.Article;
import tsi.ensg.articles.models.Author;
import tsi.ensg.articles.services.ArticleService;
import tsi.ensg.articles.services.AuthorService;

import java.util.List;
import java.util.Optional;

@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private AuthorService authorService;

    @GetMapping("/myarticle")
    public String getMyArticle(Model model) {

        Author author = new Author(1, "Matheo", "Marechal", null);
        Article art = new Article(1, "unArticle", "content", author);

        model.addAttribute("myarticle",art);
        return "article";
    }

    @GetMapping("/articles")
    public String listArticles(Model model) {
        List<Article> articles = articleService.findAll();
        model.addAttribute("articles", articles);
        return "articles";
    }

    @GetMapping("/articles/add")
    public String showAddArticleForm(Model model) {
        List<Author> authors = authorService.findAll();
        Article article = new Article();
        model.addAttribute("authors", authors);
        model.addAttribute("article", article);
        return "addArticle";
    }

    @PostMapping("/articles/add")
    public String addAuthor(@ModelAttribute("addArticle") Article article) {
        articleService.save(article);
        return "redirect:/articles";
    }

    @GetMapping("/article/{id}")
    public String showArticleDetails(@PathVariable Long id, Model model) {
        Optional<Article> optionalArticle = articleService.findById(id);

        if (optionalArticle.isPresent()) {
            model.addAttribute("myarticle", optionalArticle.get());
            return "article";
        } else {
            return "redirect:/error";
        }
    }


}