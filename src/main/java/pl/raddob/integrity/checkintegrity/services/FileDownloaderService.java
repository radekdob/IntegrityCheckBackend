package pl.raddob.integrity.checkintegrity.services;

import com.google.common.base.Strings;
import org.apache.commons.io.FileUtils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import pl.raddob.integrity.configuration.FilesLocationConfiguration;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;


@Service
public class FileDownloaderService {


    private final FilesLocationConfiguration configuration;
    private final FindExtensionFromFileNameService findExtensionFromFileNameService;

    public FileDownloaderService(FilesLocationConfiguration configuration, FindExtensionFromFileNameService findExtensionFromFileNameService) {
        this.configuration = configuration;
        this.findExtensionFromFileNameService = findExtensionFromFileNameService;
    }

    public String downloadFile(String addressUrl) throws IOException {

        URL url = new URL(addressUrl);
        URLConnection con = url.openConnection();

        String fileNameHeader = con.getHeaderField("Content-Disposition");
        String fileExtensionFromUrlString = FilenameUtils.getExtension(addressUrl);
        UUID uuid;

        String fileNameSuffix;
        if (fileNameHeader != null && fileNameHeader.contains("filename=")) {
            fileNameSuffix = fileNameHeader.substring(fileNameHeader.indexOf("filename=") + 9);
            System.out.println("weszlo z headera" + fileNameSuffix);
        } else if (!Strings.isNullOrEmpty(fileExtensionFromUrlString) && !fileExtensionFromUrlString.isBlank()) {
            fileNameSuffix = FilenameUtils.getBaseName(addressUrl) + "." + fileExtensionFromUrlString;
            System.out.println("weszlo z linku " + fileNameSuffix);
        } else {
            String foundExtension = findExtensionFromFileNameService.findExtension(addressUrl);
            uuid = UUID.randomUUID();
            if (!foundExtension.isEmpty()) {
                fileNameSuffix = uuid.toString() + foundExtension;
            } else {
                fileNameSuffix = uuid.toString();
            }
            System.out.println("else " + fileNameSuffix);
        }

        System.out.println("headery " + con.getHeaderFields());

        System.out.println("pobiera");
        FileUtils.copyURLToFile(
                url,
                new File(configuration.getWorkingDirectory() + fileNameSuffix),
                configuration.getConnectionTimeout(),
                configuration.getReadTimeout());

        System.out.println("pobralo");
        return fileNameSuffix;
    }

}
