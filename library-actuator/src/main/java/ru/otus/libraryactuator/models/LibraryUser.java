package ru.otus.libraryactuator.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@ToString(exclude = "password")
@Entity
@Table(name = "library_users")
public class LibraryUser {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String roles;

    @JsonIgnore
    private String password;

    protected LibraryUser() {
    }

    public LibraryUser(String name, String password, String roles) {
        this.name = name;
        this.roles = roles;
        setPassword(password);
    }
}
