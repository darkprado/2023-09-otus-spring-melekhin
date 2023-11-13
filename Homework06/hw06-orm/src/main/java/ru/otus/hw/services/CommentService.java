package ru.otus.hw.services;

import java.util.List;
import java.util.Optional;

import ru.otus.hw.models.Comment;

/**
 * @author s.melekhin
 * @since 13 нояб. 2023 г.
 */
public interface CommentService {


    List<Comment> findAllByBookId(Long id);

    Optional<Comment> findById(Long id);

    Comment insert(String text, Long bookId);

    Comment update(Long id, String text, Long bookId);

    void deleteById(Long id);

}
