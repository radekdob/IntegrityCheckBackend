package pl.raddob.integrity.checkintegrity.services;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.raddob.integrity.checkintegrity.models.ImportKeySerivceReturnType;
import pl.raddob.integrity.checkintegrity.models.Messages;
import pl.raddob.integrity.configuration.FilesLocationConfiguration;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class ImportKeyService {

    private final FilesLocationConfiguration configuration;

    public ImportKeyService(FilesLocationConfiguration configuration) {
        this.configuration = configuration;
    }

    public ImportKeySerivceReturnType importKey(String filename) {

        Logger logger = LoggerFactory.getLogger(ImportKeyService.class);

        String directory = this.configuration.getWorkingDirectory();
        String fullFileName = directory + filename + "PublicKey.txt";

        //Tworzenie procesu, który uruchamia się w katalogu roboczym
        ProcessBuilder builder = new ProcessBuilder();
        builder.directory(new File(directory));

        boolean isWindows = this.configuration.isWindows();
        if (isWindows) {
            builder.command("cmd", "/c", "gpg --import " + fullFileName);
        } else {
            builder.command("sh", "-c", "gpg --import " + fullFileName);
        }

        try {
            Process process = builder.start();

            //Tworzenie wątków to zapisywania wyniku tekstowego działania sh/cmd
            ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(2);
            Future<String> output = newFixedThreadPool.submit(() -> {
                return IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8);
            });
            Future<String> error = newFixedThreadPool.submit(() -> {
                return IOUtils.toString(process.getErrorStream(), StandardCharsets.UTF_8);
            });
            newFixedThreadPool.shutdown();

            //wyniki tekstowe działania GnuPG
            String errorString = error.get();
            String successString = output.get();

            //Jeżeli proces został bez błędów to exitCode == 0
            int exitCode = process.waitFor();
            process.destroy();

            if (exitCode == 0) {

                // Wydobywanie z wyników tekstowych nowo dodanego (do zbioru kluczy GnuPG) klucza publicznego
                String addedKeyId;
                if (successString != null && !successString.isEmpty()) {
                    addedKeyId = successString.substring(9, 25);
                } else if (errorString != null && !errorString.isEmpty()) {
                    addedKeyId = errorString.substring(9, 25);
                } else {
                    //klucz zostal zaimportowany, lecz nie udalo sie uzyskac jego id
                    logger.warn(Messages.GPG_IMPORT_PUBLIC_KEY_ID_ERROR.getMessageText());
                    return new ImportKeySerivceReturnType("", true);
                }
                return new ImportKeySerivceReturnType(addedKeyId, true);

            } else {
                return new ImportKeySerivceReturnType("", false);
            }


        } catch (IOException | InterruptedException | ExecutionException e) {
            return new ImportKeySerivceReturnType("", false);
        }


    }
}
