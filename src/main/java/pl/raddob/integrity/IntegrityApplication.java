package pl.raddob.integrity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.raddob.integrity.checkintegrity.services.ImportKeyService;
import pl.raddob.integrity.checkintegrity.services.RemoveKeyService;
import pl.raddob.integrity.checkintegrity.services.VerifySignatureService;
import pl.raddob.integrity.security.ApplicationUserRepository;

@SpringBootApplication
public class IntegrityApplication {

    final ImportKeyService service;
    final RemoveKeyService removeKeyService;
    final VerifySignatureService verifySignatureService;
    final ApplicationUserRepository userRepository;
    private final PasswordEncoder encoder;

    public IntegrityApplication(ImportKeyService service, RemoveKeyService removeKeyService, VerifySignatureService verifySignatureService, ApplicationUserRepository userRepository, PasswordEncoder encoder) {
        this.service = service;
        this.removeKeyService = removeKeyService;
        this.verifySignatureService = verifySignatureService;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }


    public static void main(String[] args) {
        SpringApplication.run(IntegrityApplication.class, args);
/*

        boolean isWindows = System.getProperty("os.name")
                .toLowerCase().startsWith("windows");

        ProcessBuilder builder = new ProcessBuilder();
        ProcessBuilder builder2 = new ProcessBuilder();
        builder.directory(new File("/Users/radoslawdobrzynski/Downloads/pgp"));
        System.out.println(new FilesLocationConfiguration().getWorkingDirectory());
        builder2.directory(new File(new FilesLocationConfiguration().getWorkingDirectory()));

        if (isWindows) {
            builder.command("cmd.exe", "/c", "ping -n 3 google.com");
        } else {
            String x = "gpg --verify sig.txt aoo.dmg";
            builder.command("sh", "-c", x);
            //  builder2.command("sh", "-c", "gpg --import klucze.txt");
            builder2.command("sh", "-c", "ls");
        }

        try {
            Process process2 = builder2.start();

            StreamGobbler streamGobbler2 =
                    new StreamGobbler(process2.getInputStream(), System.out::println);
            Executors.newSingleThreadExecutor().submit(streamGobbler2);
            StreamGobbler errorGobbler2 = new StreamGobbler(process2.getErrorStream(), System.err::println);
            Executors.newSingleThreadExecutor().submit(errorGobbler2);
            int exitCode2 = process2.waitFor();
            //assert exitCode2 == 0;
            System.out.println("\n 2: 2: Exited with error code : " + exitCode2);
//sssssssssssssssssssy

            */
/*if (exitCode2 == 0) {

                Process process = builder.start();
                StreamGobbler streamGobbler =
                        new StreamGobbler(process.getInputStream(), System.out::println);
                Executors.newSingleThreadExecutor().submit(streamGobbler);
                StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream(), System.err::println);
                Executors.newSingleThreadExecutor().submit(errorGobbler);

                int exitCode = process.waitFor();
                assert exitCode == 0;
                System.out.println("\nExited with error code : " + exitCode);
            }*//*


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
*/


    }

    @EventListener(ApplicationReadyEvent.class)
    void test() {
//this.service.importKey();
        //this.removeKeyService.removeKey("Jim Jagielski");
//this.verifySignatureService.verifySignature();
     /*   ApplicationUser user1 = new ApplicationUser("admin", this.encoder.encode("admin"), Collections.emptyList());
        ApplicationUser user2 = new ApplicationUser("user", this.encoder.encode("user"), Collections.emptyList());
        this.userRepository.save(user1);
        this.userRepository.save(user2);*/

    }

}
