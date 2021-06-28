package ru.otus.libraryjdbcapp.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.libraryjdbcapp.exceptions.LibraryAppException;
import ru.otus.libraryjdbcapp.models.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorRepositoryJdbcImpl implements AuthorRepository {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public AuthorRepositoryJdbcImpl(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public List<Author> all() {
        return namedParameterJdbcOperations.query("select id, name, surname from authors", new AuthorMapper());
    }

    @Override
    public Author byId(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "select id, name, surname from authors where id = :id", params, new AuthorMapper()
        );
    }

    @Override
    public long insert(Author author) throws LibraryAppException {
        if (author.getId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            namedParameters.addValue("name", author.getName());
            namedParameters.addValue("surname", author.getSurname());

            namedParameterJdbcOperations.update("insert into authors (name, surname) values (:name, :surname)",
                    namedParameters, keyHolder);
            Number key = keyHolder.getKey();
            if (key == null) {
                throw new LibraryAppException("Автор не может быть сохранен");
            }
            return (long) key;
        } else {
            namedParameterJdbcOperations.update("insert into authors (id, name, surname) values (:id, :name, :surname)",
                    Map.of("id", author.getId(), "name", author.getName(), "surname", author.getSurname()));
            return author.getId();
        }


    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from authors where id = :id", params
        );
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String surname = resultSet.getString("surname");
            return new Author(id, surname, name);
        }
    }
}
