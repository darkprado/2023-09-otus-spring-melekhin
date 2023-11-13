package ru.otus.hw.repositories.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.CommentRepository;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

/**
 * @author s.melekhin
 * @since 12 нояб. 2023 г.
 */
@Repository
@RequiredArgsConstructor
public class CommentRepositoryORM implements CommentRepository {

    private static final String IDENTIFIER = "id";

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<Comment> findById(Long id) {
        return Optional.of(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> findAllByBookId(Long bookId) {
        EntityGraph<?> entityGraph = em.getEntityGraph("comment-graph");
        TypedQuery<Comment> query = em.createQuery("select c from Comment c where c.book.id = :id", Comment.class)
                .setParameter(IDENTIFIER, bookId);
        query.setHint(FETCH.getKey(), entityGraph);
        return query.getResultList();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == null) {
            em.persist(comment);
            em.flush();
        } else {
            em.merge(comment);
        }
        return comment;
    }

    @Override
    public void deleteById(Long id) {
        em.createQuery("delete from Comment c where c.id = :id")
                .setParameter(IDENTIFIER, id)
                .executeUpdate();
    }
}
