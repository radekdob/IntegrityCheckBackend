package pl.raddob.integrity.checkintegrity.models;

public enum Messages {

    VERIFY_SYGNATURE_SUCCESS("Plik został zweryfikowany poprawnie"),
    VERIFY_SYGNATURE_WRONG_PUBLIC_KEY("Podany klucz publiczny nie pasuje do sygnatury"),
    VERIFY_SYGNATURE_ERROR_OCCURED("Werfikacja pliku nie udała się, wystąpił błąd"),

    DOWNLOAD_FILE_ERROR_OCCURED("Wystąpił błąd poczczas pobierania pliku. Sprawdź czy podany adres url jest prawidłowy"),
    PATH_TO_FILE_NOT_FOUND("Wystąpił błąd poczczas odczytywania pliku."),

    CREATE_PUBLIC_KEY_FILE_ERROR("Wystąpił błąd poczczas tworzenia pliku z kluczem publicznym."),
    CREATE_SIGNATURE_FILE_ERROR("Wystąpił błąd poczczas tworzenia pliku z sygnaturą."),

    GPG_IMPORT_PUBLIC_KEY_ERROR("Wystąpił błąd poczczas importowania klucza publicznego. Sprawdź jego format."),
    GPG_IMPORT_PUBLIC_KEY_ID_ERROR("Nie udało się uzyskać id zaimportowanego klucza publicznego. Usuń go ręcznie."),

    MD5_IS_CORRECT("Weryfikacja plik prz użyciu MD5 przebiegła pomyślnie."),
    MD5_IS_NOT_CORRECT("Weryfikacja plik przy użyciu MD5 przebiegła negatywnie."),

    SHA256_IS_CORRECT("Weryfikacja plik prz użyciu SHA256 przebiegła pomyślnie."),
    SHA256_IS_NOT_CORRECT("Weryfikacja plik przy użyciu SHA256 przebiegła negatywnie."),

    SHA512_IS_CORRECT("Weryfikacja plik prz użyciu SHA512 przebiegła pomyślnie."),
    SHA512_IS_NOT_CORRECT("Weryfikacja plik przy użyciu SHA512 przebiegła negatywnie."),

    VERIFY_INTEGRITY_SUCCESS_NO_LOCAL_LINK("Plik został zweryfikowany poprawnie."),

    USER_NOT_FOUND("Nie znaleziono użytkownika o takiej nazwie"),

    LOGGER_USER_LOGGED_IN("Użytkownik został zalogowany: "),

    REMOVE_FILE_ERROR("Nie udało się usunąć pliku: "),
    REMOVE_KEY_ERROR("Nie udało się usunąć klucza");




private String messageText;

    Messages(String message) {
        this.messageText = message;
    }

    public String getMessageText() {
        return messageText;
    }
}
