package ru.otus.hw.repositories;

import java.util.List;
import java.util.Optional;

import ru.otus.hw.models.Book;

public interface BookRepository {

    Optional<Book> findById(Long id);

    List<Book> findAll();

    Book save(Book book);

    void deleteById(Long id);
}
