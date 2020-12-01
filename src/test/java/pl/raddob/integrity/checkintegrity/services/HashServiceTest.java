package pl.raddob.integrity.checkintegrity.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.raddob.integrity.checkintegrity.models.Messages;
import pl.raddob.integrity.checkintegrity.models.VerifyFileResult;
import pl.raddob.integrity.configuration.FilesLocationConfiguration;

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
            VerifyFileResult result = service.md5Hash(nonExistingFileName, hash);

            //then
            Assertions.assertAll(
                    () -> Assertions.assertEquals(Messages.PATH_TO_FILE_NOT_FOUND.getMessageText(), result
                            .getMessage()),
                    () -> Assertions.assertFalse(result.isResult())
            );
        }

        @Test
        public void md5_fileExistsAndHashIsNotValid_returnFalse() throws IOException {

            //given
            String existingFileName = "mock.jpg";
            String hash = "7825E89001E3B230A6B1ABB16780F4C4+notValidHash";
            var service = new HashService(configuration);

            //when
            VerifyFileResult result = service.md5Hash(existingFileName, hash);

            //then
            Assertions.assertAll(
                    () -> Assertions.assertEquals(Messages.MD5_IS_NOT_CORRECT.getMessageText(), result
                            .getMessage()),
                    () -> Assertions.assertFalse(result.isResult())
            );
        }


        @Test
        public void md5_fileExistsAndHashIsValid_returnTrue() throws IOException {

            //given
            String existingFileName = "mock.jpg";
            String hash = "7825e89001e3b230a6b1abb16780f4c4";
            var service = new HashService(configuration);

            //when
            VerifyFileResult result = service.md5Hash(existingFileName, hash);

            //then
            Assertions.assertAll(
                    () -> Assertions.assertEquals(Messages.MD5_IS_CORRECT.getMessageText(), result
                            .getMessage()),
                    () -> Assertions.assertTrue(result.isResult())
            );
        }
    }

    @Nested
    class Sha256Hash {

        @Test
        public void sha256_fileDoesntExist_returnFalse() throws IOException {

            //given
            String nonExistingFileName = "non-mock.jpg";
            String hash = "someHash";
            var service = new HashService(configuration);

            //when
            VerifyFileResult result = service.sha256Hash(nonExistingFileName, hash);

            //then
            Assertions.assertAll(
                    () -> Assertions.assertEquals(Messages.PATH_TO_FILE_NOT_FOUND.getMessageText(), result
                            .getMessage()),
                    () -> Assertions.assertFalse(result.isResult())
            );
        }

        @Test
        public void sha256_fileExistsAndHashIsNotValid_returnFalse() throws IOException {

            //given
            String existingFileName = "mock.jpg";
            String hash = "7825E89001E3B230A6B1ABB16780F4C4+notValidHash";
            var service = new HashService(configuration);

            //when
            VerifyFileResult result = service.sha256Hash(existingFileName, hash);

            //then
            Assertions.assertAll(
                    () -> Assertions.assertEquals(Messages.SHA256_IS_NOT_CORRECT.getMessageText(), result
                            .getMessage()),
                    () -> Assertions.assertFalse(result.isResult())
            );
        }


        @Test
        public void sha256_fileExistsAndHashIsValid_returnTrue() throws IOException {

            //given
            String existingFileName = "mock.jpg";
            String hash = "b1f561b432f7e828451916a61471c50e314b3c9b38530cc4ae7d9eaba520f41f";
            var service = new HashService(configuration);

            //when
            VerifyFileResult result = service.sha256Hash(existingFileName, hash);

            //then
            Assertions.assertAll(
                    () -> Assertions.assertEquals(Messages.SHA256_IS_CORRECT.getMessageText(), result
                            .getMessage()),
                    () -> Assertions.assertTrue(result.isResult())
            );
        }
    }

    @Nested
    class Sha512Hash {

        @Test
        public void sha512_fileDoesntExist_returnFalse() throws IOException {

            //given
            String nonExistingFileName = "non-mock.jpg";
            String hash = "someHash";
            var service = new HashService(configuration);

            //when
            VerifyFileResult result = service.sha512Hash(nonExistingFileName, hash);

            //then
            Assertions.assertAll(
                    () -> Assertions.assertEquals(Messages.PATH_TO_FILE_NOT_FOUND.getMessageText(), result
                            .getMessage()),
                    () -> Assertions.assertFalse(result.isResult())
            );
        }

        @Test
        public void sha512_fileExistsAndHashIsNotValid_returnFalse() throws IOException {

            //given
            String existingFileName = "mock.jpg";
            String hash = "7825E89001E3B230A6B1ABB16780F4C4+notValidHash";
            var service = new HashService(configuration);

            //when
            VerifyFileResult result = service.sha512Hash(existingFileName, hash);

            //then
            Assertions.assertAll(
                    () -> Assertions.assertEquals(Messages.SHA512_IS_NOT_CORRECT.getMessageText(), result
                            .getMessage()),
                    () -> Assertions.assertFalse(result.isResult())
            );
        }


        @Test
        public void sha512_fileExistsAndHashIsValid_returnTrue() throws IOException {

            //given
            String existingFileName = "mock.jpg";
            String hash = "a2101c7a25c592d32ea7db0508806d08b717cc8bde308dcba9b7e0fe5a9f78f6bcf3d2cf1682997144daa087e5ed4855b443b1f8bd1862fc3c19ba41c1ac0cb7";
            var service = new HashService(configuration);

            //when
            VerifyFileResult result = service.sha512Hash(existingFileName, hash);

            //then
            Assertions.assertAll(
                    () -> Assertions.assertEquals(Messages.SHA512_IS_CORRECT.getMessageText(), result
                            .getMessage()),
                    () -> Assertions.assertTrue(result.isResult())
            );
        }
    }
}
