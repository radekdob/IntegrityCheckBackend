package pl.raddob.integrity.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.raddob.integrity.security.model.ApplicationUser;
import pl.raddob.integrity.security.services.ApplicationUserProfileService;

@SpringBootTest
public class ApplicationUserProfileServiceTest {

    @Autowired
    private ApplicationUserProfileService applicationUserProfileService;

    @Test
    public void getUserProfile_passNotExistingUsername_throwUserNotFoundException() {

        //given
        var username = "user1";
        //then
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> applicationUserProfileService.getUserProfile(username));
    }
    @Test
    public void getUserProfile_passExistingUsername_returnApplicationUser() {

        //given
        var username = "admin";
        //when
        ApplicationUser profile = applicationUserProfileService.getUserProfile(username);
       //then
        Assertions.assertNotNull(profile);
    }

}
