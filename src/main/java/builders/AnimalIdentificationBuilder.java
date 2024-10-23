package builders;

import abstractclass.Builder;
import entities.animals.AnimalGroupResponse;
import entities.animals.AnimalIdentificationData;
import entities.animals.AnimalIdentificationResponse;
import enums.MarkerDates;
import services.AnimalResponseService;
import io.qameta.allure.internal.shadowed.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Map;

public class AnimalIdentificationBuilder extends Builder<AnimalIdentificationData> {
    private final AnimalResponseService animalResponseService = new AnimalResponseService();

    public AnimalIdentificationBuilder() {
        super();
    }

    @Override
    protected AnimalIdentificationData createObject() {
        return new AnimalIdentificationData();
    }

    public AnimalIdentificationBuilder setMarkerDate(String animalNumber, Map<String, String> cookies, MarkerDates markerDates) throws JsonProcessingException {
        String markerDate = animalResponseService.generateDate(animalNumber, cookies, markerDates);
        setField("markerDate", markerDate);
        return this;
    }

    public AnimalIdentificationBuilder setKind(String animalNumber, Map<String, String> cookies) throws JsonProcessingException {
        AnimalGroupResponse animalGroupResponse = animalResponseService.getAnimalGroupFullInfo(animalNumber, cookies);
        setField("kindId", handbooksDB.getValue(animalGroupResponse.getKind_id(), "kinds"));
        return this;
    }

    public AnimalIdentificationBuilder setMarkerTypeSame(String animalNumber, Map<String, String> cookies) throws JsonProcessingException {
        AnimalIdentificationResponse animalIdentificationResponse = animalResponseService.getAnimalInfoFromAjax(animalNumber, cookies);
        setField("markerTypeId", handbooksDB.getValue(animalIdentificationResponse.getMarker_type_id(), "marker_types"));
        return this;
    }

    public AnimalIdentificationBuilder setMarkerType(String animalNumber, Map<String, String> cookies) throws JsonProcessingException {
        String markerType = animalResponseService.getNewMainMarkerType(animalNumber, cookies, object.getMarkerDate());
        setField("markerTypeId", markerType);
        return this;
    }

    public AnimalIdentificationBuilder setMarkerType(String markerType) {
        setField("markerTypeId", markerType);
        return this;
    }

    public AnimalIdentificationBuilder setNumber(String number) {
        setField("number", number);
        return this;
    }

    public AnimalIdentificationBuilder setMarkerPlace() {
        List<String> markerPlaces = handbooksDB.values(
                "SELECT marker_places.name " +
                        "FROM marker_places " +
                        "JOIN kind_marker_places ON marker_places.id = kind_marker_places.marker_place_id " +
                        "JOIN kinds ON kinds.id = kind_marker_places.kind_id " +
                        "JOIN marker_types ON marker_types.id = kind_marker_places.marker_type_id " +
                        "WHERE kinds.id = " + "'" + object.getKindId() + "'" +
                        "AND marker_types.id = " + "'" + object.getMarkerTypeId() + "'" +
                        "AND marker_places.deleted_at IS NULL", "name");
        setField("markerPlace", markerPlaces.get(random.nextInt(markerPlaces.size())));
        return this;
    }

    @Override
    public AnimalIdentificationData build() {
        return object;
    }
}
