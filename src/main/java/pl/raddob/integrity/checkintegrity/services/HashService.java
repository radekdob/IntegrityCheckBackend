package pl.raddob.integrity.checkintegrity.services;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import pl.raddob.integrity.checkintegrity.models.Messages;
import pl.raddob.integrity.checkintegrity.models.VerifyFileResult;
import pl.raddob.integrity.configuration.FilesLocationConfiguration;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@Service
public class HashService {

    private final FilesLocationConfiguration configuration;

    public HashService(FilesLocationConfiguration configuration) {
        this.configuration = configuration;
    }

    public VerifyFileResult md5Hash(String fileName, String hashValue) throws IOException {

        var path = Paths.get(configuration.getWorkingDirectory() + fileName);
        if (Files.exists(path)) {
            File file = FileUtils.getFile(configuration.getWorkingDirectory(), fileName);
            byte[] bytesOfFile = FileUtils.readFileToByteArray(file);
            byte[] md5Hash = DigestUtils.md5(bytesOfFile);
            String stringHash = Hex.encodeHexString(md5Hash);


            return stringHash.equalsIgnoreCase(hashValue) ?
                    new VerifyFileResult(Messages.MD5_IS_CORRECT.getMessageText(), true) :
                    new VerifyFileResult(Messages.MD5_IS_NOT_CORRECT.getMessageText(), false);
        } else {
            return new VerifyFileResult(Messages.PATH_TO_FILE_NOT_FOUND.getMessageText(), false);
        }
    }

    public VerifyFileResult sha256Hash(String fileName, String hashValue) throws IOException {

        var path = Paths.get(configuration.getWorkingDirectory() + fileName);
        if (Files.exists(path)) {
            File file = FileUtils.getFile(configuration.getWorkingDirectory(), fileName);
            byte[] bytesOfFile = FileUtils.readFileToByteArray(file);
            byte[] sha256Hash = DigestUtils.sha256(bytesOfFile);
            String stringHash = Hex.encodeHexString(sha256Hash);


            return stringHash.equalsIgnoreCase(hashValue) ?
                    new VerifyFileResult(Messages.SHA256_IS_CORRECT.getMessageText(), true) :
                    new VerifyFileResult(Messages.SHA256_IS_NOT_CORRECT.getMessageText(), false);
        } else {
            return new VerifyFileResult(Messages.PATH_TO_FILE_NOT_FOUND.getMessageText(), false);
        }
    }

    public VerifyFileResult sha512Hash(String fileName, String hashValue) throws IOException {

        var path = Paths.get(configuration.getWorkingDirectory() + fileName);
        if (Files.exists(path)) {
            File file = FileUtils.getFile(configuration.getWorkingDirectory(), fileName);
            byte[] bytesOfFile = FileUtils.readFileToByteArray(file);
            byte[] sha512Hash = DigestUtils.sha512(bytesOfFile);
            String stringHash = Hex.encodeHexString(sha512Hash);


            return stringHash.equalsIgnoreCase(hashValue) ?
                    new VerifyFileResult(Messages.SHA512_IS_CORRECT.getMessageText(), true) :
                    new VerifyFileResult(Messages.SHA512_IS_NOT_CORRECT.getMessageText(), false);
        } else {
            return new VerifyFileResult(Messages.PATH_TO_FILE_NOT_FOUND.getMessageText(), false);
        }
    }


}
