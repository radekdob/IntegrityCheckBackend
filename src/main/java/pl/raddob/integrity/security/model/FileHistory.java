package pl.raddob.integrity.security.model;

public class FileHistory {


    private String filename;

    private String  localLink;

    public FileHistory(String filename, String localLink) {
        this.filename = filename;
        this.localLink = localLink;
    }


    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getLocalLink() {
        return localLink;
    }

    public void setLocalLink(String localLink) {
        this.localLink = localLink;
    }
}
