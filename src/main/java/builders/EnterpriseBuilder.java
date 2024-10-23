package builders;

import entities.objects_enterprises.Address;
import entities.objects_enterprises.Enterprise;
import enums.User;
import services.ConfigReader;
import services.database.DBService;
import services.database.HandbooksDBService;

import java.util.List;
import java.util.Random;

public class EnterpriseBuilder {
    private final Random random;
    private final HandbooksDBService dbHelper;
    private final Enterprise enterprise;

    public EnterpriseBuilder() {
        this.enterprise = new Enterprise();
        this.dbHelper = DBService.getHandbooksDBService();
        this.random = new Random();
    }

    public EnterpriseBuilder setEnterpriseName(String name) {
        enterprise.enterpriseName = name;
        return this;
    }

    public EnterpriseBuilder setEnterpriseType() {
        int legalFormId = 1;
        List<String> enterpriseTypes = dbHelper.values(
                "SELECT enterprise_types.name\n" +
                        "FROM enterprise_types\n" +
                        "JOIN legal_form_enterprise_types\n" +
                        "ON enterprise_types.id = legal_form_enterprise_types.enterprise_type_id\n" +
                        "WHERE legal_form_enterprise_types.legal_form_id =" + legalFormId + " AND deleted_at IS NULL", "name");
        enterprise.typeOfEnterprise = enterpriseTypes.get(random.nextInt(enterpriseTypes.size()));
        return this;
    }

    public EnterpriseBuilder setOwnerInn() {
        enterprise.ownerInn = "7736280232";
        return this;
    }

    public EnterpriseBuilder setServiceArea(User user) {
        enterprise.serviceArea = ConfigReader.getUserServiceArea(user.getRole());
        return this;
    }

    public EnterpriseBuilder setDistrict(Address address) {
        enterprise.district = address.getDistrict();
        return this;
    }

    public EnterpriseBuilder setCity(Address address) {
        enterprise.city = address.getCity();
        return this;
    }

    public EnterpriseBuilder setStreet(Address address) {
        enterprise.street = address.getStreet();
        return this;
    }

    public EnterpriseBuilder setHouse(Address address) {
        enterprise.house = address.getHouse();
        return this;
    }
    public Enterprise build(){
        return enterprise;
    }
}
