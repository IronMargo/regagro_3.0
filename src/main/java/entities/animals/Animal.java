package entities.animals;

import interfaces.Animals;

public class Animal implements Animals {
    String kind;
    String identificationNumber;
    String markerType;
    String markerPlace;
    String firstMarkerDate;
    String registrationGround;
    String suit;
    String birthDate;
    String gender;
    String nickName;
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
        return suit;
    }
    @Override
    public String getBirthDate() {
        return birthDate;
    }
    @Override
    public String getGender() {
        return gender;
    }
    @Override
    public String getNickName() {
        return nickName;
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

    @Override
    public String getBirthDateFrom() {
        return null;
    }

    @Override
    public String getBirthDateBefore() {
        return null;
    }

    @Override
    public String getCount() {
        return null;
    }

    @Override
    public String getCountOfMale() {
        return null;
    }

    @Override
    public String getCountOfFemale() {
        return null;
    }
}
