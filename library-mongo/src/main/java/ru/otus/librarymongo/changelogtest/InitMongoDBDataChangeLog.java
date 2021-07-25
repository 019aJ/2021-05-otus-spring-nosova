package ru.otus.librarymongo.changelogtest;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.librarymongo.models.Author;
import ru.otus.librarymongo.models.Book;
import ru.otus.librarymongo.models.Comment;
import ru.otus.librarymongo.models.Genre;
import ru.otus.librarymongo.repositories.BookRepository;
import ru.otus.librarymongo.repositories.CommentRepository;

import java.util.ArrayList;
import java.util.List;


@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    @ChangeSet(order = "000", id = "dropDB", author = "onosova", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "onosova", runAlways = true)
    public void initBooks(BookRepository repository, CommentRepository commentRepository) {
        Author tolstoy = new Author("Lev", "Tolstoy");
        Author austen = new Author("Jane", "Austen");
        Author christie = new Author("Agatha", "Christie");
        Author king = new Author("Stephen", "King");

        Genre detective = new Genre("Detective");
        Genre novel = new Genre("Novel");
        Genre horror = new Genre("Horror");

        Book wap = new Book("War and Peace", tolstoy, novel);
        repository.save(wap);

        Book pap = new Book("Pride and prejudice", austen, novel);
        repository.save(pap);

        Book moe = new Book("Murder on the Orient Express", christie, detective);
        moe = repository.save(moe);
        Book it = new Book("It", king, horror);
        repository.save(it);

        List<Comment> comments = new ArrayList<>();
        Comment comment1 = Comment.builder().text("Boring").bookId(moe.getId()).build();
        Comment comment2 = Comment.builder().text("So exiting").bookId(moe.getId()).build();
        Comment comment3 = Comment.builder().text("Best book ever").bookId(moe.getId()).build();
        Comment comment4 = Comment.builder().text("Who is the murder? Butler again?").bookId(moe.getId()).build();
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);
        commentRepository.save(comment4);
    }


}
