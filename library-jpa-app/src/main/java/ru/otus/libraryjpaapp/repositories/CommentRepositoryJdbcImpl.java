package ru.otus.libraryjpaapp.repositories;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.libraryjpaapp.models.Book;
import ru.otus.libraryjpaapp.models.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CommentRepositoryJdbcImpl implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Comment> all() {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c", Comment.class);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Comment> byId(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    @Transactional(readOnly = false)
    public Comment insert(Comment comment) {
        Book book = em.getReference(Book.class, comment.getBook().getId());
        comment.setBook(book);
        if (comment.getId() == null) {
            em.persist(comment);
        } else {
            return em.merge(comment);
        }
        return comment;
    }

    @Override
    @Transactional(readOnly = false)
    public void update(Comment comment) {
        em.merge(comment);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Comment c where c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
