package pl.raddob.integrity.checkintegrity.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.raddob.integrity.checkintegrity.repositories.FileExtensionsRepository;


@SpringBootTest
public class FindExtensionFromFileNameServiceTest {

    @Autowired
    private FileExtensionsRepository fileExtensionsRepository;


    @Test
    public void findExtension_urlAddressWithExtension_returnsNotEmptyString() {

        //given
        var urlAddress = "https://sourceforge.net/projects/openofficeorg.mirror/files/4.1.8/binaries/pl/Apache_OpenOffice_4.1.8_MacOS_x86-64_langpack_pl.dmg/download";
        var service = new FindExtensionFromFileNameService(fileExtensionsRepository);

        //when
        var foundExtension = service.findExtension(urlAddress);

        //then
        Assertions.assertNotEquals("", foundExtension);
    }

    @Test
    public void findExtension_urlAddressWithNoExtension_returnsEmptyString() {

        //given
        var urlAddress = "https://sourceforge.net/projects/openofficeorg.mirror/files/4.1.8/binaries/pl/Apache_OpenOffice_4.1.8_MacOS_x86-64_langpack_pl/download";
        var service = new FindExtensionFromFileNameService(fileExtensionsRepository);

        //when
        var foundExtension = service.findExtension(urlAddress);

        //then
        Assertions.assertEquals("", foundExtension);
    }


}
