package ru.otus.hw.repositories.impl;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе ORM для работы с книгами")
@DataJpaTest
@Import({ BookRepositoryORM.class, GenreRepositoryORM.class })
class BookRepositoryORMTest {

    @Autowired
    private BookRepositoryORM repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен загружать книгу по id")
    @Test
    void shouldReturnCorrectBookById() {
        Book expectedBook = em.find(Book.class, 1);
        var actualBook = repository.findById(expectedBook.getId());
        assertThat(actualBook).isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);
    }

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldReturnCorrectBooksList() {
        var actualBooks = repository.findAll();
        List<Book> expectedBooks = em.getEntityManager().createQuery("select b from Book b ", Book.class).getResultList();
        for (int i = 0; i < actualBooks.size(); i++) {
            assertThat(actualBooks.get(i))
                    .usingRecursiveComparison()
                    .isEqualTo(expectedBooks.get(i));
        }
    }

    @DisplayName("должен сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        var expectedBook = new Book(null, "BookTitle_10500", em.find(Author.class, 0), em.find(Genre.class, 0));
        var returnedBook = repository.save(expectedBook);
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(em.find(Book.class, returnedBook.getId()))
                .isEqualTo(returnedBook);
    }

    @DisplayName("должен сохранять измененную книгу")
    @Test
    void shouldSaveUpdatedBook() {
        var expectedBook = em.find(Book.class, 1L);
        var updateBook = new Book(expectedBook.getId(), "T2", expectedBook.getAuthor(), expectedBook.getGenre());
        assertThat(expectedBook.getTitle())
                .isNotEqualTo(updateBook.getTitle());

        var returnedBook = repository.save(updateBook);
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(em.find(Book.class, returnedBook.getId()))
                .usingRecursiveComparison()
                .isEqualTo(returnedBook);
    }

    @DisplayName("должен удалять книгу по id ")
    @Test
    void shouldDeleteBook() {
        long before = repository.findAll().size();
        repository.deleteById(1L);
        long after = repository.findAll().size();
        assertThat(before).isGreaterThan(after);
    }

}