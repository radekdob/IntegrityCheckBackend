package pl.raddob.integrity.checkintegrity.services;

import org.springframework.stereotype.Service;
import pl.raddob.integrity.configuration.FilesLocationConfiguration;
import pl.raddob.integrity.configuration.StreamGobbler;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;

@Service
public class RemoveKeyService {

    private final FilesLocationConfiguration configuration;


    public RemoveKeyService(FilesLocationConfiguration configuration) {
        this.configuration = configuration;
    }


    public void removeKey(String keyId) {
        String directory = this.configuration.getWorkingDirectory();

        ProcessBuilder builder = new ProcessBuilder();
        builder.directory(new File(directory));

        boolean isWindows = this.configuration.isWindows();
        if (isWindows) {
            builder.command("cmd.exe", "/c", "gpg --delete-key " + keyId);
        } else {
            builder.command("sh", "-c", "gpg --batch --yes --delete-key " + keyId);
        }

        try {
            Process process = builder.start();

            StreamGobbler inputStreamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
            Executors.newSingleThreadExecutor().submit(inputStreamGobbler);

            StreamGobbler errorStreamGobbler = new StreamGobbler(process.getErrorStream(), System.err::println);
            Executors.newSingleThreadExecutor().submit(errorStreamGobbler);

            int exitCode = process.waitFor();

            System.out.println("\n RemoveKey : Exited with error code : " + exitCode);
        } catch (IOException | InterruptedException e) {
            // logger nie udalo sie usunac klucza ze zbioru kluczy gpg
            System.out.println("Nie udalo sie usunac klucza");
        }
    }
}
