package ru.otus.emergencymonitoringsystem.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.otus.emergencymonitoringsystem.models.EMSUser;
import ru.otus.emergencymonitoringsystem.repositories.EMSUserRepository;

@Component
@AllArgsConstructor
public class EMSUserDetailsService implements UserDetailsService {

    private final EMSUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        EMSUser user = repository.findByName(name);
        String[] roles = user.getRoles().split(";");
        for (int i = 0; i < roles.length; i++) {
            roles[i] = "ROLE_" + roles[i];
        }
        return new User(user.getName(), user.getPassword(),
                AuthorityUtils.createAuthorityList(roles));
    }

}