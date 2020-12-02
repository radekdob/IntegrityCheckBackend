package pl.raddob.integrity.checkintegrity.services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.raddob.integrity.checkintegrity.models.Messages;
import pl.raddob.integrity.checkintegrity.models.VerifySignatureServiceReturnType;
import pl.raddob.integrity.configuration.FilesLocationConfiguration;
import java.io.File;
import java.io.IOException;


import static pl.raddob.integrity.checkintegrity.models.Messages.VERIFY_SYGNATURE_WRONG_PUBLIC_KEY;

@Service
public class VerifySignatureService {

    private final FilesLocationConfiguration configuration;

    public VerifySignatureService(FilesLocationConfiguration configuration) {
        this.configuration = configuration;
    }


    public VerifySignatureServiceReturnType verifySignature(String fileName) {

        Logger logger = LoggerFactory.getLogger(VerifySignatureService.class);

        String directory = this.configuration.getWorkingDirectory();
        String signatureName = fileName + "Signature.txt";

        ProcessBuilder builder = new ProcessBuilder();
        builder.directory(new File(directory));

        boolean isWindows = this.configuration.isWindows();
        if (isWindows) {
            builder.command("cmd.exe", "/c", "gpg --verify " + signatureName + " " + fileName);
        } else {
            builder.command("sh", "-c", "gpg --verify " + signatureName + " " + fileName);
        }
        try {
            Process process = builder.start();
            int exitCode = process.waitFor();
            process.destroy();

            VerifySignatureServiceReturnType returnType;
            switch (exitCode) {
                case 0:
                    returnType = new VerifySignatureServiceReturnType(Messages.VERIFY_SYGNATURE_SUCCESS.getMessageText(), true);
                    break;
                case 2: //GnuPG wraca exitCode == 2 jeśli klucz publiczny nie pasuje do sygnatury
                    returnType = new VerifySignatureServiceReturnType(VERIFY_SYGNATURE_WRONG_PUBLIC_KEY.getMessageText(), false);
                    break;
                default:
                    logger.warn(Messages.VERIFY_SYGNATURE_ERROR_OCCURED.getMessageText()+  "błąd numer"  + exitCode);
                    returnType = new VerifySignatureServiceReturnType(Messages.VERIFY_SYGNATURE_ERROR_OCCURED.getMessageText()+  "błąd numer"  + exitCode, false);
                    break;
            }
            return returnType;
        } catch (IOException | InterruptedException e) {
            return new VerifySignatureServiceReturnType(Messages.VERIFY_SYGNATURE_ERROR_OCCURED.getMessageText(), false);
        }


    }
}
