package pl.raddob.integrity.checkintegrity.services;

import com.google.common.base.Strings;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import pl.raddob.integrity.checkintegrity.models.ImportKeySerivceReturnType;
import pl.raddob.integrity.configuration.FilesLocationConfiguration;
import pl.raddob.integrity.configuration.StreamGobbler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
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

        String directory = this.configuration.getWorkingDirectory();
        String fullFileName = directory + filename + "PublicKey.txt";

        ProcessBuilder builder = new ProcessBuilder();
        builder.directory(new File(directory));

        boolean isWindows = this.configuration.isWindows();
        if (isWindows) {
            builder.command("cmd.exe", "/c", "ping -n 3 google.com");
        } else {
            builder.command("sh", "-c", "gpg --import " + fullFileName);
        }

        try {
            Process process = builder.start();
/*
            StreamGobbler inputStreamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
            Executors.newSingleThreadExecutor().submit(inputStreamGobbler);

            StreamGobbler errorStreamGobbler = new StreamGobbler(process.getErrorStream(), System.err::println);
            Executors.newSingleThreadExecutor().submit(errorStreamGobbler);*/

            ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(2);
            Future<String> output = newFixedThreadPool.submit(() -> {
                return IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8);
            });
            Future<String> error = newFixedThreadPool.submit(() -> {
                return IOUtils.toString(process.getErrorStream(), StandardCharsets.UTF_8);
            });
            newFixedThreadPool.shutdown();


            String errorString = error.get();
            String successString = output.get();

            int exitCode = process.waitFor();
            process.destroy();
            if (exitCode == 0) {
                String addedKeyId;
                if (successString != null && !successString.isEmpty()) {
                    addedKeyId = successString.substring(9, 25);
                    System.out.println("SUCCESS" + successString);

                } else if (errorString != null && !errorString.isEmpty()) {
                    System.out.println("ERROR " + errorString);
                    addedKeyId = errorString.substring(9, 25);
                } else {
                    System.out.println("else" + errorString + successString);
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
