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
import ru.otus.hw.mappers.GenreMapper;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.GenreRepository;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryJdbc implements GenreRepository {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<Genre> findAll() {
        return jdbc.getJdbcOperations()
                .query("select g.id, g.name from genres g", new GenreMapper());
    }

    @Override
    public Optional<Genre> findById(long id) {
        try {
            return Optional.ofNullable(jdbc.queryForObject(
                    "select id, name from genres where id=:id",
                    Map.of("id", id), new GenreMapper()));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == 0) {
            return insert(genre);
        }
        return update(genre);
    }

    @Override
    public long deleteById(long id) {
        return jdbc.update(
                "delete from genres where id=:id", Map.of("id", id));
    }

    private Genre insert(Genre genre) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("name", genre.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(
                "insert into genres (name) values (:name)", parameterSource, keyHolder, new String[]{"id"});
        genre.setId(keyHolder.getKey().longValue());
        return genre;
    }

    private Genre update(Genre genre) {
        jdbc.update(
                "update genres g set g.name=:name where g.id=:id",
                Map.of("id", genre.getId(), "name", genre.getName()));
        return genre;
    }

}
