package pl.raddob.integrity.security;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("users")
public class ApplicationUser {

    @Id
    private ObjectId id;

    private String username;

    private String password;

    private List<String> downloadedFilesNames;

    public ApplicationUser(String username, String password, List<String> downloadedFilesNames) {
        this.username = username;
        this.password = password;
        this.downloadedFilesNames = downloadedFilesNames;
    }


    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getDownloadedFilesNames() {
        return downloadedFilesNames;
    }

    public void setDownloadedFilesNames(List<String> downloadedFilesNames) {
        this.downloadedFilesNames = downloadedFilesNames;
    }
}
