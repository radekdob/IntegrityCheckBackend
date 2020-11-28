package pl.raddob.integrity.checkintegrity.services;

import org.springframework.stereotype.Service;
import pl.raddob.integrity.checkintegrity.interfaces.FileExtensionsRepository;
import pl.raddob.integrity.checkintegrity.models.FileExtensions;

import java.util.List;
import java.util.Optional;

@Service
public class FindExtensionFromFileNameService {

    private final FileExtensionsRepository fileExtensionsRepository;


    public FindExtensionFromFileNameService(FileExtensionsRepository fileExtensionsRepository) {
        this.fileExtensionsRepository = fileExtensionsRepository;
    }

    public String findExtension(String addressUrl) {

        //Pobranie obiektu zawierającego dodane do bazdy danych formaty plików
        FileExtensions extensionsFromDb = fileExtensionsRepository.findAll().get(0);

        //Sprawdzenie czy ciąg znaków adresu url zawiera element tablicy obiektu extensionsFromDb
        //Jeśli taki obiekt się znajdzie to będzie to znalezione rozszerzenie pliku
        Optional<String> foundExtension = extensionsFromDb.getFileExtensions()
                .stream()
                .filter(extensions -> addressUrl.contains(extensions)).findFirst();
        return foundExtension
                .orElse(""); //Jeśli żaden obiekt nie pasował zwracany jest pusty String
    }


}



