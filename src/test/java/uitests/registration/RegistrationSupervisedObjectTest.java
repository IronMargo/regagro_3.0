package uitests.registration;

import com.codeborne.selenide.Selenide;
import entities.objects_enterprises.SupervisedObject;
import enums.User;
import factory.SupervisedObjectFactory;
import org.junit.jupiter.api.RepeatedTest;
import services.ConfigReader;
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
import steps.general.AuthSteps;
import steps.general.HomeSteps;
import steps.general.SidebarSteps;
import steps.supervisedObject.AddSupervisedObjectSteps;
import steps.supervisedObject.SupervisedObjectCardSteps;

import static datagenerator.DataGeneratorNames.getEnterpriseName;
import static enums.User.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegistrationSupervisedObjectTest {
    private RegagroDBService regagroDBService = DBService.getRegagroDBService();
    private static final String name = getEnterpriseName();
    private static final String newName = getEnterpriseName();
    private final SupervisedObjectFactory supervisedObjectFactory = new SupervisedObjectFactory();
    private static SupervisedObject supervisedObject;
    private static SupervisedObjectCardSteps supervisedObjectCardSteps;
    private static final Configurations configurations = new Configurations();
    private static final HomeSteps homeSteps = new HomeSteps();
    private static User user;
    private String enterpriseName;
    //private final String enterpriseName = "Навуходоносор";
    @BeforeAll
    static void setUpAll() {
        user = KAMERER;
        configurations.setUpConfigurationsApi(user);
    }

    @AfterAll
    static void tearDownAll() {
        configurations.clear();
    }

    @Tag("REGISTRATION_ENTERPRISE_OBJECT")
    @Tag("REGRESS")
    @Order(1)
    @DisplayName("RAT-2669 Регистрация объекта")
    @Test
    void regSupervisedObject() {
        enterpriseName = regagroDBService.getEnterprisesOfUser(ConfigReader.getUserEmail(user.getRole()));
        //enterpriseName = "Навуходоносор";
        supervisedObject = supervisedObjectFactory.createSupervisedObject(name, enterpriseName);
        RegagroDBService dbHelper = DBService.getRegagroDBService();
        SidebarSteps sidebarSteps = new SidebarSteps();
        AddSupervisedObjectSteps addSupervisedObjectSteps = sidebarSteps.getAddSupervisedObjectPage();
        addSupervisedObjectSteps.getNewSupervisedObject(supervisedObject, name);
        supervisedObjectCardSteps = new SupervisedObjectCardSteps();

        Assertions.assertTrue(supervisedObjectCardSteps.getNameOfSupervisedObject().contains(name));
        Assertions.assertTrue(dbHelper.isValueInDatabase("name", "supervised_objects", name));
    }

    @Tag("REGRESS")
    @Order(2)
    @DisplayName("RAT-2712 Редактирование объекта")
    @Test
    void getSuccessEditSupervisedObject() {
        supervisedObjectCardSteps = supervisedObjectCardSteps.editSupervisedObjectName(newName);
        regagroDBService = DBService.getRegagroDBService();
        Assertions.assertTrue(supervisedObjectCardSteps.getNameOfSupervisedObject().contains(newName));
        Assertions.assertTrue(regagroDBService.isValueInDatabase("name", "supervised_objects", newName));
//        Assertions.assertFalse(dbHelper.isValueInDatabase("name", "supervised_objects", name));
    }

    @Tag("REGRESS")
    @Order(3)
    @DisplayName("RAT-3290 Удаление объекта")
    @Test
    void getSuccessDeleteSupervisedObject() {
        homeSteps.changeUser(SUPER_ADMIN);
        regagroDBService = DBService.getRegagroDBService();
        Selenide.open("https://v3.dev.regagro.ru/supervised_objects/" + regagroDBService.getId(newName, "supervised_objects"));
        supervisedObjectCardSteps.deleteSupervisedObject();
        regagroDBService = DBService.getRegagroDBService();
        Assertions.assertTrue(regagroDBService.isSoftDeleted("id", regagroDBService.getId(newName, "supervised_objects"), "supervised_objects"));
    }
}