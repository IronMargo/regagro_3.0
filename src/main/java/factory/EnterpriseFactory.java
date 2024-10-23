package factory;

import builders.EnterpriseBuilder;
import entities.objects_enterprises.Address;
import entities.objects_enterprises.Enterprise;
import enums.User;

public class EnterpriseFactory {
    EnterpriseBuilder enterpriseBuilder = new EnterpriseBuilder();
    public Enterprise createEnterprise(String name, Address address, User user){
        return enterpriseBuilder
                .setEnterpriseName(name)
                .setEnterpriseType()
                .setOwnerInn()
                .setServiceArea(user)
                .setDistrict(address)
                .setCity(address)
                .setStreet(address)
                .setHouse(address)
                .build();
    }

}
