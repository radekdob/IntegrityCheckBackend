package pl.raddob.integrity.checkintegrity.services;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import pl.raddob.integrity.configuration.FilesLocationConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class HashService {

    private final FilesLocationConfiguration configuration;

    public HashService(FilesLocationConfiguration configuration) {
        this.configuration = configuration;
    }

    public boolean md5Hash(String fileName, String hashValue) throws  IOException {

        File file = FileUtils.getFile(configuration.getWorkingDirectory(), fileName);
        byte[] bytesOfFile =   FileUtils.readFileToByteArray(file);
        byte[] md5Hash = DigestUtils.md5(bytesOfFile);
        String stringHash = Hex.encodeHexString(md5Hash);
        return stringHash.equalsIgnoreCase(hashValue);
    }

}
