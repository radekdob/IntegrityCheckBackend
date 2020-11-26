package pl.raddob.integrity.checkintegrity.services;

import com.google.common.base.Strings;
import org.springframework.stereotype.Service;
import pl.raddob.integrity.checkintegrity.models.AlgoritmTypes;
import pl.raddob.integrity.checkintegrity.models.CheckFileIntegrityRequest;
import pl.raddob.integrity.checkintegrity.models.CheckRequestAlgoritmType;
import pl.raddob.integrity.checkintegrity.models.CheckRequestFieldsReturnType;

@Service
public class CheckRequestFieldsService {

    public CheckRequestFieldsService() {
    }

    public CheckRequestFieldsReturnType checkFields(CheckFileIntegrityRequest request) {

        if ((Strings.isNullOrEmpty(request.getAlgoritmType()) || request.getAlgoritmType().isBlank()) ||
                (Strings.isNullOrEmpty(request.getAlgoritmValue()) || request.getAlgoritmValue().isBlank()) ||
                (Strings.isNullOrEmpty(request.getAssetAddress()) || request.getAssetAddress().isBlank())) {
            return new CheckRequestFieldsReturnType("Brak wszystkich danych", false);
        } else {
            return new CheckRequestFieldsReturnType("Request jest prawidłowy", true);
        }
    }

    public CheckRequestAlgoritmType checkAlgoritmType(CheckFileIntegrityRequest request) {
        String algoritmType = request.getAlgoritmType();
        AlgoritmTypes algoritmTypeEnum;

        try {
            algoritmTypeEnum = Enum.valueOf(AlgoritmTypes.class, algoritmType.toUpperCase());
        } catch (IllegalArgumentException e) {
            return new CheckRequestAlgoritmType("Algorytm: " + algoritmType + " nie jest wspierany.",
                    false, null);
        }

        if (algoritmTypeEnum == AlgoritmTypes.PGP &&
                ((Strings.isNullOrEmpty(request.getPublicKey())) ||
                        (request.getPublicKey() != null && request.getPublicKey().isBlank()))) {
            return new CheckRequestAlgoritmType("Brak klucza publicznego",
                    false, algoritmTypeEnum);
        }
        return new CheckRequestAlgoritmType("Algorytm jest wspierany", true, algoritmTypeEnum);
    }


}
