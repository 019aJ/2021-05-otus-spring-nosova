package ru.otus.rxlibrary.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.rxlibrary.models.Author;
import ru.otus.rxlibrary.models.Book;
import ru.otus.rxlibrary.models.Genre;
import ru.otus.rxlibrary.repositories.BookRepository;


@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    @ChangeSet(order = "000", id = "dropDB", author = "onosova", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "onosova", runAlways = true)
    public void initBooks(BookRepository repository) {
        Author tolstoy = new Author("Lev", "Tolstoy");
        Author austen = new Author("Jane", "Austen");
        Author christie = new Author("Agatha", "Christie");
        Author king = new Author("Stephen", "King");

        Genre detective = new Genre("Detective");
        Genre novel = new Genre("Novel");
        Genre horror = new Genre("Horror");

        Book wap = new Book("War and Peace", tolstoy, novel);
        repository.save(wap).subscribe();

        Book pap = new Book("Pride and prejudice", austen, novel);
        repository.save(pap).subscribe();

        Book moe = new Book("Murder on the Orient Express", christie, detective);
        repository.save(moe).subscribe();
        Book it = new Book("It", king, horror);
        repository.save(it).subscribe();
    }


}
