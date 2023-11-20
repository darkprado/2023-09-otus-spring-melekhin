package ru.otus.hw.repositories.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.GenreRepository;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryORM implements GenreRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Genre> findAll() {
        return em.createQuery("select a from Genre a", Genre.class)
                .getResultList();
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return Optional.of(em.find(Genre.class, id));
    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == null) {
            em.persist(genre);
        } else {
            genre = em.merge(genre);
        }
        return genre;
    }

    @Override
    public void deleteById(Long id) {
        em.remove(em.find(Genre.class, id));
    }

}