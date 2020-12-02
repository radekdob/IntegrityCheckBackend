package pl.raddob.integrity.security.services;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.raddob.integrity.security.repositories.ApplicationUserRepository;
import pl.raddob.integrity.security.model.ApplicationUser;

import java.util.Collections;

@Service
public class ApplicationUserDetailService  implements UserDetailsService {

    private final ApplicationUserRepository userRepository;

    public ApplicationUserDetailService(ApplicationUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ApplicationUser user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("Username %s not found", username))
                );

        return new User(user.getUsername(),user.getPassword(), Collections.emptyList());
    }
}
