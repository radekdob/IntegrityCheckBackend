package pl.raddob.integrity.checkintegrity.models;

public class ImportKeySerivceReturnType {


    private String keyId;
    private boolean status;


    public ImportKeySerivceReturnType(String keyId, boolean status) {
        this.keyId = keyId;
        this.status = status;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
