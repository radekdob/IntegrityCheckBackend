# IntegrityCheckBackend

Aplikacja serwerowa systemu IntegrityCheck.

Baza danych: MongoDB Atlas https://www.mongodb.com/cloud/atlas do przechowywania użytkownikow i ich historii pobranych plików oraz do obsługiwanych rozszerzeń plików.

Wymagania techniczne do uruchomienia programu:

1. Min. Java 11/OpenJDK 11 https://adoptopenjdk.net/
2. Zainstalowany GnuPG https://gnupg.org/download/index.html (binary releases)
    - dla windows https://gnupg.org/ftp/gcrypt/binary/gnupg-w32-2.2.25_20201123.exe
    - po instalacji warto sprawdzić, czy został prawidłowo zainstalowany poprzez komendę "gpg --version" (instalator powinien sam dodac zmienną środowiskową "gpg", ale gdyby jednak tak się nie stało to instrukcja jak ją dodać jest tu: https://www.phildev.net/pgp/gpginstall.html, domyślnie zmienna PATH powinna zawierać C:\Program Files (x86)\GnuPG\bin) )
    - jeśli program GnuPG był już zainstalowany to konieczne jest, aby wyzerować zbiór kluczy publicznych !
3. Pobrany plik IntegrityJAR.zip rozpakować
    - W folderze "config" w pliku application.properties można dokonać zmian konfiguracyjnych
    - W tym pliku należy zmienić wartośc zmiennej "working.directory" na adres folderu, w  którym przetwarzane będą pliki np.C:/Users/dobrzynski_r/Desktop/files/ (UWAGA NA ODPOWIEDNI SLASH, gdyż Java wymaga zapisu jak w tym przykładzie oraz scieżka ma być absolutna, też tak jak w przykładzie)
    - Logi aplikacji także znajdują się w folderze "config" w pliku "logs.txt."
    - Klucze RSA znajdują się w katalogu "config", jeśli zostaną zmienione to należy w pliku "application.properties" zmienić ich hasło oraz ścieżkę (jeśli uległa zmianie)
4. Uruchomić aplikacje poprzez komendę "java -jar integrity.jar" (serwer tomcat zostanie uruchomiony na localhost:8443)
5. Uruchomić aplikację kliencką: https://github.com/radekdob/IntegrityCheck
