package uitests.registration;

import assertions.AnimalRegistrationAssertions;
import enums.User;
import factory.AnimalFactory;
import org.junit.jupiter.api.BeforeEach;
import services.ConfigReader;
import services.Configurations;
import interfaces.Animals;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import services.database.DBService;
import services.database.RegagroDBService;
import steps.animal.AddAnimalSteps;
import steps.animal.AnimalPassportSteps;
import steps.general.SidebarSteps;

import static enums.User.KAMERER;
import static enums.User.VET_CHECHOV;

public class RegistrationIndividualKrsTest {
    private static final Configurations configurations = new Configurations();
    private final AnimalRegistrationAssertions animalRegistrationAssertions = new AnimalRegistrationAssertions();
    private final SidebarSteps sidebarSteps = new SidebarSteps();
    private AddAnimalSteps addAnimalSteps;
    private Animals krs;
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

    @Step("Создание животного")
    public void createAnimal() {
        AnimalFactory animalFactory = new AnimalFactory();
        krs = animalFactory.createAnimal("Крупный рогатый скот");
    }

    @Step("Открытие страницы регистрации")
    public void openAnimalRegistrationPage() throws InstantiationException, IllegalAccessException {
        addAnimalSteps = sidebarSteps.getAddAnimalPage();
    }

    @Step("Регистрация животного")
    public void registrationAnimal() {
        assert krs != null;
        addAnimalSteps.addIndividualAnimal(krs, supervisedObjectName);
        // Проверки, Ожидаемый результат
        Assertions.assertTrue(animalRegistrationAssertions.getSuccessMessage(krs.getIdentificationNumber()),
                "Отсутстсует сообщение об успешной регистрации");
//        Assertions.assertTrue(animalRegistrationAssertions.isAnimalAtDB(krs),
//                "Животное отсутствует в базе данных");
    }

    @Step("Переход в паспорт животного")
    public void goToAnimalPassportPage() {
        AnimalPassportSteps animalPassportSteps = addAnimalSteps.getAnimalPassportPage();
        // Проверки, Ожидаемый результат
        Assertions.assertTrue(animalRegistrationAssertions.isValueCorrect(animalPassportSteps.getIdentificationNumber(), krs.getIdentificationNumber()),
                "Идентификационный номер, указанный при регистрации: " + krs.getIdentificationNumber() + " не совпадает с номером в паспорте животного");
    }

    @Step("Переход в Реестр животных")
    public void goToAnimalRegistryPage() {
        sidebarSteps.getAnimalsListPage();
        // Проверки, Ожидаемый результат
        Assertions.assertTrue(animalRegistrationAssertions.isAnimalInAnimalRegistry(krs.getIdentificationNumber()),
                "Животное с номером: " + krs.getIdentificationNumber() + " не отображается в Реестре животных");
    }
    @Tag("ANIMAL_REGISTRATION")
    @Tag("REGRESS")
    @DisplayName("RAT-1948 Регистрация животного")
    @Test
    void regIndividualAnimals() throws InstantiationException, IllegalAccessException {
        createAnimal();
        openAnimalRegistrationPage();
        registrationAnimal();
//        goToAnimalPassportPage();
//        goToAnimalRegistryPage();
    }
}
