package pl.raddob.integrity.checkintegrity.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.raddob.integrity.configuration.FilesLocationConfiguration;

import java.io.File;
import java.io.IOException;

@SpringBootTest
public class HashServiceTest {

    @Autowired
    private FilesLocationConfiguration configuration;



    @Nested
    class Md5Hash {

        @Test
        public void md5_fileDoesntExist_returnFalse() throws IOException {

            //given
            String nonExistingFileName = "non-mock.jpg";
            String hash = "someHash";
            var service = new HashService(configuration);

            //when
            Boolean foundExtension = service.md5Hash(nonExistingFileName, hash);

            //then
            Assertions.assertFalse(foundExtension);
        }

        @Test
        public void md5_fileExistsAndHashIsNotValid_returnFalse() throws IOException {

            //given
            String existingFileName = "mock.jpg";
            String hash = "7825E89001E3B230A6B1ABB16780F4C4+notValidHash";
            var service = new HashService(configuration);

            //when
            Boolean foundExtension = service.md5Hash(existingFileName, hash);

            //then
            Assertions.assertFalse(foundExtension);
        }


        @Test
        public void md5_fileExistsAndHashIsValid_returnTrue() throws IOException {

            //given
            String existingFileName ="mock.jpg";
            String hash = "7825e89001e3b230a6b1abb16780f4c4";
            var service = new HashService(configuration);

            //when
            boolean foundExtension = service.md5Hash(existingFileName, hash);

            //then
            Assertions.assertTrue(foundExtension);
        }
    }

}
