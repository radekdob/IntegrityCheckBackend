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


    public boolean removeKey(String keyId) {
        String directory = this.configuration.getWorkingDirectory();
        boolean isWindows = this.configuration.isWindows();
        ProcessBuilder builder = new ProcessBuilder();
        builder.directory(new File(directory));

        if (isWindows) {
            builder.command("cmd.exe", "/c", "gpg --delete-key " + keyId);
        } else {
            builder.command("sh", "-c", "gpg --batch --yes --delete-key " + keyId);
        }

        System.out.println(builder.command());
        try {
            Process process = builder.start();

            StreamGobbler inputStreamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
            Executors.newSingleThreadExecutor().submit(inputStreamGobbler);

            StreamGobbler errorStreamGobbler = new StreamGobbler(process.getErrorStream(), System.err::println);
            Executors.newSingleThreadExecutor().submit(errorStreamGobbler);

            int exitCode2 = process.waitFor();
            //assert exitCode2 == 0;
            System.out.println("\n RemoveKey : Exited with error code : " + exitCode2);
            return exitCode2 == 0 ? true : false;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
