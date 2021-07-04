package ru.otus.libraryjpaapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Comments")
public class Comment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "text")
    private String text;
    @ManyToOne(cascade =
            CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Book book;

    public Comment(String text, Book book) {
        this.text = text;
        this.book = book;
    }
}
