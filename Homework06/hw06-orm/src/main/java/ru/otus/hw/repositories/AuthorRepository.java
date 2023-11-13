package ru.otus.hw.repositories;

import java.util.List;
import java.util.Optional;

import ru.otus.hw.models.Author;

public interface AuthorRepository {

    List<Author> findAll();

    Optional<Author> findById(Long id);

    Author save(Author genre);

    long deleteById(Long id);
}
