package pl.raddob.integrity.checkintegrity.models;

public class VerifySignatureServiceReturnType {

    private String message;
    private boolean status;

    public VerifySignatureServiceReturnType(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "VerifySignatureServiceReturnType{" +
                "message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
