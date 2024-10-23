package entities.animals;

import interfaces.Animals;

public class AnimalGroup implements Animals {
    String kind;
    String identificationNumber;
    String markerType;
    String markerPlace;
    String firstMarkerDate;
    String registrationGround;
    String birthDateFrom;
    String birthDateBefore;
    String gender;
    String count;
    String countOfMale;
    String countOfFemale;
    String keepType;
    String keepPlace;
    String productDirection;
    String supervisedObjectName;
    String enterpriseName;

    @Override
    public String getKind() {
        return kind;
    }

    @Override
    public String getIdentificationNumber() {
        return identificationNumber;
    }

    @Override
    public String getMarkerType() {
        return markerType;
    }

    @Override
    public String getMarkerPlace() {
        return markerPlace;
    }

    @Override
    public String getFirstMarkerDate() {
        return firstMarkerDate;
    }

    @Override
    public String getRegistrationGround() {
        return registrationGround;
    }

    @Override
    public String getSuit() {
        return null;
    }

    @Override
    public String getBirthDate() {
        return null;
    }

    public String getBirthDateFrom() {
        return birthDateFrom;
    }

    @Override
    public String getBirthDateBefore() {
        return birthDateBefore;
    }

    @Override
    public String getGender() {
        return gender;
    }

    @Override
    public String getNickName() {
        return null;
    }

    public String getCount() {
        return count;
    }

    @Override
    public String getCountOfMale() {
        return countOfMale;
    }

    @Override
    public String getCountOfFemale() {
        return countOfFemale;
    }

    @Override
    public String getKeepType() {
        return keepType;
    }

    @Override
    public String getKeepPlace() {
        return keepPlace;
    }

    @Override
    public String getProductDirection() {
        return productDirection;
    }

    @Override
    public String getSupervisedObjectName() {
        return supervisedObjectName;
    }

    @Override
    public String getEnterpriseName() {
        return enterpriseName;
    }
}
