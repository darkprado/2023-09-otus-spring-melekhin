package ru.otus.hw.repositories.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author s.melekhin
 * @since 13 нояб. 2023 г.
 */
@DisplayName("Репозиторий на основе ORM для работы с комментариями")
@DataJpaTest
@Import({ BookRepositoryORM.class, CommentRepositoryORM.class })
class CommentRepositoryORMTest {

    @Autowired
    private CommentRepositoryORM repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен загружать комментарий по id")
    @Test
    void shouldReturnCorrectCommentById() {
        Comment expectedComment = em.find(Comment.class, 1);
        var actualComment = repository.findById(expectedComment.getId());

        assertThat(actualComment).isPresent()
                .get()
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expectedComment);
    }

    @DisplayName("должен загружать список всех комментариев по id книги")
    @Test
    void shouldReturnCorrectCommentsList() {
        Book book = new Book(1L, "title", new Author(), new Genre());
        var expectedComment = em.getEntityManager().createQuery("select c from Comment c where c.book.id = :bookId", Comment.class)
                .setParameter("bookId", book.getId()).getResultList();
        var actualComments = repository.findAllByBook(book);
        for (int i = 0; i < actualComments.size(); i++) {
            assertThat(actualComments.get(i))
                    .usingRecursiveComparison()
                    .ignoringExpectedNullFields()
                    .isEqualTo(expectedComment.get(i));
        }
    }

    @DisplayName("должен сохранять новый комментарий")
    @Test
    void shouldSaveNewComment() {
        var expectedComment = new Comment(null, "text", em.find(Book.class, 1));
        var returnedComment = repository.save(expectedComment);
        assertThat(returnedComment).isNotNull()
                .matches(comment -> comment.getId() > 0)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expectedComment);

        assertThat(em.find(Comment.class, returnedComment.getId()))
                .isEqualTo(returnedComment);
    }

    @DisplayName("должен сохранять измененный комментарий")
    @Test
    void shouldSaveUpdatedComment() {
        var expectedComment = new Comment(1L, "Comment_12", em.find(Book.class, 1));

        assertThat(em.find(Comment.class, expectedComment.getId()))
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isNotEqualTo(expectedComment);

        var returnedComment = repository.save(expectedComment);
        assertThat(returnedComment).isNotNull()
                .matches(comment -> comment.getId() > 0)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expectedComment);

        assertThat(em.find(Comment.class, returnedComment.getId()))
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(returnedComment);
    }

    @DisplayName("должен удалять комментарий по id ")
    @Test
    void shouldDeleteComment() {
        Book book = new Book(1L, "title", new Author(), new Genre());
        long before = repository.findAllByBook(book).size();
        repository.deleteById(1L);
        long after = repository.findAllByBook(book).size();
        assertThat(before).isGreaterThan(after);
    }

}