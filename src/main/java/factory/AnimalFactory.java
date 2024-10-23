package factory;

import builders.AnimalBuilder;
import builders.AnimalGroupBuilder;
import interfaces.Animals;

public class AnimalFactory {
    AnimalBuilder animalBuilder = new AnimalBuilder();
    AnimalGroupBuilder animalGroupBuilder = new AnimalGroupBuilder();

    public Animals createAnimal(String kind) {
        if (kind.equals("Крупный рогатый скот")) {
            return animalBuilder
                    .setAnimalKind(kind)
                    .setMarkerType()
                    .setMarkerPlace()
                    .setIdentificationNumber()
                    .setFirstMarkerDate()
                    .setRegistrationGround()
                    .setSuit()
                    .setBirthDate()
                    .setGender()
                    .setNickName()
                    .setKeepType()
                    .setKeepPlace()
                    .setProductDirection()
                    .setSupervisedObjectName()
                    .setEnterpriseName()
                    .build();
        }
        if (kind.equals("Куры")) {
            return animalBuilder
                    .setAnimalKind(kind)
                    .setMarkerType()
                    .setMarkerPlace()
                    .setIdentificationNumber()
                    .setFirstMarkerDate()
                    .setRegistrationGround()
                    .setSuit()
                    .setBirthDate()
                    .setGender()
                    .setNickName()
                    .setKeepType()
                    .setKeepPlace()
                    .setProductDirection()
                    .setSupervisedObjectName()
                    .setEnterpriseName()
                    .build();
        } else {
            return null;
        }
    }

    public Animals createAnimalGroup(String kind) {
        if (kind.equals("Свиньи")) {
            return animalGroupBuilder
                    .setAnimalKind(kind)
                    .setMarkerType()
                    .setMarkerPlace()
                    .setIdentificationNumber()
                    .setFirstMarkerDate()
                    .setRegistrationGround()
                    .setBirthDateRange()
                    .setGender()
                    .setCountOfMale()
                    .setCountOfFemale()
                    .setKeepType()
                    .setKeepPlace()
                    .setProductDirection()
                    .setSupervisedObjectName()
                    .setEnterpriseName()
                    .build();
        }
        if (kind.equals("Пчёлы")) {
            return animalGroupBuilder
                    .setAnimalKind(kind)
                    .setMarkerType()
                    .setMarkerPlace()
                    .setIdentificationNumber()
                    .setFirstMarkerDate()
                    .setRegistrationGround()
                    .setBirthDateRange()
                    .setKeepType()
                    .setProductDirection()
                    .setSupervisedObjectName()
                    .setEnterpriseName()
                    .build();
        } else {
            return null;
        }
    }

    public Animals createAnimal(String kind, String markerType, String number, String markerDate) {
            return animalBuilder
                    .setAnimalKind(kind)
                    .setMarkerType(markerType)
                    .setMarkerPlace()
                    .setIdentificationNumber(number)
                    .setFirstMarkerDate(markerDate)
                    .setRegistrationGround()
                    .setSuit()
                    .setBirthDate()
                    .setGender()
                    .setNickName()
                    .setKeepType()
                    .setKeepPlace()
                    .setProductDirection()
                    .setSupervisedObjectName()
                    .setEnterpriseName()
                    .build();
        }

    public Animals createAnimalGroup(String kind, String markerType, String number, String markerDate) {
        if (kind.equals("Свиньи")) {
            return animalGroupBuilder
                    .setAnimalKind(kind)
                    .setMarkerType(markerType)
                    .setMarkerPlace()
                    .setIdentificationNumber(number)
                    .setFirstMarkerDate(markerDate)
                    .setRegistrationGround()
                    .setBirthDateRange()
                    .setGender()
                    .setCountOfMale()
                    .setCountOfFemale()
                    .setKeepType()
                    .setKeepPlace()
                    .setProductDirection()
                    .setSupervisedObjectName()
                    .setEnterpriseName()
                    .build();
        }
        if (kind.equals("Пчёлы")) {
            return animalGroupBuilder
                    .setAnimalKind(kind)
                    .setMarkerType(markerType)
                    .setMarkerPlace()
                    .setIdentificationNumber(number)
                    .setFirstMarkerDate(markerDate)
                    .setRegistrationGround()
                    .setBirthDateRange()
                    .setKeepType()
                    .setProductDirection()
                    .setSupervisedObjectName()
                    .setEnterpriseName()
                    .build();
        } else {
            return null;
        }
    }
}
