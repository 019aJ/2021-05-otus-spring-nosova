package ru.otus.libraryauthenticationacl.repositories;

import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.libraryauthenticationacl.models.LibraryUser;

@RepositoryRestResource(exported = false)
public interface LibraryUserRepository extends Repository<LibraryUser, Long> {
    LibraryUser findByName(String name);
}
