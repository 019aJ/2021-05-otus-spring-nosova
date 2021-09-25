package ru.otus.librarymigration.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.otus.librarymigration.model.nosql.MongoAuthor;
import ru.otus.librarymigration.model.nosql.MongoBook;
import ru.otus.librarymigration.model.nosql.MongoComment;
import ru.otus.librarymigration.model.nosql.MongoGenre;

import java.util.ArrayList;
import java.util.List;


@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    @ChangeSet(order = "000", id = "dropDB", author = "onosova", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "onosova", runAlways = true)
    public void initBooks(MongockTemplate template) {
        MongoAuthor tolstoy = new MongoAuthor("Lev", "Tolstoy");
        MongoAuthor austen = new MongoAuthor("Jane", "Austen");
        MongoAuthor christie = new MongoAuthor("Agatha", "Christie");
        MongoAuthor king = new MongoAuthor("Stephen", "King");

        MongoGenre detective = new MongoGenre("Detective");
        MongoGenre novel = new MongoGenre("Novel");
        MongoGenre horror = new MongoGenre("Horror");

        MongoBook wap = new MongoBook("War and Peace", tolstoy, novel);
        template.save(wap);

        MongoBook pap = new MongoBook("Pride and prejudice", austen, novel);
        template.save(pap);

        MongoBook moe = new MongoBook("Murder on the Orient Express", christie, detective);
        moe = template.save(moe);
        MongoBook it = new MongoBook("It", king, horror);
        template.save(it);

        List<MongoComment> comments = new ArrayList<>();
        MongoComment comment1 = MongoComment.builder().text("Boring").bookId(moe.getId()).build();
        MongoComment comment2 = MongoComment.builder().text("So exiting").bookId(moe.getId()).build();
        MongoComment comment3 = MongoComment.builder().text("Best book ever").bookId(moe.getId()).build();
        MongoComment comment4 = MongoComment.builder().text("Who is the murder? Butler again?").bookId(moe.getId()).build();
        template.save(comment1);
        template.save(comment2);
        template.save(comment3);
        template.save(comment4);
    }


}
