package pl.raddob.integrity.checkintegrity.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import pl.raddob.integrity.checkintegrity.models.AlgoritmTypes;
import pl.raddob.integrity.checkintegrity.models.CheckFileIntegrityRequest;
import pl.raddob.integrity.checkintegrity.models.CheckRequestFieldsReturnType;


public class CheckRequestFieldServiceTest {

    @Nested
    class CheckField {

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" "})
        public void checkField_algoritmTypeInvalidValues_returnsBrakWszystkichDanychAndFalse(String algoritmType) {

            //given
            CheckRequestFieldsService service = new CheckRequestFieldsService();
            CheckFileIntegrityRequest request = new CheckFileIntegrityRequest(algoritmType, "PGP", "https://",
                    "asndhasbdjaskdasdasdsa");

            //when
            CheckRequestFieldsReturnType checkRequestFieldsReturnType = service.checkFields(request);

            //then
            Assertions.assertAll(
                    () -> Assertions.assertEquals("Brak wszystkich danych", checkRequestFieldsReturnType.getMessage()),
                    () -> Assertions.assertFalse(checkRequestFieldsReturnType.isResult())
            );
        }


        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" "})
        public void checkField_algoritmValueInvalidValues_returnsBrakWszystkichDanychAndFalse(String algoritmValue) {

            //given
            CheckRequestFieldsService service = new CheckRequestFieldsService();
            CheckFileIntegrityRequest request = new CheckFileIntegrityRequest("PGP", algoritmValue, "https://",
                    "asndhasbdjaskdasdasdsa");

            //when
            CheckRequestFieldsReturnType checkRequestFieldsReturnType = service.checkFields(request);

            //then
            Assertions.assertAll(
                    () -> Assertions.assertEquals("Brak wszystkich danych", checkRequestFieldsReturnType.getMessage()),
                    () -> Assertions.assertFalse(checkRequestFieldsReturnType.isResult())
            );
        }


        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" "})
        public void checkField_assetAddressInvalidValues_returnsBrakWszystkichDanychAndFalse(String assetAddress) {

            //given
            CheckRequestFieldsService service = new CheckRequestFieldsService();
            CheckFileIntegrityRequest request = new CheckFileIntegrityRequest("PGP", "some value", assetAddress,
                    "asndhasbdjaskdasdasdsa");

            //when
            CheckRequestFieldsReturnType checkRequestFieldsReturnType = service.checkFields(request);

            //then
            Assertions.assertAll(
                    () -> Assertions.assertEquals("Brak wszystkich danych", checkRequestFieldsReturnType.getMessage()),
                    () -> Assertions.assertFalse(checkRequestFieldsReturnType.isResult())
            );
        }


        @Test
        public void checkField_allParametersNotNullAndNotBlankAndNotEmpty_returnsRequestJestPrawidlowyAndTrue() {

            //given
            CheckRequestFieldsService service = new CheckRequestFieldsService();
            CheckFileIntegrityRequest request = new CheckFileIntegrityRequest("PGP", "--pgp signature--", "https://localhost:8080",
                    "asndhasbdj23askdasdasd21sa12");

            //when
            CheckRequestFieldsReturnType checkRequestFieldsReturnType = service.checkFields(request);

            //then
            Assertions.assertAll(
                    () -> Assertions.assertEquals("Request jest prawidłowy", checkRequestFieldsReturnType.getMessage()),
                    () -> Assertions.assertTrue(checkRequestFieldsReturnType.isResult())
            );
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" "})
        public void checkField_allParametersInvalidValue_returnsBrakWszystkichDanychAndFalse(String invalidValue) {

            //given
            CheckRequestFieldsService service = new CheckRequestFieldsService();
            CheckFileIntegrityRequest request = new CheckFileIntegrityRequest(invalidValue, invalidValue, invalidValue,
                    "asndhasbdj23askdasdasd21sa12");

            //when
            CheckRequestFieldsReturnType checkRequestFieldsReturnType = service.checkFields(request);

            //then
            Assertions.assertAll(
                    () -> Assertions.assertEquals("Brak wszystkich danych", checkRequestFieldsReturnType.getMessage()),
                    () -> Assertions.assertFalse(checkRequestFieldsReturnType.isResult())
            );
        }

    }


    @Nested
    class CheckAlgoritmType {

        @Test
        public void checkAlgoritmType_ifArgumentIsNotEnumValue_returnsAlgorytmNieJestWspieranyAndResultFalseAndAlgoritmNull() {
            //given
            var service = new CheckRequestFieldsService();
            var request = new CheckFileIntegrityRequest("abc", "--pgp signature--", "https://localhost:8080",
                    "asndhasbdj23askdasdasd21sa12");

            //when
            var returnObject = service.checkAlgoritmType(request);

            //then
            Assertions.assertAll(
                    () -> {
                        Assertions.assertEquals("Algorytm: " + request.getAlgoritmType() + " nie jest wspierany.", returnObject.getMessage());
                    },
                    () -> {
                        Assertions.assertEquals(false, returnObject.isResult());
                    },
                    () -> {
                        Assertions.assertEquals(null, returnObject.getAlgoritm());
                    }
            );

        }

        @Test
        public void checkAlgoritmType_ifArgumentIsLowerCaseEnumValue_doesNotReturnAlgorytmNieJestWspieranyAndResultFalseAndAlgoritmNull() {
            //given
            var service = new CheckRequestFieldsService();
            var request = new CheckFileIntegrityRequest("pgp", "--pgp signature--", "https://localhost:8080",
                    "asndhasbdj23askdasdasd21sa12");

            //when
            var returnObject = service.checkAlgoritmType(request);

            //then
            Assertions.assertAll(
                    () -> {
                        Assertions.assertNotEquals("Algorytm: " + request.getAlgoritmType() + " nie jest wspierany.", returnObject.getMessage());
                    },
                    () -> {
                        Assertions.assertNotEquals(false, returnObject.isResult());
                    },
                    () -> {
                        Assertions.assertNotEquals(null, returnObject.getAlgoritm());
                    }
            );

        }

        @Test
        public void checkAlgoritmType_ifArgumentIsEnumValue_doesNotReturnAlgorytmNieJestWspieranyAndResultFalseAndAlgoritmNull() {
            //given
            var service = new CheckRequestFieldsService();
            var request = new CheckFileIntegrityRequest("PGP", "--pgp signature--", "https://localhost:8080",
                    "asndhasbdj23askdasdasd21sa12");

            //when
            var returnObject = service.checkAlgoritmType(request);

            //then
            Assertions.assertAll(
                    () -> {
                        Assertions.assertNotEquals("Algorytm: " + request.getAlgoritmType() + " nie jest wspierany.", returnObject.getMessage());
                    },
                    () -> {
                        Assertions.assertNotEquals(false, returnObject.isResult());
                    },
                    () -> {
                        Assertions.assertNotEquals(null, returnObject.getAlgoritm());
                    }
            );

        }


        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" "})
        public void checkAlgoritmType_ifAlgoritmTypeIsPgpAndPublicKeyHasInvalidValues_returnBrakKluczaPublicznegoAndAlgoritmTypePGP(String publicKeyValue) {
            //given
            var algoritmTypeEnum = AlgoritmTypes.PGP;
            var service = new CheckRequestFieldsService();
            var request = new CheckFileIntegrityRequest("PGP", "--pgp signature--", "https://localhost:8080",
                    publicKeyValue);

            //when
            var returnObject = service.checkAlgoritmType(request);

            //then
            Assertions.assertAll(
                    () -> {
                        Assertions.assertEquals("Brak klucza publicznego", returnObject.getMessage());
                    },
                    () -> {
                        Assertions.assertEquals(false, returnObject.isResult());
                    },
                    () -> {
                        Assertions.assertEquals(algoritmTypeEnum, returnObject.getAlgoritm());
                    }
            );

        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" "})
        public void checkAlgoritmType_ifAlgoritmTypeIsNotPgpAndPublicKeyHasInvalidValues_returnBrakKluczaPublicznegoAndAlgoritmTypePGP(String publicKeyValue) {
            //given
            var algoritmTypeEnum = AlgoritmTypes.MD5;
            var service = new CheckRequestFieldsService();
            var request = new CheckFileIntegrityRequest("MD5", "--pgp signature--", "https://localhost:8080",
                    publicKeyValue);

            //when
            var returnObject = service.checkAlgoritmType(request);

            //then
            Assertions.assertAll(
                    () -> {
                        Assertions.assertEquals("Algorytm jest wspierany", returnObject.getMessage());
                    },
                    () -> {
                        Assertions.assertEquals(true, returnObject.isResult());
                    },
                    () -> {
                        Assertions.assertEquals(algoritmTypeEnum, returnObject.getAlgoritm());
                    }
            );

        }





        @ParameterizedTest
        @ValueSource(strings = {"pgp", "PGP", "md5", "MD5", "SHA256", "sha256", "SHA512", "sha512"})
        public void checkAlgoritmType_ifArgumentsForAlgoritmTypeAreValid_returnsAlgorytmJestWspieranyAndResultTrueAndAlgoritmTypeEnum(String algoritmType) {
            //given
            var service = new CheckRequestFieldsService();
            var request = new CheckFileIntegrityRequest(algoritmType, "--pgp signature--", "https://localhost:8080",
                    "asndhasbdj23askdasdasd21sa12");
            var algoritmTypeEnum = Enum.valueOf(AlgoritmTypes.class, request.getAlgoritmType().toUpperCase());
            //when
            var returnObject = service.checkAlgoritmType(request);

            //then
            Assertions.assertAll(
                    () -> {
                        Assertions.assertEquals("Algorytm jest wspierany", returnObject.getMessage());
                    },
                    () -> {
                        Assertions.assertEquals(true, returnObject.isResult());
                    },
                    () -> {
                        Assertions.assertEquals(algoritmTypeEnum, returnObject.getAlgoritm());
                    }
            );

        }
    }



}


