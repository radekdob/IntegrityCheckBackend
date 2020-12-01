package pl.raddob.integrity.checkintegrity.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.raddob.integrity.configuration.FilesLocationConfiguration;

import java.net.MalformedURLException;
import java.net.URL;

@SpringBootTest
public class FileDownloaderServiceTest {

    @Autowired
    private  FilesLocationConfiguration configuration;
    @Autowired
    private  FileDownloaderService fileDownloaderService;




    @Nested
    class GetLocalLinkToFile {

        @Test
        public void getLocalLinkToFile_FileDoesNotExist_doesnNotreturnMalformedURLExceptionException() {

            //given
            String fileName = "not_existing_file.jpg";
            //then
            Assertions.assertDoesNotThrow(
                    () -> fileDownloaderService.getLocalLinkToFile(fileName));
        }
        @Test
        public void getLocalLinkToFile_FileExists_returnURL() throws MalformedURLException {

            //given
            String fileName = "mock.jpg";
            //when
            var result = fileDownloaderService.getLocalLinkToFile(fileName);
            //then
            Assertions.assertTrue(result instanceof URL);

        }


    }

}
