package in_work.identification;

import assertions.AnimalPassportAssertions;
import assertions.IdentificationAssertions;
import services.Configurations;
import services.database.DBService;
import services.database.RegagroDBService;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import steps.animal.AnimalPassportSteps;
import steps.animal.IdentificationSteps;
import steps.general.HomeSteps;

import static enums.User.VET_CHECHOV;

public class AddNumberTest {
    private static Configurations configurations = new Configurations();
    private String animalNumber;
    private HomeSteps homeSteps;
    private AnimalPassportSteps animalPassportSteps;
    private IdentificationSteps identificationSteps;
    private final AnimalPassportAssertions animalPassportAssertions = new AnimalPassportAssertions();
    private final IdentificationAssertions identificationAssertions = new IdentificationAssertions();

    @BeforeAll()
    static void setUp() {
        configurations.setUpConfigurationsApi(VET_CHECHOV);
    }

    @Step("Открыть паспорт животного")
    void getRandomAnimalPassport() {
        homeSteps = new HomeSteps();
        RegagroDBService dbHelpers = DBService.getRegagroDBService();
//        animalNumber = dbHelpers.getAnimalNumberFromEnterprise(enterpriseName);
        animalNumber = "212121121211";
        animalPassportSteps = homeSteps.getFoundAnimal(animalNumber);
        Assertions.assertTrue(animalPassportAssertions.isOnAnimalPassportPage(animalNumber));
    }

    @Step("Открыть страницу Индентификация")
    void getIdentificationPage(String userRole) {
        identificationSteps = animalPassportSteps.getIdentificationSteps();
        Assertions.assertTrue(identificationAssertions.isOnIdentificationPage(userRole));
    }

    @Step("Сгенерировать данные нового номера")
    void generateNewNumberData() {

    }

    @Step("Добавить новый номер")
    void addNewNumber() {

    }

    @Step("Сохранить добавленный номер")
    void saveNumber() {

    }
}
