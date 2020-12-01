package pl.raddob.integrity.security;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.raddob.integrity.security.model.ApplicationUser;

import java.util.Optional;

@Repository
public interface ApplicationUserRepository extends MongoRepository<ApplicationUser, ObjectId> {

    Optional<ApplicationUser> findByUsername(String username);

}
