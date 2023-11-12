package ru.otus.hw.repositories.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.mappers.AuthorMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.repositories.AuthorRepository;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryJdbc implements AuthorRepository {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<Author> findAll() {
        return jdbc.getJdbcOperations()
                .query("select id, name from authors", new AuthorMapper());
    }

    @Override
    public Optional<Author> findById(long id) {
        try {
            return Optional.of(jdbc.queryForObject(
                    "select id, name from authors where id=:id",
                    Map.of("id", id), new AuthorMapper()));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Author save(Author genre) {
        if (genre.getId() == 0) {
            return insert(genre);
        }
        return update(genre);
    }

    @Override
    public long deleteById(long id) {
        return jdbc.update(
                "delete from authors where id=:id", Map.of("id", id));
    }

    private Author insert(Author author) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("name", author.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(
                "insert into authors (name) values (:name)", parameterSource, keyHolder, new String[]{"id"});
        author.setId(keyHolder.getKey().longValue());
        return author;
    }

    private Author update(Author author) {
        jdbc.update(
                "update authors g set g.name=:name where g.id=:id",
                Map.of("id", author.getId(), "name", author.getName()));
        return author;
    }

}
