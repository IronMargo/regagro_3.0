package builders;

import abstractclass.Builder;
import entities.animals.AnimalGroup;
import interfaces.Animals;

import java.util.ArrayList;
import java.util.List;

import static datagenerator.DataGeneratorDate.getDateBeforeMarchString;
import static datagenerator.DataGeneratorDate.getDateRange;
import static datagenerator.DataGeneratorNumbers.getNumber;
import static datagenerator.DataGeneratorNumbers.getNumberRange;

public class AnimalGroupBuilder extends Builder<Animals> {

    public AnimalGroupBuilder() {
        super();
    }

    @Override
    protected Animals createObject() {
        return new AnimalGroup();
    }
    public AnimalGroupBuilder setAnimalKind(String kind) {
        setField("kind", kind);
        return this;
    }
    public AnimalGroupBuilder setMarkerType() {
        setField("markerType", "Внутрихозяйственное");
        return this;
    }
    public AnimalGroupBuilder setMarkerType(String markerType) {
        setField("markerType",markerType);
        return this;
    }
    public AnimalGroupBuilder setIdentificationNumber() {
        setField("identificationNumber", getNumber(9));
        return this;
    }
    public AnimalGroupBuilder setIdentificationNumber(String number) {
        setField("identificationNumber", number);
        return this;
    }
    public AnimalGroupBuilder setMarkerPlace() {
        List<String> markerPlaces = handbooksDB.values(
                "SELECT name\n" +
                        "FROM marker_places\n" +
                        "WHERE is_only_group = 1 ", "name");
        setField("markerPlace", markerPlaces.get(0));
        return this;
    }
    public AnimalGroupBuilder setFirstMarkerDate() {
        setField("firstMarkerDate", getDateBeforeMarchString());
        return this;
    }
    public AnimalGroupBuilder setFirstMarkerDate(String date) {
        setField("firstMarkerDate", date);
        return this;
    }
    public AnimalGroupBuilder setRegistrationGround() {
        setField("registrationGround", "Заявление");
        return this;
    }
    public AnimalGroupBuilder setBirthDateRange() {
        if (object.getKind().matches("Пчёлы")) {
            setField("birthDateFrom", getDateRange(object.getRegistrationGround()).get(0));
        } else {
            List<String> dateRange = getDateRange(object.getRegistrationGround());
            setField("birthDateFrom", getDateRange(object.getRegistrationGround()).get(0));
            setField("birthDateBefore", getDateRange(object.getRegistrationGround()).get(1));
        }
        return this;
    }
    public AnimalGroupBuilder setGender() {
        List<String> gender = new ArrayList<>();
        gender.add("male");
        gender.add("female");
        gender.add("mixed");
        setField("gender", gender.get(random.nextInt(gender.size())));
        return this;
    }
    public AnimalGroupBuilder setCountOfMale() {
        setField("countOfMale", getNumberRange(6, 20));
        return this;
    }
    public AnimalGroupBuilder setCountOfFemale() {
        setField("countOfFemale", getNumberRange(6, 20));
        return this;
    }
    public AnimalGroupBuilder setKeepType() {
        List<String> keepTypes = handbooksDB.values("SELECT keep_types.name FROM keep_types\n" +
                        "JOIN kind_keep_types ON keep_types.id = kind_keep_types.keep_type_id \n" +
                        "JOIN kinds ON kinds.id = kind_keep_types.kind_id\n" +
                        "WHERE kinds.name = " + "'" + object.getKind() + "'" + " AND keep_types.deleted_at IS NULL"
                , "name");
        setField("keepType", keepTypes.get(random.nextInt(keepTypes.size())));
        return this;
    }
    public AnimalGroupBuilder setKeepPlace() {
        List<String> keepPlaces = handbooksDB.values("SELECT keep_places.name  from kind_keep_places \n" +
                "JOIN kinds \n" +
                "ON kind_keep_places.kind_id = kinds.id \n" +
                "JOIN keep_places\n" +
                "ON kind_keep_places.keep_place_id = keep_places.id \n" +
                "WHERE kinds.name = '" + object.getKind() + "'", "name");
        setField("keepPlace", keepPlaces.get(random.nextInt(keepPlaces.size())));
        return this;
    }
    public AnimalGroupBuilder setProductDirection() {
        List<String> productDirections = handbooksDB.values("SELECT product_directions.name from kind_product_directions \n" +
                "JOIN kinds \n" +
                "ON kind_product_directions.kind_id = kinds.id \n" +
                "JOIN product_directions \n" +
                "ON kind_product_directions.product_direction_id  = product_directions.id \n" +
                "WHERE kinds.name = '" + object.getKind() + "'", "name");
        setField("productDirection", productDirections.get(random.nextInt(productDirections.size())));
        return this;
    }
    public AnimalGroupBuilder setSupervisedObjectName(){
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
    public AnimalGroupBuilder setEnterpriseName(){
        List<String> names = regagroDB.values("SELECT enterprises.name FROM enterprises \n" +
                "JOIN supervised_objects \n" +
                "ON supervised_objects.enterprise_id = enterprises.id \n" +
                "WHERE supervised_objects.name = '" + object.getSupervisedObjectName() + "'", "name");
        setField("enterpriseName", names.get(random.nextInt(names.size())));
        return this;
    }
    @Override
    public Animals build() {
        return object;
    }
}
