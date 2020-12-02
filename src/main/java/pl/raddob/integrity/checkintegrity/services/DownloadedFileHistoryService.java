package pl.raddob.integrity.checkintegrity.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.raddob.integrity.security.repositories.ApplicationUserRepository;
import pl.raddob.integrity.security.model.ApplicationUser;
import pl.raddob.integrity.security.model.FileHistory;

import java.net.URL;
import java.util.Optional;

@Service
public class DownloadedFileHistoryService {

    private final ApplicationUserRepository userRepository;

    public DownloadedFileHistoryService(ApplicationUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void addAssetInfoToUser(String filename, URL assetLink, String username) throws UsernameNotFoundException {
        ApplicationUser user = getUser(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("Username %s not found", username))
        );
        var newFileHistory = new FileHistory(filename, assetLink.toString());
        user.getFilesHistory().add(newFileHistory);
        userRepository.save(user);
    }

    private Optional<ApplicationUser> getUser(String username) {
        return userRepository.findByUsername(username);
    }
}
