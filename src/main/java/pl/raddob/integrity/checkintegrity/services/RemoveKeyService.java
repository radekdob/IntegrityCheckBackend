package pl.raddob.integrity.checkintegrity.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.raddob.integrity.checkintegrity.models.Messages;
import pl.raddob.integrity.configuration.FilesLocationConfiguration;
import java.io.File;
import java.io.IOException;


@Service
public class RemoveKeyService {

    private final FilesLocationConfiguration configuration;


    public RemoveKeyService(FilesLocationConfiguration configuration) {
        this.configuration = configuration;
    }


    public void removeKey(String keyId) {
        String directory = this.configuration.getWorkingDirectory();
        Logger logger = LoggerFactory.getLogger(ImportKeyService.class);
        ProcessBuilder builder = new ProcessBuilder();
        builder.directory(new File(directory));

        boolean isWindows = this.configuration.isWindows();
        if (isWindows) {
            builder.command("cmd.exe", "/c", "gpg --batch --yes --delete-key " + keyId);
        } else {
            builder.command("sh", "-c", "gpg --batch --yes --delete-key " + keyId);
        }

        try {
            Process process = builder.start();
            int exitCode = process.waitFor();
            process.destroy();

        } catch (IOException | InterruptedException e) {
            logger.warn(Messages.REMOVE_KEY_ERROR.getMessageText());
        }
    }
}
