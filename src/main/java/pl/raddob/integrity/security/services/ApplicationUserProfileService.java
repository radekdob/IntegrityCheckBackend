package pl.raddob.integrity.security.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.raddob.integrity.security.repositories.ApplicationUserRepository;
import pl.raddob.integrity.security.model.ApplicationUser;

@Service
public class ApplicationUserProfileService {

    private final ApplicationUserRepository userRepository;

    public ApplicationUserProfileService(ApplicationUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ApplicationUser getUserProfile(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("Username %s not found", username))
        );
    }
}
