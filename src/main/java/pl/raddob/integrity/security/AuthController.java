package pl.raddob.integrity.security;

import org.bson.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/")
public class AuthController {


    @GetMapping("")
    public Document logIn() {
        return new Document("status", "logged in");
    }
}
