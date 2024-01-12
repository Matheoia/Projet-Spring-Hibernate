package tsi.ensg.articles.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tsi.ensg.articles.models.Author;
import tsi.ensg.articles.repositories.AuthorRepo;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepo repository;

    public AuthorService(AuthorRepo repository) {
        this.repository = repository;
    }


    public List<Author> findAll() {
        return (List<Author>) repository.findAll();
    }

    public void save(Author author) {
        repository.save(author);
    }

    public Optional<Author> findById(Long id) {
        return repository.findById(id);
    }

    public void delete(Author author) {
        repository.delete(author);
    }
}
