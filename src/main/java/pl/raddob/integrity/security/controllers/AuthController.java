package pl.raddob.integrity.security.controllers;

import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.raddob.integrity.checkintegrity.models.Messages;
import pl.raddob.integrity.security.services.ApplicationUserProfileService;

import java.security.Principal;

@RestController
@RequestMapping("/auth/")
public class AuthController {

    private final ApplicationUserProfileService userProfileService;

    public AuthController(ApplicationUserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }


    @GetMapping("")
    public Document logIn() {
        return new Document("status", "logged in");
    }

    @GetMapping("profile")
    public ResponseEntity<?> getUserProfile(Principal principal) {

        try {
            return new ResponseEntity(userProfileService.getUserProfile(principal.getName()),
                    HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity(new Document("message", Messages.USER_NOT_FOUND.getMessageText()),
                    HttpStatus.NO_CONTENT);
        }
    }
}
