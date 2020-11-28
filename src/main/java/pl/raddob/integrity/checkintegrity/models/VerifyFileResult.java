package pl.raddob.integrity.checkintegrity.models;

public class VerifyFileResult {

    private String message;

    private boolean result;

    public VerifyFileResult(String message, boolean result) {
        this.message = message;
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "PgpServiceVerifyFileReturnType{" +
                "message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
