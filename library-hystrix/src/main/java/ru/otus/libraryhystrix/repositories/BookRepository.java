package ru.otus.libraryhystrix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.libraryhystrix.models.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select b from Book b join fetch b.author join fetch b.genre join fetch b.comments")
    List<Book> findAllEagerly();
}
