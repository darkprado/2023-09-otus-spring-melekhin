package ru.otus.hw.repositories;

import java.util.List;
import java.util.Optional;

import ru.otus.hw.models.Comment;

/**
 * @author s.melekhin
 * @since 12 нояб. 2023 г.
 */
public interface CommentRepository {

    Optional<Comment> findById(Long id);

    List<Comment> findAllByBookId(Long bookId);

    Comment save(Comment comment);

    void deleteById(Long id);

}
