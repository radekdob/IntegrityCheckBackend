package pl.raddob.integrity.checkintegrity.controllers;


import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.raddob.integrity.checkintegrity.models.*;
import pl.raddob.integrity.checkintegrity.services.CheckRequestFieldsService;
import pl.raddob.integrity.checkintegrity.services.FileDownloaderService;
import pl.raddob.integrity.checkintegrity.services.HashService;
import pl.raddob.integrity.checkintegrity.services.PgpService;
import pl.raddob.integrity.configuration.FilesLocationConfiguration;

import java.io.IOException;
import java.security.Principal;


@RestController
@RequestMapping("v1/integrity/")
public class IntegrityController {

    private final CheckRequestFieldsService checkRequestFieldsService;
    private final FileDownloaderService downloaderService;
    private final FilesLocationConfiguration configuration;
    private final HashService hashService;
    private final PgpService pgpService;

    public IntegrityController(CheckRequestFieldsService checkRequestFieldsService, FileDownloaderService downloaderService, FilesLocationConfiguration configuration, HashService hashService, PgpService pgpService) {
        this.checkRequestFieldsService = checkRequestFieldsService;
        this.downloaderService = downloaderService;
        this.configuration = configuration;
        this.hashService = hashService;
        this.pgpService = pgpService;
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
    public ResponseEntity<Document> checkFileIntegrity(@RequestBody CheckFileIntegrityRequest request) throws IOException{


        // 1. Sprawdzenie pól obiektu request
        CheckRequestFieldsReturnType checkRequestFieldsReturnType = checkRequestFieldsService.checkFields(request);
        if (!checkRequestFieldsReturnType.isResult()) {
            return new ResponseEntity(new Document("message", checkRequestFieldsReturnType.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        // 2. Sprawdzenie czy typ algorytmu otrzymany z requesta jest wspierany przez system (czy jest elementem enumu AlgoritmTypes
        CheckRequestAlgoritmType checkRequestAlgoritmType = checkRequestFieldsService.checkAlgoritmType(request);
        if (!checkRequestAlgoritmType.isResult()) {
            return new ResponseEntity(new Document("message", checkRequestAlgoritmType.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }

        // 3. Nawiązanie połączenia i pobranie pliku z adresu url zawartym w requescie.
        String fileName;
        try {
            fileName = downloaderService.downloadFile(request.getAssetAddress());
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(new Document("message", Messages.DOWNLOAD_FILE_ERROR_OCCURED.getMessageText()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // 4. W zależności od algorytmu podanym w requescie wykonanie sprawdzenia integralnośći pobranego pliku.
        VerifyFileResult verifyResult;
        switch (checkRequestAlgoritmType.getAlgoritm()) {
            case PGP:
                System.out.println("PGP");
                verifyResult = pgpService.verifyFile(request.getPublicKey(), request.getAlgoritmValue(), fileName);
                break;
            case MD5:
                System.out.println("MD5");
                verifyResult = new VerifyFileResult(Messages.CREATE_PUBLIC_KEY_FILE_ERROR.getMessageText(), false);
                Boolean result = hashService.md5Hash(fileName, request.getAlgoritmValue());
                System.out.println(result);
                break;
            case SHA256:
                verifyResult = new VerifyFileResult(Messages.CREATE_PUBLIC_KEY_FILE_ERROR.getMessageText(), false);
                System.out.println("SHA256");
                break;
            case SHA512:
                verifyResult = new VerifyFileResult(Messages.CREATE_PUBLIC_KEY_FILE_ERROR.getMessageText(), false);
                System.out.println("SHA512");
                break;
            default:
                verifyResult = new VerifyFileResult(Messages.CREATE_PUBLIC_KEY_FILE_ERROR.getMessageText(), false);
                break;
        }
        // 4.1 Powiadomienie o blędzie, który wystąpił podczas weryfikacji
        if (!verifyResult.isResult()) {
            return new ResponseEntity(new Document("message", verifyResult.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //4.2 Weryfikacja przebiegła pomyślnie
        return new ResponseEntity(new Document("message", verifyResult.getMessage()), HttpStatus.OK);
    }
}
