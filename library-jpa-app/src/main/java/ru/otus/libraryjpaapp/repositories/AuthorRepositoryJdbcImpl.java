package ru.otus.libraryjpaapp.repositories;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.libraryjpaapp.models.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AuthorRepositoryJdbcImpl implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Author> all() {
        TypedQuery<Author> query = em.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Author> byId(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    @Transactional(readOnly = false)
    public Author insert(Author author) {
        if (author.getId() == null) {
            em.persist(author);
        } else {
            return em.merge(author);
        }
        return author;
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Author a where a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
