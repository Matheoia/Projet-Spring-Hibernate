package tsi.ensg.articles.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import tsi.ensg.articles.models.Author;
import tsi.ensg.articles.services.ArticleService;
import tsi.ensg.articles.services.AuthorService;

import java.util.List;

@Controller
public class AuthorController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private AuthorService authorService;

    @GetMapping("/authors")
    public String listAuthors(Model model) {
        List<Author> authors = authorService.findAll();
        model.addAttribute("authors", authors);
        return "authors"; // Assurez-vous que le nom du fichier correspond au nom réel de votre fichier HTML
    }


    @GetMapping("/authors/add")
    public String showAddAuthorForm(Model model) {
        Author author = new Author();
        model.addAttribute("author", author);
        return "author";
    }

    @PostMapping("/authors/add")
    public String addAuthor(@ModelAttribute("author") Author author) {
        authorService.save(author);
        return "redirect:/authors";
    }
}