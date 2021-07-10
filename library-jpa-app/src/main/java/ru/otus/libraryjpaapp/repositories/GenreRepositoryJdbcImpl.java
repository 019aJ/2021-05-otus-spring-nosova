package ru.otus.libraryjpaapp.repositories;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.libraryjpaapp.models.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class GenreRepositoryJdbcImpl implements GenreRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
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
    @Transactional
    public Genre insert(Genre genre) {
        if (genre.getId() == null) {
            em.persist(genre);
        } else {
            return em.merge(genre);
        }
        return genre;
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        byId(id).ifPresent(em::remove);
    }
}
