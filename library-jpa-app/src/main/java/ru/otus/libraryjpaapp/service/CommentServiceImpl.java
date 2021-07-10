package ru.otus.libraryjpaapp.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.otus.libraryjpaapp.exceptions.InvalidInputException;
import ru.otus.libraryjpaapp.exceptions.LibraryAppException;
import ru.otus.libraryjpaapp.exceptions.NoSuchResultException;
import ru.otus.libraryjpaapp.models.Comment;
import ru.otus.libraryjpaapp.repositories.CommentRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repository;

    @Override
    public List<Comment> all() {
        return repository.all();
    }

    @Override
    public Comment byId(long id) throws LibraryAppException {
        return repository.byId(id).orElseThrow(() -> new NoSuchResultException("Данные для id " + id + "не найдены"));
    }

    @Override
    public long insert(Comment comment) throws LibraryAppException {
        checkMandatory(comment);
        try {
            return repository.insert(comment).getId();
        } catch (Exception e) {
            throw new LibraryAppException("Невозможно создать комментарий", e);
        }
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @Override
    public void update(Comment comment) throws LibraryAppException {
        checkMandatory(comment);
        repository.update(comment);
    }

    private void checkMandatory(Comment comment) throws LibraryAppException {
        if (StringUtils.isEmpty(comment.getText())) {
            throw new InvalidInputException("Комментарий не может быть пустым");
        }
        if (comment.getBook() == null || comment.getBook().getId() == null) {
            throw new InvalidInputException("Ссылка на книгу должена быть заполнена");
        }
    }
}
