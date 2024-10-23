package factory;

import entities.objects_enterprises.Address;
import entities.objects_enterprises.AddressBuilder;
import enums.User;
import lombok.Getter;

import java.io.IOException;
import java.util.Map;

@Getter
public class AddressFactory {
    AddressBuilder addressBuilder = new AddressBuilder();

    public Address createAddress(Map<String, String> cookies, User user) {
        return addressBuilder
                .setDistrict(cookies, user)
                .setCity(cookies)
                .setStreet(cookies)
                .setHouse()
                .build();
    }
}
