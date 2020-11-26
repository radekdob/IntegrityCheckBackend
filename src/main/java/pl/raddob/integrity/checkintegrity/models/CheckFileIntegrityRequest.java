package pl.raddob.integrity.checkintegrity.models;

public class CheckFileIntegrityRequest {

    private String algoritmType;
    private String algoritmValue;
    private String assetAddress;
    private String publicKey;

    public CheckFileIntegrityRequest(String algoritmType, String algoritmValue, String assetAddress, String publicKey) {
        this.algoritmType = algoritmType;
        this.algoritmValue = algoritmValue;
        this.assetAddress = assetAddress;
        this.publicKey = publicKey;
    }

    public String getAlgoritmType() {
        return algoritmType;
    }

    public void setAlgoritmType(String algoritmType) {
        this.algoritmType = algoritmType;
    }

    public String getAlgoritmValue() {
        return algoritmValue;
    }

    public void setAlgoritmValue(String algoritmValue) {
        this.algoritmValue = algoritmValue;
    }

    public String getAssetAddress() {
        return assetAddress;
    }

    public void setAssetAddress(String assetAddress) {
        this.assetAddress = assetAddress;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }


    @Override
    public String toString() {
        return "CheckPgpSignatureRequest{" +
                "algoritmType='" + algoritmType + '\'' +
                ", algoritmValue='" + algoritmValue + '\'' +
                ", assetAddress='" + assetAddress + '\'' +
                ", publicKey='" + publicKey + '\'' +
                '}';
    }
}
