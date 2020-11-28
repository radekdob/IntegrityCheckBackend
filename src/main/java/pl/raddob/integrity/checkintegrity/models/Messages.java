package pl.raddob.integrity.checkintegrity.models;

public enum Messages {

    VERIFY_SYGNATURE_SUCCESS("Plik został zweryfiikowany poprawnie"),
    VERIFY_SYGNATURE_WRONG_PUBLIC_KEY("Podany klucz publiczny nie pasuje do sygnatury"),
    VERIFY_SYGNATURE_ERROR_OCCURED("Werfikacja pliku nie udała się, wystąpił błąd"),

    DOWNLOAD_FILE_ERROR_OCCURED("Wystąpił błąd poczczas pobierania pliku. Sprawdź czy podany adres url jest prawidłowy"),

    CREATE_PUBLIC_KEY_FILE_ERROR("Wystąpił błąd poczczas tworzenia pliku z kluczem publicznym."),
    CREATE_SIGNATURE_FILE_ERROR("Wystąpił błąd poczczas tworzenia pliku z sygnaturą."),

    GPG_IMPORT_PUBLIC_KEY_ERROR("Wystąpił błąd poczczas importowania klucza publicznego.");

private String messageText;

    Messages(String message) {
        this.messageText = message;
    }

    public String getMessageText() {
        return messageText;
    }
}
