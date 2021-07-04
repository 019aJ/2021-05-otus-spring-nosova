package ru.otus.libraryjpaapp.repositories;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.libraryjpaapp.models.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class GenreRepositoryJdbcImpl implements GenreRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Genre> all() {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g", Genre.class);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Genre> byId(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    @Transactional(readOnly = false)
    public Genre insert(Genre genre) {
        if (genre.getId() == null) {
            em.persist(genre);
        } else {
            return em.merge(genre);
        }
        return genre;
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Genre g where g.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
