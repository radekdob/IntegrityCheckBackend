package pl.raddob.integrity.checkintegrity.services;

import org.springframework.stereotype.Service;
import pl.raddob.integrity.checkintegrity.interfaces.FileExtensionsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FindExtensionFromFileNameService {

    private final FileExtensionsRepository fileExtensionsRepository;


    public FindExtensionFromFileNameService(FileExtensionsRepository fileExtensionsRepository) {
        this.fileExtensionsRepository = fileExtensionsRepository;
    }

    public String findExtension(String addressUrl) {

        var extensionsFromDb = fileExtensionsRepository.findAll().get(0);

        Optional<String> foundExtension = extensionsFromDb.getFileExtensions()
                .stream()
                .filter(extensions -> addressUrl.contains(extensions)).findFirst();
        return foundExtension.orElse("");
    }


}



