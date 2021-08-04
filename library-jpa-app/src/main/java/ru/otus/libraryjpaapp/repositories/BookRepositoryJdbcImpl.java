package ru.otus.libraryjpaapp.repositories;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.libraryjpaapp.models.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class BookRepositoryJdbcImpl implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Book> all() {
        TypedQuery<Book> query = em.createQuery("select b from Book b join fetch b.author join fetch b.genre", Book.class);
        return query.getResultList();
    }

    @Override
    public List<Book> allEagerly() {
        TypedQuery<Book> query = em.createQuery("select b from Book b join fetch b.author join fetch b.genre join fetch b.comments", Book.class);
        return query.getResultList();
    }

    @Override
    public Optional<Book> byId(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public Optional<Book> byIdEagerly(long id) {
        Optional<Book> book = byId(id);
        book.ifPresent(x ->
                x.getComments().size());
        return book;
    }

    @Override
    public Book insert(Book book) {
        if (book.getId() == null) {
            em.persist(book);
        } else {
            return em.merge(book);
        }
        return book;
    }

    @Override
    public void update(Book book) {
        em.merge(book);
    }

    @Override
    public void deleteById(long id) {
        byId(id).ifPresent(em::remove);
    }
}
