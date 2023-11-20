package ru.otus.hw.repositories.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import ru.otus.hw.models.Author;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author s.melekhin
 * @since 10 нояб. 2023 г.
 */
@DisplayName("Репозиторий на основе ORM для работы с авторами")
@DataJpaTest
@Import(AuthorRepositoryORM.class)
class AuthorRepositoryORMTest {

    @Autowired
    private AuthorRepositoryORM repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Должен загружать автора по id")
    @Test
    void shouldReturnCorrectAuthorById() {
        var expectedAuthor = em.find(Author.class, 1);
        var actualAuthor = repository.findById(expectedAuthor.getId());
        assertThat(actualAuthor).isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthor);
    }

    @DisplayName("Должен загружать список всех авторов")
    @Test
    void shouldReturnCorrectAuthorsList() {
        var expectedAuthors = em.getEntityManager().createQuery("select a from Author a", Author.class).getResultList();
        var actualAuthors = repository.findAll();
        for (int i = 0; i < actualAuthors.size(); i++) {
            assertThat(actualAuthors.get(i))
                    .usingRecursiveComparison()
                    .isEqualTo(expectedAuthors.get(i));
        }
    }

    @DisplayName("Должен сохранять нового автора")
    @Test
    void shouldSaveNewAuthor() {
        var expectedAuthor = new Author(null, "Author_100500");
        var returnedAuthor = repository.save(expectedAuthor);
        assertThat(returnedAuthor).isNotNull()
                .matches(author -> author.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedAuthor);

        assertThat(em.find(Author.class, returnedAuthor.getId()))
                .isEqualTo(returnedAuthor);
    }

    @DisplayName("Должен сохранять измененного автора")
    @Test
    void shouldSaveUpdatedAuthor() {
        var expectedAuthor = new Author(1L, "Author_100500");

        assertThat(repository.findById(expectedAuthor.getId()))
                .isPresent()
                .get()
                .isNotEqualTo(expectedAuthor);

        var returnedAuthor = repository.save(expectedAuthor);
        assertThat(returnedAuthor).isNotNull()
                .matches(author -> author.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedAuthor);

        assertThat(em.find(Author.class, returnedAuthor.getId()))
                .usingRecursiveComparison()
                .isEqualTo(returnedAuthor);
    }

    @DisplayName("Должен удалять автора по id")
    @Test
    void shouldDeleteAuthor() {
        long before = repository.findAll().size();
        repository.deleteById(1L);
        long after = repository.findAll().size();
        assertThat(before).isGreaterThan(after);
    }

}