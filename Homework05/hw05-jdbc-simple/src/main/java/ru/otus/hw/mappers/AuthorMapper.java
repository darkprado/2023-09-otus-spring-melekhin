package ru.otus.hw.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ru.otus.hw.models.Author;

/**
 * @author s.melekhin
 * @since 17 окт. 2022 г.
 */
public class AuthorMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        return new Author(id, name);
    }

}
