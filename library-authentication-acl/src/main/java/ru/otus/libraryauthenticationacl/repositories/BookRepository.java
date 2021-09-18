package ru.otus.libraryauthenticationacl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.otus.libraryauthenticationacl.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select b from Book b join fetch b.author join fetch b.genre join fetch b.comments")
    @PostFilter("hasPermission(filterObject, 'READ')")
    List<Book> findAllEagerly();

    @PostFilter("hasPermission(filterObject, 'READ')")
    List<Book> findAll();

    @PreAuthorize("hasPermission(#id, 'ru.otus.libraryauthenticationacl.models.Book', 'READ')")
    Optional<Book> findById(Long id);

    @PreAuthorize("#book.id == null || hasPermission(#book, 'WRITE')")
    Book save(@Param("book") Book book);

    @Override
    @PreAuthorize("hasPermission(#id, 'ru.otus.libraryauthenticationacl.models.Book', 'DELETE')")
    void deleteById(Long id);
}
