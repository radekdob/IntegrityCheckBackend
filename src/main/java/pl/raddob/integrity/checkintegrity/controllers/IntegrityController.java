package pl.raddob.integrity.checkintegrity.controllers;

import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.raddob.integrity.checkintegrity.models.CheckFileIntegrityRequest;
import pl.raddob.integrity.checkintegrity.models.CheckRequestAlgoritmType;
import pl.raddob.integrity.checkintegrity.models.CheckRequestFieldsReturnType;
import pl.raddob.integrity.checkintegrity.services.CheckRequestFieldsService;
import pl.raddob.integrity.checkintegrity.services.FileDownloaderService;
import pl.raddob.integrity.checkintegrity.services.HashService;
import pl.raddob.integrity.configuration.FilesLocationConfiguration;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;


@RestController
@RequestMapping("v1/integrity/")
public class IntegrityController {

    private final CheckRequestFieldsService checkRequestFieldsService;
    private final FileDownloaderService downloaderService;
    private final FilesLocationConfiguration configuration;
    private final HashService hashService;

    public IntegrityController(CheckRequestFieldsService checkRequestFieldsService, FileDownloaderService downloaderService, FilesLocationConfiguration configuration, HashService hashService) {
        this.checkRequestFieldsService = checkRequestFieldsService;
        this.downloaderService = downloaderService;
        this.configuration = configuration;
        this.hashService = hashService;
    }


    @GetMapping("1")
    public String test(Principal principal) {
        System.out.println(principal.toString());
        return "protected OKOKO";

    }

    @GetMapping("2")
    public Document test2(Principal principal) {
        // return "not protected ok";
        System.out.println(principal.toString());
        return new Document("name", "Radek");
    }

    @PostMapping("")
    public ResponseEntity<Document> checkFileIntegrity(@RequestBody CheckFileIntegrityRequest request) throws IOException, NoSuchAlgorithmException {


        CheckRequestFieldsReturnType checkRequestFieldsReturnType = checkRequestFieldsService.checkFields(request);
        if (!checkRequestFieldsReturnType.isResult()) {
            return new ResponseEntity(new Document("message", checkRequestFieldsReturnType.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }

        CheckRequestAlgoritmType checkRequestAlgoritmType = checkRequestFieldsService.checkAlgoritmType(request);
        if (!checkRequestAlgoritmType.isResult()) {
            return new ResponseEntity(new Document("message", checkRequestAlgoritmType.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        String fileName;
        try {
            fileName = downloaderService.downloadFile(request.getAssetAddress());
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(new Document("message", "Wystąpił błąd podczas przetwarzania pliku"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        System.out.println(fileName);

        switch (checkRequestAlgoritmType.getAlgoritm()) {
            case PGP:
                System.out.println("PGP");
                break;
            case MD5:
                System.out.println("MD5");
                Boolean result = hashService.md5Hash(fileName, request.getAlgoritmValue());
                System.out.println(result);
                break;
            case SHA256:
                System.out.println("SHA256");
                break;
            case SHA512:
                System.out.println("SHA512");
                break;
        }


        return new ResponseEntity(new Document("success", "success"), HttpStatus.OK);
    }
}
