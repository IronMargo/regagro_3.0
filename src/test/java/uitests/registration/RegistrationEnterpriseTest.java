package uitests.registration;

import com.codeborne.selenide.Selenide;
import entities.objects_enterprises.Address;
import entities.objects_enterprises.Enterprise;
import enums.User;
import factory.AddressFactory;
import factory.EnterpriseFactory;
import org.junit.jupiter.api.RepeatedTest;
import services.Configurations;
import services.database.DBService;
import services.database.RegagroDBService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import steps.enterprise.AddEnterpriseSteps;
import steps.enterprise.EnterpriseCardSteps;
import steps.general.AuthSteps;
import steps.general.HomeSteps;
import steps.general.SidebarSteps;
import steps.lists.EnterpriseListSteps;

import java.util.Map;

import static datagenerator.DataGeneratorNames.getEnterpriseName;
import static enums.User.KAMERER;
import static enums.User.SUPER_ADMIN;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegistrationEnterpriseTest {
    private static final String name = getEnterpriseName();
    private static final String newName = getEnterpriseName();
    private static final EnterpriseFactory enterpriseFactory = new EnterpriseFactory();
    private static final Configurations configurations = new Configurations();
    private static final AddressFactory addressFactory = new AddressFactory();
    private static EnterpriseCardSteps enterpriseCardSteps;
    private static final HomeSteps homeSteps = new HomeSteps();
    private static Enterprise enterprise;
    private static RegagroDBService regagroDBService;
    private static Map<String, String> cookies;
    private static final User user = KAMERER;

    @BeforeAll
    static void setUpAll() {
        cookies = configurations.getCookiesMAP(user);
        configurations.setUpConfigurationsWithCookies(cookies);
    }

    @AfterAll
    static void tearDownAll() {
        configurations.clear();
    }

    @Tag("REGISTRATION_ENTERPRISE_OBJECT")
    @Tag("REGRESS")
    @Order(1)
    @DisplayName("RAT-2669 Регистрация площадки")
    @Test
    void regEnterprise() {
        Address address = addressFactory.createAddress(cookies, user);
        enterprise = enterpriseFactory.createEnterprise(name, address, user);

        RegagroDBService dbHelper = DBService.getRegagroDBService();
        SidebarSteps sidebarSteps = new SidebarSteps();
        AddEnterpriseSteps addEnterprisePage = sidebarSteps.getAddEnterprisePage();
        enterpriseCardSteps = addEnterprisePage.getNewEnterprise(enterprise, name);

        Assertions.assertTrue(enterpriseCardSteps.getNameOfEnterprise().contains(name));
        Assertions.assertTrue(dbHelper.isValueInDatabase("name", "enterprises", name));
    }

    @Tag("REGRESS")
    @Order(2)
    @DisplayName("RAT-2712 Редактирование площадки")
    @Test
    void getSuccessEditEnterprise() {
        enterpriseCardSteps = enterpriseCardSteps.editEnterpriseName(newName);
        RegagroDBService dbHelper = DBService.getRegagroDBService();

        Assertions.assertTrue(enterpriseCardSteps.getNameOfEnterprise().contains(newName));

        Assertions.assertTrue(dbHelper.isValueInDatabase("name", "enterprises", newName));
        // Assertions.assertFalse(dbHelper.isValueInDatabase("name", "enterprises", name));
    }

    @Tag("REGRESS")
    @Order(3)
    @DisplayName("RAT-3290 Удаление площадки")
    @Test
    void getSuccessDeleteEnterprise() {
        homeSteps.changeUser(SUPER_ADMIN);
        regagroDBService = DBService.getRegagroDBService();
        Selenide.open("https://v3.dev.regagro.ru/enterprises/" + regagroDBService.getId(newName, "enterprises"));
        EnterpriseListSteps enterpriseListSteps = enterpriseCardSteps.deleteEnterprise();
        regagroDBService = DBService.getRegagroDBService();
        Assertions.assertTrue(regagroDBService.isSoftDeleted("id", regagroDBService.getId(newName, "enterprises"), "enterprises"));
    }
}