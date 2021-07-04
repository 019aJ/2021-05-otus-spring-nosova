package ru.otus.libraryjpaapp.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.otus.libraryjpaapp.exceptions.InvalidInputException;
import ru.otus.libraryjpaapp.exceptions.LibraryAppException;
import ru.otus.libraryjpaapp.exceptions.NoSuchResultException;
import ru.otus.libraryjpaapp.models.Book;
import ru.otus.libraryjpaapp.models.Comment;
import ru.otus.libraryjpaapp.repositories.CommentRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    public long insert(Map<String, String> commentFields) throws LibraryAppException {
        return insert(Mapper.get(commentFields));
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

    @Override
    public void update(Long id, Map<String, String> commentFields) throws LibraryAppException {
        Comment comment = Mapper.get(commentFields);
        comment.setId(id);
        update(comment);
    }

    @Override
    public List<String> fieldsForInput() {
        List<String> fields = new ArrayList<>();
        Collections.addAll(fields, "text", "bookId");
        return fields;
    }

    @Override
    public List<String> fieldsForUpdate() {
        List<String> fields = new ArrayList<>();
        Collections.addAll(fields, "text", "bookId");
        return fields;
    }

    private void checkMandatory(Comment comment) throws LibraryAppException {
        if (StringUtils.isEmpty(comment.getText())) {
            throw new InvalidInputException("Комментарий не может быть пустым");
        }
        if (comment.getBook() == null || comment.getBook().getId() == null) {
            throw new InvalidInputException("Ссылка на книгу должена быть заполнена");
        }
    }


    private static class Mapper {
        public static Comment get(Map<String, String> commentFields) throws LibraryAppException {
            String text = commentFields.get("text");
            Long bookId;
            try {
                bookId = Long.valueOf(commentFields.get("bookId"));
            } catch (Exception e) {
                throw new InvalidInputException("Id книги должно быть числом");
            }
            return new Comment(text, new Book(bookId, null, null, null, null));
        }
    }
}
