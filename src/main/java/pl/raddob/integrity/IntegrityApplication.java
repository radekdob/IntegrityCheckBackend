package pl.raddob.integrity;

import org.bouncycastle.openpgp.PGPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.raddob.integrity.checkintegrity.services.ImportKeyService;
import pl.raddob.integrity.checkintegrity.services.PgpService;
import pl.raddob.integrity.checkintegrity.services.RemoveKeyService;
import pl.raddob.integrity.checkintegrity.services.VerifySignatureService;
import pl.raddob.integrity.security.repositories.ApplicationUserRepository;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class IntegrityApplication {

    final ImportKeyService service;
    final RemoveKeyService removeKeyService;
    final VerifySignatureService verifySignatureService;
    final ApplicationUserRepository userRepository;
    private final PasswordEncoder encoder;
    @Autowired
     PgpService pgpService;

    public IntegrityApplication(ImportKeyService service, RemoveKeyService removeKeyService, VerifySignatureService verifySignatureService, ApplicationUserRepository userRepository, PasswordEncoder encoder) {
        this.service = service;
        this.removeKeyService = removeKeyService;
        this.verifySignatureService = verifySignatureService;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }


    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, PGPException {
        SpringApplication.run(IntegrityApplication.class, args);


       /* Security.setProperty("crypto.policy", "unlimited");
        int maxKeySize = javax.crypto.Cipher.getMaxAllowedKeyLength("AES");
        System.out.println("Max Key Size for AES : " + maxKeySize);
*/
    }

    @EventListener(ApplicationReadyEvent.class)
    void test() throws IOException, PGPException {


       // System.out.println( pgpService.checkPgpSignature());

//this.service.importKey();
        //this.removeKeyService.removeKey("Jim Jagielski");
//this.verifySignatureService.verifySignature();
     /*   ApplicationUser user1 = new ApplicationUser("admin", this.encoder.encode("admin"), Collections.emptyList());
        ApplicationUser user2 = new ApplicationUser("user", this.encoder.encode("user"), Collections.emptyList());
        this.userRepository.save(user1);
        this.userRepository.save(user2);*/

    }

}
