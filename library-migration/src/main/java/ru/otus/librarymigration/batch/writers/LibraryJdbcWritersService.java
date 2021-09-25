package ru.otus.librarymigration.batch.writers;

import lombok.AllArgsConstructor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import ru.otus.librarymigration.model.relational.Author;
import ru.otus.librarymigration.model.relational.Book;
import ru.otus.librarymigration.model.relational.Comment;
import ru.otus.librarymigration.model.relational.Genre;

import javax.sql.DataSource;

@AllArgsConstructor
@Service
public class LibraryJdbcWritersService {

    private final DataSource dataSource;

    public JdbcBatchItemWriter<Author> authorWriter() {
        JdbcBatchItemOnlyNewWriter<Author> writer = new JdbcBatchItemOnlyNewWriter<>();
        writer.setCheckQuery("SELECT 1 FROM Authors WHERE name = :name AND surname = :surname");
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO Authors (name, surname) VALUES (:name, :surname)");
        writer.setDataSource(dataSource);
        return writer;
    }


    public JdbcBatchItemWriter<Genre> genreWriter() {
        JdbcBatchItemOnlyNewWriter<Genre> writer = new JdbcBatchItemOnlyNewWriter<>();
        writer.setCheckQuery("SELECT 1 FROM Genres WHERE name = :name");
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO Genres (name) VALUES (:name)");
        writer.setDataSource(dataSource);
        return writer;
    }

    public JdbcBatchItemWriter<Book> bookWriter() {
        JdbcBatchItemOnlyNewWriter<Book> writer = new JdbcBatchItemOnlyNewWriter<>();
        writer.setCheckQuery("SELECT 1 FROM Books join Authors a on a.id = author_id join Genres g on g.id = genre_id WHERE title = :title AND a.name = :author_name AND a.surname = :author_surname and g.name = :genre_name");
        writer.setItemSqlParameterSourceProvider(book -> {
            MapSqlParameterSource source = new MapSqlParameterSource();
            source.addValue("title", book.getTitle());
            source.addValue("author_name", book.getAuthor().getName());
            source.addValue("author_surname", book.getAuthor().getSurname());
            source.addValue("genre_name", book.getGenre().getName());
            return source;
        });
        writer.setSql("INSERT INTO Books (title, author_id, genre_id) " +
                "VALUES (:title, " +
                "       (SELECT id FROM Authors WHERE name = :author_name AND surname = :author_surname)," +
                "       (SELECT id FROM Genres WHERE  name = :genre_name ))");
        writer.setDataSource(dataSource);
        return writer;
    }

    public JdbcBatchItemWriter<Comment> commentWriter() {
        JdbcBatchItemOnlyNewWriter<Comment> writer = new JdbcBatchItemOnlyNewWriter<>();
        writer.setCheckQuery("SELECT 1 FROM Comments c join Books b on c.book_id = b.id join Authors a on a.id = author_id join Genres g on g.id = genre_id WHERE b.title = :title AND a.name = :author_name AND a.surname = :author_surname and g.name = :genre_name and c.text = :text ");
        writer.setItemSqlParameterSourceProvider(comment -> {
                    Book book = comment.getBook();
                    MapSqlParameterSource source = new MapSqlParameterSource();
                    source.addValue("title", book.getTitle());
                    source.addValue("author_name", book.getAuthor().getName());
                    source.addValue("author_surname", book.getAuthor().getSurname());
                    source.addValue("genre_name", book.getGenre().getName());
                    source.addValue("text", comment.getText());
                    return source;
                }
        );
        writer.setSql("INSERT INTO Comments (text, book_id) " +
                "VALUES (:text, " +
                "       (SELECT b.id FROM Books b join Authors a on a.id = author_id join Genres g on g.id = genre_id WHERE title = :title AND a.name = :author_name AND a.surname = :author_surname and g.name = :genre_name))");
        writer.setDataSource(dataSource);
        return writer;
    }
}
