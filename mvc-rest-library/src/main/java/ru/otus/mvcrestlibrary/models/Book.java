package ru.otus.mvcrestlibrary.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Books")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;

    @ManyToOne(cascade = {
            CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "fk_author"))
    private Author author;

    @ManyToOne(cascade = {
            CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "genre_id", foreignKey = @ForeignKey(name = "fk_genre"))
    private Genre genre;

    @OneToMany(cascade =
            CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "fk_book"))
    @Fetch(FetchMode.SUBSELECT)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private List<Comment> comments;

    public Book(String title, Author author, Genre genre) {
        this.genre = genre;
        this.author = author;
        this.title = title;
    }


}
