package ru.otus.libraryauthentication.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.otus.libraryauthentication.models.LibraryUser;
import ru.otus.libraryauthentication.repositories.LibraryUserRepository;

@Component
@AllArgsConstructor
public class SpringDataJpaUserDetailsService implements UserDetailsService {

    private final LibraryUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        LibraryUser user = repository.findByName(name);
        return new User(user.getName(), LibraryUser.PASSWORD_ENCODER.encode(user.getPassword()),
                AuthorityUtils.createAuthorityList("ADMIN"));
    }

}