package pl.raddob.integrity.checkintegrity.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.raddob.integrity.checkintegrity.models.FileExtensions;

@Repository
public interface FileExtensionsRepository  extends MongoRepository<FileExtensions, ObjectId> {




}
