package ru.otus.hw.repositories.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.CommentRepository;

/**
 * @author s.melekhin
 * @since 12 нояб. 2023 г.
 */
@Repository
@RequiredArgsConstructor
public class CommentRepositoryORM implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<Comment> findById(Long id) {
        return Optional.of(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> findAllByBook(Book book) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c where c.book.id = :id", Comment.class)
                .setParameter("id", book.getId());
        return query.getResultList();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == null) {
            em.persist(comment);
        } else {
            comment = em.merge(comment);
        }
        return comment;
    }

    @Override
    public void deleteById(Long id) {
        em.remove(em.find(Comment.class, id));
    }
}
