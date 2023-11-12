package ru.otus.hw.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ru.otus.hw.models.Genre;

/**
 * @author s.melekhin
 * @since 17 окт. 2022 г.
 */
public class GenreMapper implements RowMapper<Genre> {

    @Override
    public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        return new Genre(id, name);
    }

}
