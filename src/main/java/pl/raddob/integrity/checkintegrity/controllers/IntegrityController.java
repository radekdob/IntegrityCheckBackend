package pl.raddob.integrity.checkintegrity.controllers;
import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.raddob.integrity.checkintegrity.models.*;
import pl.raddob.integrity.checkintegrity.services.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;


@RestController
@RequestMapping("v1/integrity/")
public class IntegrityController {

    private final CheckRequestFieldsService checkRequestFieldsService;
    private final FileDownloaderService downloaderService;
    private final HashService hashService;
    private final PgpService pgpService;
    private final DownloadedFileHistoryService downloadedFileHistoryService;

    public IntegrityController(CheckRequestFieldsService checkRequestFieldsService, FileDownloaderService downloaderService, HashService hashService, PgpService pgpService, DownloadedFileHistoryService downloadedFileHistoryService) {
        this.checkRequestFieldsService = checkRequestFieldsService;
        this.downloaderService = downloaderService;
        this.hashService = hashService;
        this.pgpService = pgpService;
        this.downloadedFileHistoryService = downloadedFileHistoryService;
    }


    @PostMapping("")
    public ResponseEntity<?> checkFileIntegrity(@RequestBody CheckFileIntegrityRequest request, Principal principal) throws IOException {


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
                verifyResult = pgpService.verifyFile(request.getPublicKey(), request.getAlgoritmValue(), fileName);
                break;
            case MD5:
                verifyResult = hashService.md5Hash(fileName, request.getAlgoritmValue());
                break;
            case SHA256:
                verifyResult = hashService.sha256Hash(fileName, request.getAlgoritmValue());
                break;
            case SHA512:
                verifyResult = hashService.sha512Hash(fileName, request.getAlgoritmValue());
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
        //4.3 Udostępnienie linku do zasobu lokalnego
        try {
            URL localLink = downloaderService.getLocalLinkToFile(fileName);
            //4.4 Jeśli użytkownik jest zalogowany to pobrany plik zostaje zapisany w jego profilu
            if (principal != null) {
                downloadedFileHistoryService.addAssetInfoToUser(fileName, localLink, principal.getName());
            }
            return new ResponseEntity(new IntegrityCheckResponse(verifyResult.getMessage(), localLink), HttpStatus.OK);
        } catch (MalformedURLException e) {
            return new ResponseEntity(new IntegrityCheckResponse(Messages.VERIFY_INTEGRITY_SUCCESS_NO_LOCAL_LINK.getMessageText()), HttpStatus.OK);
        }
    }
}
