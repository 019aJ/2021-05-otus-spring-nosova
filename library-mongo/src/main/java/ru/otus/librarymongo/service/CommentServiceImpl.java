package ru.otus.librarymongo.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.librarymongo.exceptions.InvalidInputException;
import ru.otus.librarymongo.exceptions.LibraryAppException;
import ru.otus.librarymongo.exceptions.NoSuchResultException;
import ru.otus.librarymongo.models.Comment;
import ru.otus.librarymongo.repositories.CommentRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Comment> all() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Comment byId(String id) throws LibraryAppException {
        return repository.findById(id).orElseThrow(() -> new NoSuchResultException("Данные для id " + id + "не найдены"));
    }

    @Override
    @Transactional
    public String insert(Comment comment) throws LibraryAppException {
        checkMandatory(comment);
        try {
            return repository.save(comment).getId();
        } catch (Exception e) {
            throw new LibraryAppException("Невозможно создать комментарий", e);
        }
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Comment comment) throws LibraryAppException {
        if (StringUtils.isEmpty(comment.getText())) {
            throw new InvalidInputException("Комментарий не может быть пустым");
        }
        repository.updateText(comment);
    }

    private void checkMandatory(Comment comment) throws LibraryAppException {
        if (StringUtils.isEmpty(comment.getText())) {
            throw new InvalidInputException("Комментарий не может быть пустым");
        }
        if (comment.getBookId() == null) {
            throw new InvalidInputException("Ссылка на книгу должена быть заполнена");
        }
    }
}
