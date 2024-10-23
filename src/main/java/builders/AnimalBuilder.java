package builders;
import abstractclass.Builder;
import entities.animals.Animal;
import interfaces.Animals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static datagenerator.DataGeneratorDate.getDateBeforeMarchString;
import static datagenerator.DataGeneratorNames.getNickname;
import static datagenerator.DataGeneratorNumbers.getNumber;
import static datagenerator.DataGeneratorNumbers.range;

public class AnimalBuilder extends Builder<Animals> {

    @Override
    protected Animals createObject() {
        return new Animal();
    }

    public AnimalBuilder setAnimalKind(String kind) {
        setField("kind", kind);
        return this;
    }

    public AnimalBuilder setMarkerType() {
        setField("markerType", "Чип");
        return this;
    }

    public AnimalBuilder setMarkerType(String markerType) {
        setField("markerType", markerType);
        return this;
    }

    public AnimalBuilder setIdentificationNumber() {
        setField("identificationNumber", getNumber(15));
        return this;
    }

    public AnimalBuilder setIdentificationNumber(String number) {
        setField("identificationNumber", number);
        return this;
    }

    public AnimalBuilder setMarkerPlace() {
        List<String> markerPlaces = handbooksDB.values(
                "SELECT marker_places.name " +
                        "FROM marker_places " +
                        "JOIN kind_marker_places ON marker_places.id = kind_marker_places.marker_place_id " +
                        "JOIN kinds ON kinds.id = kind_marker_places.kind_id " +
                        "JOIN marker_types ON marker_types.id = kind_marker_places.marker_type_id " +
                        "WHERE kinds.name = " + "'" + object.getKind()+ "'" +
                        "AND marker_types.name = " + "'" + object.getMarkerType() + "'" +
                        "AND marker_places.deleted_at IS NULL", "name");
        setField("markerPlace", markerPlaces.get(random.nextInt(markerPlaces.size())));
        return this;
    }

    public AnimalBuilder setFirstMarkerDate() {
        setField("firstMarkerDate", getDateBeforeMarchString());
        return this;
    }

    public AnimalBuilder setFirstMarkerDate(String date) {
        setField("firstMarkerDate", date);
        return this;
    }

    public AnimalBuilder setRegistrationGround() {
        setField("registrationGround", "Заявление");
        return this;
    }

    public AnimalBuilder setSuit() {
        List<String> suits = handbooksDB.values("SELECT suits.name from suits " +
                "JOIN kinds ON suits.kind_id = kinds.id " +
                "WHERE kinds.name = '" + object.getKind() + "' " +
                "AND suits.deleted_at IS NULL", "name");
        setField("suit", suits.get(random.nextInt(suits.size())));
        return this;
    }

    public AnimalBuilder setBirthDate() {
        LocalDate markerDate = LocalDate.parse(object.getFirstMarkerDate(), DateTimeFormatter.ofPattern("ddMMyyyy"));
        if (object.getRegistrationGround().contains("Рождение животного")) {
            setField("birthDate", markerDate.minusMonths(range(1, 6)).format(DateTimeFormatter.ofPattern("ddMMyyyy")));
        } else {
            setField("birthDate", markerDate.minusYears(range(1, 3)).format(DateTimeFormatter.ofPattern("ddMMyyyy")));
        }
        return this;
    }

    public AnimalBuilder setGender() {
        List<String> gender = new ArrayList<>();
        gender.add("male");
        gender.add("female");
        setField("gender", gender.get(random.nextInt(gender.size())));
        return this;
    }

    public AnimalBuilder setNickName() {
        setField("nickName", getNickname());
        return this;
    }

    public AnimalBuilder setKeepType() {
        List<String> keepTypes = handbooksDB.values("SELECT keep_types.name FROM keep_types\n" +
                "JOIN kind_keep_types ON keep_types.id = kind_keep_types.keep_type_id \n" +
                "JOIN kinds ON kinds.id = kind_keep_types.kind_id\n" +
                "WHERE kinds.name = " + "'" + object.getKind() + "'" + " AND keep_types.deleted_at IS NULL", "name");
        setField("keepType", keepTypes.get(random.nextInt(keepTypes.size())));
        return this;
    }

    public AnimalBuilder setKeepPlace() {
        List<String> keepPlaces = handbooksDB.values("SELECT keep_places.name  from kind_keep_places \n" +
                "JOIN kinds \n" +
                "ON kind_keep_places.kind_id = kinds.id \n" +
                "JOIN keep_places\n" +
                "ON kind_keep_places.keep_place_id = keep_places.id \n" +
                "WHERE kinds.name = '" + object.getKind() + "'", "name");
        setField("keepPlace", keepPlaces.get(random.nextInt(keepPlaces.size())));
        return this;
    }

    public AnimalBuilder setProductDirection() {
        List<String> productDirections = handbooksDB.values("SELECT product_directions.name FROM kind_product_directions \n" +
                "JOIN kinds \n" +
                "ON kind_product_directions.kind_id = kinds.id \n" +
                "JOIN product_directions \n" +
                "ON kind_product_directions.product_direction_id = product_directions.id \n" +
                "WHERE kinds.name = '" + object.getKind() + "'", "name");
        setField("productDirection", productDirections.get(random.nextInt(productDirections.size())));
        return this;
    }

    public AnimalBuilder setSupervisedObjectName() {
        List<String> names = regagroDB.values("SELECT supervised_objects.name  FROM supervised_objects \n" +
                "JOIN enterprises \n" +
                "ON supervised_objects.enterprise_id = enterprises.id \n" +
                "JOIN service_areas \n" +
                "ON service_areas.id = enterprises.service_area_id \n" +
                "WHERE service_areas.name = 'Территория 1'\n" +
                "AND supervised_objects.deleted_at IS NULL", "name");
        setField("supervisedObjectName", names.get(random.nextInt(names.size())));
        return this;
    }

    public AnimalBuilder setEnterpriseName() {
        List<String> names = regagroDB.values("SELECT enterprises.name FROM enterprises \n" +
                "JOIN supervised_objects \n" +
                "ON supervised_objects.enterprise_id = enterprises.id \n" +
                "WHERE supervised_objects.name = '" + object.getSupervisedObjectName() + "'", "name");
        setField("enterpriseName", names.get(random.nextInt(names.size())));
        return this;
    }

    public AnimalBuilder setEnterpriseName(String enterpriseName) {
        setField("enterpriseName", enterpriseName);
        return this;
    }

    @Override
    public Animals build() {
        return object;
    }
}
