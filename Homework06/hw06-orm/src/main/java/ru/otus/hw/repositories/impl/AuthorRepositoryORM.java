package ru.otus.hw.repositories.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.models.Author;
import ru.otus.hw.repositories.AuthorRepository;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryORM implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Author> findAll() {
        return em.createQuery("select a from Author a", Author.class)
                .getResultList();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return Optional.of(em.find(Author.class, id));
    }

    @Override
    public Author save(Author author) {
        if (author.getId() == null) {
            em.persist(author);
        } else {
            author = em.merge(author);
        }
        return author;
    }

    @Override
    public void deleteById(Long id) {
        em.remove(em.find(Author.class, id));
    }

}
