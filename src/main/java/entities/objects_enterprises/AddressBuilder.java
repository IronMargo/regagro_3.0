package entities.objects_enterprises;

import enums.User;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import services.ConfigReader;
import services.RequestSpecificationCreator;
import services.database.DBService;
import services.database.RegagroDBService;

import java.util.List;
import java.util.Map;
import java.util.Random;

import static datagenerator.DataGeneratorNumbers.getNumberRange;

public class AddressBuilder {
    private final RegagroDBService regagroDBService = DBService.getRegagroDBService();
    private final Address address;
    private final Random random;
    private String regionUuid;

    public AddressBuilder() {
        this.address = new Address();
        this.random = new Random();
    }



    private AddressFull getRandomAddress(String level, Map<String, String> cookies) {
        RequestSpecification reqSpec = RequestSpecificationCreator.getReqSpec(cookies);
        Response response = RestAssured.given()
                .spec(reqSpec)
                .when()
                .get("/ajax/address/object/" + regionUuid + "/children?levels=" + level);
        List<AddressFull> addresses = response.jsonPath().getList(".", AddressFull.class);
        AddressFull randomAddress = null;
        if (!(addresses.isEmpty())) {
            randomAddress = addresses.get(random.nextInt(addresses.size()));
            regionUuid = randomAddress.getObject_guid();
        }
        return randomAddress;
    }

    public AddressBuilder setDistrict(Map<String, String> cookies, User user) {
        String userEmail = ConfigReader.getUserEmail(user.getRole());
        regionUuid = regagroDBService.getRegionCode(userEmail);
        address.district = getRandomAddress("district", cookies).getName();
        return this;
    }

    public AddressBuilder setCity(Map<String, String> cookies) {
        address.city = getRandomAddress("city,locality", cookies).getName();
        return this;
    }

    public AddressBuilder setStreet(Map<String, String> cookies) {
        AddressFull randomAddress = getRandomAddress("street", cookies);
        if (randomAddress != null) {
            address.street = randomAddress.getName();
        }
        return this;
    }

    public AddressBuilder setHouse() {
        address.house = getNumberRange(1, 30);
        return this;
    }

    public Address build() {
        return address;
    }

}
