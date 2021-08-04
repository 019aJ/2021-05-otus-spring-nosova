package ru.otus.libraryjpaapp.repositories;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.libraryjpaapp.models.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AuthorRepositoryJdbcImpl implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Author> all() {
        TypedQuery<Author> query = em.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }

    @Override
    public Optional<Author> byId(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public Author insert(Author author) {
        if (author.getId() == null) {
            em.persist(author);
        } else {
            return em.merge(author);
        }
        return author;
    }

    @Override
    public void deleteById(long id) {
        byId(id).ifPresent(em::remove);
    }
}
