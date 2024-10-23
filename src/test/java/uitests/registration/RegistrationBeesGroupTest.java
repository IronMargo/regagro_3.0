package uitests.registration;

import assertions.AnimalRegistrationAssertions;
import enums.User;
import factory.AnimalFactory;
import org.junit.jupiter.api.BeforeEach;
import services.ConfigReader;
import services.Configurations;
import services.database.DBService;
import services.database.RegagroDBService;
import interfaces.Animals;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import steps.animal.AddAnimalSteps;
import steps.animal.AnimalGroupPassportSteps;
import steps.general.SidebarSteps;
import steps.lists.AnimalsListSteps;

import static enums.User.KAMERER;
import static enums.User.VET_CHECHOV;


public class RegistrationBeesGroupTest {

    AnimalRegistrationAssertions animalRegistrationAssertions = new AnimalRegistrationAssertions();
    private static final Configurations configurations = new Configurations();
    private String supervisedObjectName;
    private static final User user = KAMERER;

    @BeforeAll
    static void setUpAll() {
        configurations.setUpConfigurationsApi(user);
    }
    @BeforeEach
    void getStartPage() {
        configurations.setStartPage();
        // supervisedObjectName = "НКО Владимир";
        RegagroDBService regagroDBService = DBService.getRegagroDBService();
        supervisedObjectName = regagroDBService.getSupervisedObjectsOfUser(ConfigReader.getUserEmail(user.getRole()));
    }
    @AfterAll
    static void tearDownAll() {
        configurations.clear();
    }
    @Tag("ANIMAL_REGISTRATION")
    @Tag("REGRESS")
    @DisplayName("RAT-919 Регистрация пасеки")
    @Test
    void regAnimalGroupBees() {
        RegagroDBService dbHelper = DBService.getRegagroDBService();
        AnimalFactory animalFactory = new AnimalFactory();
        Animals bees = animalFactory.createAnimalGroup("Пчёлы");

        SidebarSteps sidebarSteps = new SidebarSteps();

        AddAnimalSteps registrationAnimalSteps = sidebarSteps.getAddAnimalGroupPage();
        assert bees != null;
        registrationAnimalSteps.addAnimalGroup(bees, supervisedObjectName);
        animalRegistrationAssertions.getSuccessMessage(bees.getIdentificationNumber());
        Assertions.assertTrue(dbHelper.isValueInDatabase("number", "animals", bees.getIdentificationNumber()),
                "Животное отсутствует в базе данных");

        registrationAnimalSteps.getAnimalGroupPassportPage();
        AnimalGroupPassportSteps animalGroupPassportPage = new AnimalGroupPassportSteps();
        Assertions.assertEquals(bees.getIdentificationNumber(), animalGroupPassportPage.getIdentificationNumber(),
                "Идентификационный номер, указанный при регистрации: " + bees.getIdentificationNumber() + " не совпадает с номером в паспорте животного");

        AnimalsListSteps animalsListPage = sidebarSteps.getAnimalsListPage();
        Assertions.assertTrue(animalsListPage.isAnimalInList(bees.getIdentificationNumber()),
                "Животное с номером: " + bees.getIdentificationNumber() + " не отображается в Реестре животных");
    }
}
