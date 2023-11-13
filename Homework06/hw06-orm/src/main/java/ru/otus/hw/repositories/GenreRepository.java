package ru.otus.hw.repositories;

import java.util.List;
import java.util.Optional;

import ru.otus.hw.models.Genre;

public interface GenreRepository {

    List<Genre> findAll();

    Optional<Genre> findById(Long id);

    Genre save(Genre genre);

    long deleteById(Long id);
}
