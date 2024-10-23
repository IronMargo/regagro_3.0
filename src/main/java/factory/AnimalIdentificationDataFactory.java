package factory;

import builders.AnimalIdentificationBuilder;
import entities.animals.AnimalIdentificationData;
import enums.MarkerDates;
import io.qameta.allure.internal.shadowed.jackson.core.JsonProcessingException;

import java.util.Map;

public class AnimalIdentificationDataFactory {
    private final AnimalIdentificationBuilder animalIdentificationBuilder = new AnimalIdentificationBuilder();
    public AnimalIdentificationData createNewIdentificationData(
            String animalNumberCurrent, String number, MarkerDates markerDate, Map<String, String> cookies) throws JsonProcessingException {
        return animalIdentificationBuilder
                .setKind(animalNumberCurrent, cookies)
                .setMarkerDate(animalNumberCurrent, cookies, markerDate)
                .setMarkerType(animalNumberCurrent, cookies)
                .setMarkerPlace()
                .setNumber(number)
                .build();
    }
    public AnimalIdentificationData createNewIdentificationData(
            String animalNumberCurrent, String markerType, String number, MarkerDates markerDate, Map<String, String> cookies) throws JsonProcessingException {
        return animalIdentificationBuilder
                .setKind(animalNumberCurrent, cookies)
                .setMarkerDate(animalNumberCurrent, cookies, markerDate)
                .setMarkerType(markerType)
                .setMarkerPlace()
                .setNumber(number)
                .build();
    }
}
