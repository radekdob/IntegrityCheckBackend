package pl.raddob.integrity.checkintegrity.models;

public class CheckRequestAlgoritmType {


    private String message;
    private boolean result;

    private AlgoritmTypes algoritm;

    public CheckRequestAlgoritmType(String message, boolean result, AlgoritmTypes algoritm) {
        this.message = message;
        this.result = result;
        this.algoritm = algoritm;
    }


    public AlgoritmTypes getAlgoritm() {
        return algoritm;
    }

    public void setAlgoritm(AlgoritmTypes algoritm) {
        this.algoritm = algoritm;
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
}


