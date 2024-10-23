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
import steps.general.SidebarSteps;

import static enums.User.KAMERER;
import static enums.User.VET_CHECHOV;

public class RegistrationAnimalGroupPigTest {
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
    @DisplayName("RAT-1948 Регистрация группы животных (свиньи)")
    @Test
    void regAnimalGroup() {
        RegagroDBService regagroDBService = DBService.getRegagroDBService();
        AnimalFactory animalFactory = new AnimalFactory();
        Animals pigs = animalFactory.createAnimalGroup("Свиньи");

        SidebarSteps sidebarSteps = new SidebarSteps();

        AddAnimalSteps registrationAnimalSteps = sidebarSteps.getAddAnimalGroupPage();
        assert pigs != null;
        registrationAnimalSteps.addAnimalGroup(pigs, supervisedObjectName);
        animalRegistrationAssertions.getSuccessMessage(pigs.getIdentificationNumber());
        Assertions.assertTrue(regagroDBService.isValueInDatabase("number", "animals", pigs.getIdentificationNumber()),
                "Животное отсутствует в базе данных");

//        AnimalGroupPassportSteps animalGroupPassportSteps = registrationAnimalSteps.getAnimalGroupPassportPage();
//
//        Assertions.assertEquals(pigs.getIdentificationNumber(), animalGroupPassportPage.getIdentificationNumber(),
//                "Идентификационный номер, указанный при регистрации: " + pigs.getIdentificationNumber() + " не совпадает с номером в паспорте животного");
//
//        AnimalsListPage animalsListPage = basePage.getAnimalsListPage();
//        Assertions.assertTrue(animalsListPage.isAnimalInList(pigs.getIdentificationNumber()),
//                "Животное с номером: " + pigs.getIdentificationNumber() + " не отображается в Реестре животных");
    }
}