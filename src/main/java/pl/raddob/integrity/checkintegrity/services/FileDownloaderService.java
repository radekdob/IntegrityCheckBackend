package pl.raddob.integrity.checkintegrity.services;

import com.google.common.base.Strings;
import org.apache.commons.io.FileUtils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import pl.raddob.integrity.configuration.FilesLocationConfiguration;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
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

        // Nawiązanie połączenia w celu zdobycia rozszerzenia pliku
        URL url = new URL(addressUrl);
        URLConnection con = url.openConnection();

        // Próba zdobycia rozszerzenia pliku z nagłówka adresu url
        String fileNameHeader = con.getHeaderField("Content-Disposition");
        // Próba zdobycia rozszerzenia pliku z ciągu znaków adresu url
        String fileExtensionFromUrlString = FilenameUtils.getExtension(addressUrl);

        UUID uuid;
        String fileNameSuffix;
        if (fileNameHeader != null && fileNameHeader.contains("filename=")) {
            fileNameSuffix = fileNameHeader.substring(fileNameHeader.indexOf("filename=") + 9);
        } else if (!Strings.isNullOrEmpty(fileExtensionFromUrlString) && !fileExtensionFromUrlString.isBlank()) {
            fileNameSuffix = FilenameUtils.getBaseName(addressUrl) + "." + fileExtensionFromUrlString;
        } else {
            // Proba zdobycia rozszerzenia pliku z adresu url lub nagłówka nie powiodła się
            // Dla adresu typu https://onet.pl/samplefile.exe/download powyższe metody nie zadziałają
            // aby zdobyć rozszerzenie z powyższego adresu wykorzystać trzeba poniższy serwis
            String foundExtension = findExtensionFromFileNameService.findExtension(addressUrl);
            uuid = UUID.randomUUID();
            if (!foundExtension.isEmpty()) {
                fileNameSuffix = uuid.toString() + foundExtension; //nadanie losowej nazwy plikowi wraz ze znalezionym w serwisie rozszerzeniem
            } else {
                fileNameSuffix = uuid.toString(); //nadanie losowej nazwy plikowi, bez rozszerzenia - nie zostało znalezione w bazie
            }
        }


        FileUtils.copyURLToFile(
                url,
                new File(configuration.getWorkingDirectory() + fileNameSuffix),
                configuration.getConnectionTimeout(),
                configuration.getReadTimeout());

        return fileNameSuffix;
    }



    public URL getLocalLinkToFile(String filename) throws MalformedURLException {
        var file = new File(this.configuration.getWorkingDirectory() + filename);
        return file.toURI().toURL();
    }

}
