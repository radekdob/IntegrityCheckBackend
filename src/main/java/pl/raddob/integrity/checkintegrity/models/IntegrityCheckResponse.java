package pl.raddob.integrity.checkintegrity.models;

import java.net.URL;

public class IntegrityCheckResponse {
    private String message;
    private URL localLink;


    public IntegrityCheckResponse(String message, URL localLink) {
        this.message = message;
        this.localLink = localLink;
    }

    public IntegrityCheckResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public URL getLocalLink() {
        return localLink;
    }

    public void setLocalLink(URL localLink) {
        this.localLink = localLink;
    }
}
