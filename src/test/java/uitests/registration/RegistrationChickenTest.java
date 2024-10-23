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
import steps.animal.AnimalPassportSteps;
import steps.general.SidebarSteps;
import steps.lists.AnimalsListSteps;

import static enums.User.KAMERER;
import static enums.User.VET_CHECHOV;


public class RegistrationChickenTest {

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
    @DisplayName("RAT-2768 Регистрация птиц")
    @Test
    void regChicken() {
        RegagroDBService dbHelper = DBService.getRegagroDBService();
        AnimalFactory animalFactory = new AnimalFactory();
        Animals chicken = animalFactory.createAnimal("Куры");

        SidebarSteps sidebarSteps = new SidebarSteps();

        AddAnimalSteps registrationAnimalSteps = sidebarSteps.getAddAnimalPage();
        assert chicken != null;
        registrationAnimalSteps.addIndividualAnimal(chicken, supervisedObjectName);

        animalRegistrationAssertions.getSuccessMessage(chicken.getIdentificationNumber());
        Assertions.assertTrue(dbHelper.isValueInDatabase("number", "animals", chicken.getIdentificationNumber()),
                "Животное отсутствует в базе данных");
        AnimalPassportSteps animalPassportSteps = registrationAnimalSteps.getAnimalPassportPage();
        Assertions.assertEquals(chicken.getIdentificationNumber(), animalPassportSteps.getIdentificationNumber(),
                "Идентификационный номер, указанный при регистрации: " + chicken.getIdentificationNumber() + " не совпадает с номером в паспорте животного");

        AnimalsListSteps animalsListPage = sidebarSteps.getAnimalsListPage();
        Assertions.assertTrue(animalsListPage.isAnimalInList(chicken.getIdentificationNumber()),
                "Животное с номером: " + chicken.getIdentificationNumber() + " не отображается в Реестре животных");
    }
}
