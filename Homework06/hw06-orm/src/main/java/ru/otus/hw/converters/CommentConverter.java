package ru.otus.hw.converters;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.models.Comment;

/**
 * @author s.melekhin
 * @since 13 нояб. 2023 г.
 */
@RequiredArgsConstructor
@Component
public class CommentConverter {

    public String commentToString(Comment comment) {
        return "Id: %d, text: %s, bookId: %s, bookTitle: {%s}".formatted(
                comment.getId(),
                comment.getText(),
                comment.getBook().getId(),
                comment.getBook().getTitle());
    }

}
