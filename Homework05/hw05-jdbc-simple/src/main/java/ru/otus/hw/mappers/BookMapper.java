package ru.otus.hw.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

/**
 * @author s.melekhin
 * @since 17 окт. 2022 г.
 */
public class BookMapper  implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Book(rs.getLong("b_id"), rs.getString("b_title"),
                new Author(rs.getLong("a_id"), rs.getString("name")),
                new Genre(rs.getLong("g_id"), rs.getString("g_name"))
        );
    }
}
