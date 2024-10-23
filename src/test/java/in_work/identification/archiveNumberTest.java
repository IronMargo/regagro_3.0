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

import static enums.User.SUPER_ADMIN;

public class archiveNumberTest {
    private static final Configurations configurations = new Configurations();
    private final String enterpriseName = "\"Навуходоносор\"";
    private final AnimalPassportAssertions animalPassportAssertions = new AnimalPassportAssertions();
    private final IdentificationAssertions identificationAssertions = new IdentificationAssertions();
    private AnimalPassportSteps animalPassportSteps;
    private IdentificationSteps identificationSteps;
    private final HomeSteps homeSteps = new HomeSteps();
    private String animalNumber;

    @BeforeAll
    static void setup() {
        configurations.setUpConfigurationsApi(SUPER_ADMIN);
    }

    //    @BeforeEach
//    void changeRole(String role) {
//        basePage.changeRole(role);
//    }
    @Step("Открыть паспорт животного")
    void getRandomAnimalPassport() {
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

    @Step("Архивировать номер")
    void getArchiveNumber() {
        identificationSteps.archivateNumber(animalNumber);
    }

//    @Test
//    void archiveNumber(){
//        getRandomAnimalPassport();
//        getIdentificationPage(User.SUPER_ADMIN.getRole());
//        getArchiveNumber();
//    }

}
