package ru.otus.libraryauthenticationacl.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.otus.libraryauthenticationacl.models.LibraryUser;
import ru.otus.libraryauthenticationacl.repositories.LibraryUserRepository;

@Component
@AllArgsConstructor
public class SpringDataJpaUserDetailsService implements UserDetailsService {

    private final LibraryUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        LibraryUser user = repository.findByName(name);
        String[] roles = user.getRoles().split(";");
        for (int i = 0; i < roles.length; i++) roles[i] = "ROLE_" + roles[i];
        return new User(user.getName(), LibraryUser.PASSWORD_ENCODER.encode(user.getPassword()),
                AuthorityUtils.createAuthorityList(roles));
    }

}