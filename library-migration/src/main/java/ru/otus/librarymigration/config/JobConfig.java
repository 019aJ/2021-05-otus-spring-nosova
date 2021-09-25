package ru.otus.librarymigration.config;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import ru.otus.librarymigration.batch.processors.AuthorProcessor;
import ru.otus.librarymigration.batch.processors.BookProcessor;
import ru.otus.librarymigration.batch.processors.GenreProcessor;
import ru.otus.librarymigration.batch.readers.LibraryMongoReaderService;
import ru.otus.librarymigration.batch.writers.LibraryJdbcWritersService;
import ru.otus.librarymigration.model.nosql.MongoAuthor;
import ru.otus.librarymigration.model.nosql.MongoBook;
import ru.otus.librarymigration.model.nosql.MongoComment;
import ru.otus.librarymigration.model.nosql.MongoGenre;
import ru.otus.librarymigration.model.relational.Author;
import ru.otus.librarymigration.model.relational.Book;
import ru.otus.librarymigration.model.relational.Comment;
import ru.otus.librarymigration.model.relational.Genre;


@Configuration
@AllArgsConstructor
public class JobConfig {
    private static final int CHUNK_SIZE = 5;
    private final Logger logger = LoggerFactory.getLogger("Batch");
    public static final String IMPORT_USER_JOB_NAME = "migrateLibraryFromMongoToH2Job";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final LibraryMongoReaderService mongoReaders;
    private final LibraryJdbcWritersService jdbcWriters;
    private final BookProcessor bookProcessor;
    private final AuthorProcessor authorProcessor;
    private final GenreProcessor genreProcessor;

    @StepScope
    @Bean
    public ItemReader<MongoBook> bookReader() {
        return mongoReaders.bookReader();
    }

    @StepScope
    @Bean
    public ItemReader<MongoAuthor> authorReader() {
        return mongoReaders.authorReader();
    }

    @StepScope
    @Bean
    public ItemReader<MongoGenre> genreReader() {
        return mongoReaders.genreReader();
    }

    @StepScope
    @Bean
    public ItemReader<MongoComment> commentReader() {
        return mongoReaders.commentReader();
    }

    @StepScope
    @Bean
    public JdbcBatchItemWriter<Book> bookWriter() {
        return jdbcWriters.bookWriter();
    }

    @StepScope
    @Bean
    public JdbcBatchItemWriter<Author> authorWriter() {
        return jdbcWriters.authorWriter();
    }

    @StepScope
    @Bean
    public JdbcBatchItemWriter<Genre> genreWriter() {
        return jdbcWriters.genreWriter();
    }

    @StepScope
    @Bean
    public JdbcBatchItemWriter<Comment> commentWriter() {
        return jdbcWriters.commentWriter();
    }

    @Bean
    public Job migrateLibraryFromMongoToH2Job(Step transformAuthorStep, Step transformGenreStep, Step transformBookStep, Step transformCommentStep) {
        return jobBuilderFactory.get(IMPORT_USER_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(transformAuthorStep)
                .next(transformGenreStep)
                .next(transformBookStep)
                .next(transformCommentStep)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(@NonNull JobExecution jobExecution) {
                        logger.info("Начало job");
                    }

                    @Override
                    public void afterJob(@NonNull JobExecution jobExecution) {
                        logger.info("Конец job");
                    }
                })
                .build();
    }


    @Bean
    public Step transformAuthorStep(ItemReader<MongoAuthor> reader,
                                    ItemProcessor<MongoAuthor, Author> itemProcessor, JdbcBatchItemWriter<Author> writer) {
        return stepBuilderFactory.get("authorStep")
                .<MongoAuthor, Author>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step transformGenreStep(ItemReader<MongoGenre> reader,
                                   ItemProcessor<MongoGenre, Genre> itemProcessor, JdbcBatchItemWriter<Genre> writer) {
        return stepBuilderFactory.get("genreStep")
                .<MongoGenre, Genre>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step transformBookStep(ItemReader<MongoBook> reader,
                                  ItemProcessor<MongoBook, Book> itemProcessor, JdbcBatchItemWriter<Book> writer) {
        return stepBuilderFactory.get("bookStep")
                .<MongoBook, Book>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step transformCommentStep(ItemReader<MongoComment> reader,
                                     ItemProcessor<MongoComment, Comment> itemProcessor, JdbcBatchItemWriter<Comment> writer) {
        return stepBuilderFactory.get("commentStep")
                .<MongoComment, Comment>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }
}
