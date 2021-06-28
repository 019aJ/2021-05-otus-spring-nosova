package ru.otus.libraryjdbcapp.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.libraryjdbcapp.exceptions.LibraryAppException;
import ru.otus.libraryjdbcapp.models.Author;
import ru.otus.libraryjdbcapp.models.Book;
import ru.otus.libraryjdbcapp.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookRepositoryJdbcImpl implements BookRepository {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookRepositoryJdbcImpl(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public List<Book> all() {
        return this.namedParameterJdbcOperations.query(
                "select b.id," +
                        " b.title," +
                        " a.id as authorId," +
                        " a.name as authorName," +
                        " a.surname as authorSurname," +
                        " g.id as genreId," +
                        " g.name as genreName" +
                        " from Books b " +
                        " left join Authors a on author_id = a.id " +
                        " left join Genres  g on genre_id = g.id", new BookRowMapper());
    }

    @Override
    public Book byId(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return this.namedParameterJdbcOperations.queryForObject(
                "select b.id," +
                        " b.title," +
                        " a.id as authorId," +
                        " a.name as authorName," +
                        " a.surname as authorSurname," +
                        " g.id as genreId," +
                        " g.name as genreName" +
                        " from Books b " +
                        " left join Authors a on author_id = a.id " +
                        " left join Genres  g on genre_id = g.id" +
                        " where b.id = :id", params, new BookRowMapper()
        );
    }

    @Override
    public long insert(Book book) throws LibraryAppException {
        if (book.getId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            namedParameters.addValue("title", book.getTitle());
            namedParameters.addValue("authorId", book.getAuthor().getId());
            namedParameters.addValue("genreId", getGenreId(book));

            namedParameterJdbcOperations.update("insert into Books (title, author_id, genre_id) values (:title, :authorId, :genreId)",
                    namedParameters, keyHolder);
            Number key = keyHolder.getKey();
            if (key == null) {
                throw new LibraryAppException("Книга не может быть сохранена");
            }
            return (long) key;
        } else {
            this.namedParameterJdbcOperations.update("insert into Books (id, title, author_id, genre_id) values (:id, :title, :authorId, :genreId)",
                    getParams(book));
            return book.getId();
        }
    }

    @Override
    public void update(Book book) {
        this.namedParameterJdbcOperations.update("update Books set title = :title, author_id = :authorId, genre_id = :genreId where id = :id",
                getParams(book));
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        this.namedParameterJdbcOperations.update(
                "delete from Books where id = :id", params
        );
    }

    private Map<String, ?> getParams(Book book) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", book.getId());
        map.put("title", book.getTitle());
        map.put("authorId", book.getAuthor().getId());
        map.put("genreId", getGenreId(book));
        return map;
    }

    private Long getGenreId(Book book) {
        return book.getGenre() != null ? book.getGenre().getId() : null;
    }

    public class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int i) throws SQLException {
            return new Book(rs.getLong("id"), rs.getString("title"),
                    new Author(rs.getLong("authorId"), rs.getString("authorSurname"), rs.getString("authorName")),
                    new Genre(rs.getLong("genreId"), rs.getString("genreName")));
        }
    }
}
