package pl.raddob.integrity.checkintegrity.services;

import com.google.common.base.Enums;
import org.springframework.stereotype.Service;
import pl.raddob.integrity.checkintegrity.models.Messages;
import pl.raddob.integrity.checkintegrity.models.VerifySignatureServiceReturnType;
import pl.raddob.integrity.configuration.FilesLocationConfiguration;
import pl.raddob.integrity.configuration.StreamGobbler;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;

import static pl.raddob.integrity.checkintegrity.models.Messages.VERIFY_SYGNATURE_WRONG_PUBLIC_KEY;

@Service
public class VerifySignatureService {

    private final FilesLocationConfiguration configuration;

    public VerifySignatureService(FilesLocationConfiguration configuration) {
        this.configuration = configuration;
    }


    public VerifySignatureServiceReturnType verifySignature(String fileName) {
        String directory = this.configuration.getWorkingDirectory();
       // String fullFileName = this.configuration.getWorkingDirectory() + fileName;
        String signatureName = fileName + "Signature.txt";

        ProcessBuilder builder = new ProcessBuilder();
        builder.directory(new File(directory));

        boolean isWindows = this.configuration.isWindows();
        if (isWindows) {
            builder.command("cmd.exe", "/c", "ping -n 3 google.com");
        } else {
            builder.command("sh", "-c", "gpg --verify " + signatureName + " " + fileName);
        }
        try {
            Process process = builder.start();

            StreamGobbler inputStreamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
            Executors.newSingleThreadExecutor().submit(inputStreamGobbler);

            StreamGobbler errorStreamGobbler = new StreamGobbler(process.getErrorStream(), System.err::println);
            Executors.newSingleThreadExecutor().submit(errorStreamGobbler);

            int exitCode = process.waitFor();
            System.out.println("\n Verify : Exited with error code : " + exitCode);

            VerifySignatureServiceReturnType returnType;
            switch (exitCode) {
                case 0:
                    returnType = new VerifySignatureServiceReturnType(Messages.VERIFY_SYGNATURE_SUCCESS.getMessageText(), true);
                    break;
                case 2:
                    returnType = new VerifySignatureServiceReturnType(VERIFY_SYGNATURE_WRONG_PUBLIC_KEY.getMessageText(), false);
                    break;
                default:
                    returnType = new VerifySignatureServiceReturnType(Messages.VERIFY_SYGNATURE_ERROR_OCCURED.getMessageText()+  "błąd numer"  + exitCode, false);
                    break;
            }
            return returnType;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return new VerifySignatureServiceReturnType(Messages.VERIFY_SYGNATURE_ERROR_OCCURED.getMessageText(), false);
        }


    }
}
