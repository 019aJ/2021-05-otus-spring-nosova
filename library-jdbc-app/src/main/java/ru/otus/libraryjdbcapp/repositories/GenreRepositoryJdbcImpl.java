package ru.otus.libraryjdbcapp.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.libraryjdbcapp.exceptions.LibraryAppException;
import ru.otus.libraryjdbcapp.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class GenreRepositoryJdbcImpl implements GenreRepository {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public GenreRepositoryJdbcImpl(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public List<Genre> all() {
        return namedParameterJdbcOperations.query("select id, name from genres", new GenreMapper());
    }

    @Override
    public Genre byId(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "select id, name from genres where id = :id", params, new GenreMapper()
        );
    }

    @Override
    public long insert(Genre genre) throws LibraryAppException {
        if (genre.getId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            namedParameters.addValue("name", genre.getName());
            namedParameterJdbcOperations.update("insert into genres (name) values (:name)",
                    namedParameters, keyHolder);
            Number key = keyHolder.getKey();
            if (key == null) {
                throw new LibraryAppException("Жанр не может быть сохранен");
            }
            return (long) key;
        } else {
            namedParameterJdbcOperations.update("insert into genres (id, name) values (:id, :name)",
                    Map.of("id", genre.getId(), "name", genre.getName()));
            return genre.getId();
        }
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from genres where id = :id", params
        );
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }
}
