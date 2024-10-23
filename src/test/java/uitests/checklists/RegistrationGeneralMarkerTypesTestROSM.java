package uitests.checklists;

import assertions.AnimalRegistrationAssertions;
import datagenerator.DataGeneratorForCheckLists;
import enums.User;
import factory.AnimalFactory;
import interfaces.Animals;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import services.ConfigReader;
import services.Configurations;
import services.database.DBService;
import services.database.RegagroDBService;
import steps.animal.AddAnimalSteps;
import steps.general.SidebarSteps;

import java.util.stream.Stream;

import static enums.User.KAMERER;

@Epic("Регистрация основными СМ. Не эмиссионные")
public class RegistrationGeneralMarkerTypesTestROSM {
    private static final Configurations configurations = new Configurations();
    private final AnimalFactory animalFactory = new AnimalFactory();
    private final AnimalRegistrationAssertions animalRegistrationAssertions = new AnimalRegistrationAssertions();
    private static final DataGeneratorForCheckLists dataGeneratorForCheckLists = new DataGeneratorForCheckLists();
    private static String supervisedObjectName;
    private SidebarSteps sidebarSteps;
    private Animals animal;
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
    @DisplayName("Успешная индивидуальная регистрация")
    @ParameterizedTest()
    @MethodSource("generateAnimalDataPositive")
    void positiveRegistration(String kind, String markerType, String number, String date) {
        animalRegistration("Индивидуальная", kind, markerType, number, date);
        Assertions.assertTrue(animalRegistrationAssertions.getSuccessMessage(number));
    }

    @Tag("ANIMAL_REGISTRATION")
    @Tag("REGRESS")
    @DisplayName("Успешная групповая регистрация")
    @ParameterizedTest()
    @MethodSource("generateAnimalGroupDataPositive")
    void positiveGroupRegistration(String kind, String markerType, String number, String date) {
        animalRegistration("Групповая", kind, markerType, number, date);
        Assertions.assertTrue(animalRegistrationAssertions.getSuccessMessage(number));
    }

    @Tag("ANIMAL_REGISTRATION")
    @Tag("REGRESS")
    @DisplayName("Ошибка валидации при индивидуальной регистрации")
    @ParameterizedTest()
    @MethodSource("generateAnimalDataNegative")
    void negativeIndividualRegistration(String kind, String markerType, String number, String date) {
        animalRegistration("Индивидуальная", kind, markerType, number, date);
        Assertions.assertTrue(animalRegistrationAssertions.getErrorMessage());
    }

    @Tag("ANIMAL_REGISTRATION")
    @Tag("REGRESS")
    @DisplayName("Регистрация с некорректным номером")
    @ParameterizedTest()
    @MethodSource("generateAnimalDataNegativeButRegistered")
    void negativeButRegistration(String kind, String markerType, String number, String date) {
        animalRegistration("Индивидуальная", kind, markerType, number, date);
        Assertions.assertTrue(animalRegistrationAssertions.getUnCorrectRegistration(number));
    }

    @Tag("ANIMAL_REGISTRATION")
    @Tag("REGRESS")
    @DisplayName("Ошибка валидации при групповой регистрации")
    @ParameterizedTest()
    @MethodSource("generateAnimalGroupDataNegative")
    void negativeGroupRegistration(String kind, String markerType, String number, String date) {
        animalRegistration("Групповая", kind, markerType, number, date);
        Assertions.assertTrue(animalRegistrationAssertions.getErrorMessage());
    }

    @Tag("ANIMAL_REGISTRATION")
    @Tag("REGRESS")
    @DisplayName("Успешная регистрация с учетом СМ и вида животного (список осм после 1.03)")
    @ParameterizedTest()
    @MethodSource("generateAnimalDataMarkerTypesPositive")
    void positiveRegistrationKindsAndMarkerTypes(String kind, String markerType, String number, String date) {
        animalRegistration("Индивидуальная", kind, markerType, number, date);
        Assertions.assertTrue(animalRegistrationAssertions.getSuccessMessage(number));
    }

    // Регистрация животного с учетом типа регистрации (индивидуальная или групповая)
    void animalRegistration(String typeOfRegistration, String kind, String markerType, String number, String date) {
        sidebarSteps = new SidebarSteps();
        if (typeOfRegistration.equals("Индивидуальная")) {
            animal = animalFactory.createAnimal(kind, markerType, number, date);
            AddAnimalSteps animalSteps = sidebarSteps.getAddAnimalPage();
            animalSteps.addIndividualAnimal(animal, supervisedObjectName);
        } else {
            animal = animalFactory.createAnimalGroup(kind, markerType, number, date);
            AddAnimalSteps animalSteps = sidebarSteps.getAddAnimalGroupPage();
            animalSteps.addAnimalGroup(animal, supervisedObjectName);
        }
    }

    // Генрация данных, передаваемых в параметризованный тест
    static Stream<Arguments> generateAnimalDataPositive() {
        return dataGeneratorForCheckLists.generateAnimalData("Индивидуальная регистрация. Валидные данные", supervisedObjectName)
                .map(animalData -> Arguments.of(
                        animalData.getKind(), animalData.getMarkerType(), animalData.getNumber(), animalData.getMarkerDate()));
    }

    static Stream<Arguments> generateAnimalDataNegative() {
        return dataGeneratorForCheckLists.generateAnimalData("Индивидуальная регистрация. Неалидные данные. Ошибка валидации", supervisedObjectName)
                .map(animalData -> Arguments.of(
                        animalData.getKind(), animalData.getMarkerType(), animalData.getNumber(), animalData.getMarkerDate()));
    }

    static Stream<Arguments> generateAnimalDataNegativeButRegistered() {
        return dataGeneratorForCheckLists.generateAnimalData("Индивидуальная регистрация. Невалидные данные с некорректной регистрацией", supervisedObjectName)
                .map(animalData -> Arguments.of(
                        animalData.getKind(), animalData.getMarkerType(), animalData.getNumber(), animalData.getMarkerDate()));
    }

    static Stream<Arguments> generateAnimalGroupDataPositive() {
        return dataGeneratorForCheckLists.generateAnimalData("Групповая регистрация. Валидные данные", supervisedObjectName)
                .map(animalData -> Arguments.of(
                        animalData.getKind(), animalData.getMarkerType(), animalData.getNumber(), animalData.getMarkerDate()));
    }

    static Stream<Arguments> generateAnimalGroupDataNegative() {
        return dataGeneratorForCheckLists.generateAnimalData("Групповая регистрация. Неалидные данные", supervisedObjectName)
                .map(animalData -> Arguments.of(
                        animalData.getKind(), animalData.getMarkerType(), animalData.getNumber(), animalData.getMarkerDate()));
    }

    static Stream<Arguments> generateAnimalDataMarkerTypesPositive() {
        return dataGeneratorForCheckLists.generateAnimalData("Регистрация основными СМ для видов животных", supervisedObjectName)
                .map(animalData -> Arguments.of(
                        animalData.getKind(), animalData.getMarkerType(), animalData.getNumber(), animalData.getMarkerDate()));
    }
}
