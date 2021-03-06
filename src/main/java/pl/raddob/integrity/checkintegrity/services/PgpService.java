package pl.raddob.integrity.checkintegrity.services;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.raddob.integrity.checkintegrity.models.ImportKeySerivceReturnType;
import pl.raddob.integrity.checkintegrity.models.Messages;
import pl.raddob.integrity.checkintegrity.models.VerifyFileResult;
import pl.raddob.integrity.checkintegrity.models.VerifySignatureServiceReturnType;
import pl.raddob.integrity.configuration.FilesLocationConfiguration;
import java.io.*;


@Service
public class PgpService {

    private final FilesLocationConfiguration configuration;
    private final ImportKeyService importKeyService;
    private final VerifySignatureService verifySignatureService;
    private final RemoveKeyService removeKeyService;

    public PgpService(FilesLocationConfiguration configuration, ImportKeyService importKeyService, VerifySignatureService verifySignatureService, RemoveKeyService removeKeyService) {
        this.configuration = configuration;
        this.importKeyService = importKeyService;
        this.verifySignatureService = verifySignatureService;
        this.removeKeyService = removeKeyService;
    }

    public VerifyFileResult verifyFile(String publicKey, String signature, String filename) throws IOException {

        Logger logger = LoggerFactory.getLogger(PgpService.class);

        try {
            createPublicKeyFile(publicKey, filename);
        } catch (IOException e) {
            e.printStackTrace();
            logger.warn(Messages.CREATE_PUBLIC_KEY_FILE_ERROR.getMessageText() + filename);
            return new VerifyFileResult(Messages.CREATE_PUBLIC_KEY_FILE_ERROR.getMessageText(), false);
        }
        try {
            createSignatureFile(signature, filename);
        } catch (IOException e) {
            return new VerifyFileResult(Messages.CREATE_SIGNATURE_FILE_ERROR.getMessageText(), false);
        }

        // Importowanie klucza publicznego do programu GnuPG
        ImportKeySerivceReturnType importKeyResult = importKeyService.importKey(filename);
        if (!importKeyResult.isStatus()) {
            deleteAllFiles(filename);
            return new VerifyFileResult(Messages.GPG_IMPORT_PUBLIC_KEY_ERROR.getMessageText(), false);
        }

        // Weryfikacja syngatury za pomocą zaimportowanego klucza publicznego
        VerifySignatureServiceReturnType verifyResult = verifySignatureService.verifySignature(filename);
        // Weryfikacja nie powiodła się
        // Usunięcie klucza publicznego ze zbioru kluczy GnuPG
        //Usuniecie plików zawierających klucz ubliczny oraz sygnaturę
        if (!verifyResult.isStatus() && !importKeyResult.getKeyId().isEmpty()) {
            removeKeyService.removeKey(importKeyResult.getKeyId());
            deleteAllFiles(filename);
            return new VerifyFileResult(verifyResult.getMessage(), false);
        } else if (!verifyResult.isStatus() && importKeyResult.getKeyId().isEmpty()) {
            deleteAllFiles(filename);
            return new VerifyFileResult(verifyResult.getMessage(), false);
        }
        // Weryfikacja powiodła się
        // Usunięcie klucza publicznego ze zbioru kluczy GnuPG
        if (!importKeyResult.getKeyId().isEmpty()) {
            removeKeyService.removeKey(importKeyResult.getKeyId());
        }
        //Usuniecie plików zawierających klucz ubliczny oraz sygnaturę
        deletePublicKeyFile(filename);
        deleteSignatureFile(filename);

        return new VerifyFileResult(verifyResult.getMessage(), true);
    }

    private void createPublicKeyFile(String publicKey, String filename) throws IOException {
        BufferedWriter keyWriter = new BufferedWriter(new FileWriter(configuration.getWorkingDirectory() + filename + "PublicKey.txt"));
        keyWriter.write(publicKey);
        keyWriter.close();
    }

    private void createSignatureFile(String signature, String filename) throws IOException {
        BufferedWriter signatureWriter = new BufferedWriter(new FileWriter(configuration.getWorkingDirectory() + filename + "Signature.txt"));
        signatureWriter.write(signature);
        signatureWriter.close();
    }

    private void deletePublicKeyFile(String filename) {
        Logger logger = LoggerFactory.getLogger(PgpService.class);
        String fullFileName = configuration.getWorkingDirectory() + filename + "PublicKey.txt";
        try {
            FileUtils.forceDelete(new File(fullFileName));
        } catch (IOException e) {
            logger.warn(Messages.REMOVE_FILE_ERROR.getMessageText() + fullFileName);
        }
    }

    private void deleteSignatureFile(String filename) {
        Logger logger = LoggerFactory.getLogger(PgpService.class);
        String fullFileName = configuration.getWorkingDirectory() + filename + "Signature.txt";
        try {
            FileUtils.forceDelete(new File(fullFileName));
        } catch (IOException e) {
            logger.warn(Messages.REMOVE_FILE_ERROR.getMessageText() + fullFileName);
        }
    }

    private void deleteFile(String filename) {
        Logger logger = LoggerFactory.getLogger(PgpService.class);
        String fullFileName = configuration.getWorkingDirectory() + filename;
        try {
            FileUtils.forceDelete(new File(fullFileName));
        } catch (IOException e) {
            logger.warn(Messages.REMOVE_FILE_ERROR.getMessageText() + fullFileName);
        }
    }


    private void deleteAllFiles(String filename) {
        deleteFile(filename);
        deleteSignatureFile(filename);
        deletePublicKeyFile(filename);
    }
}
