package ru.otus.libraryjpaapp.repositories;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.libraryjpaapp.models.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class BookRepositoryJdbcImpl implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Book> all() {
        TypedQuery<Book> query = em.createQuery("select b from Book b join fetch b.author join fetch b.genre", Book.class);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> byId(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    @Transactional(readOnly = false)
    public Book insert(Book book) {
        if (book.getId() == null) {
            em.persist(book);
        } else {
            return em.merge(book);
        }
        return book;
    }

    @Override
    @Transactional(readOnly = false)
    public void update(Book book) {
        em.merge(book);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
