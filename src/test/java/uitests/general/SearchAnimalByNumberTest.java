package uitests.general;

import services.Configurations;
import services.database.DBService;
import services.database.RegagroDBService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import steps.animal.AnimalGroupPassportSteps;
import steps.animal.AnimalPassportSteps;
import steps.general.HomeSteps;

import static enums.User.SUPER_ADMIN;

public class SearchAnimalByNumberTest {
    private static final Configurations configurations = new Configurations();

    @BeforeAll
    static void setUpAll() {
        configurations.setUpConfigurationsApi(SUPER_ADMIN);
    }

    @AfterAll
    static void tearDownAll() {
        configurations.clear();
    }
    @Tag("REGRESS")
    @DisplayName("RAT-2691 Поиск животного по номеру")
    @Test
    void getSuccessAnimalSearchByNumber() {
        HomeSteps homeSteps = new HomeSteps();
        RegagroDBService dbHelper = DBService.getRegagroDBService();

        String randomAnimalNumber = dbHelper.getRandomAnimalNumberFromDB();
        AnimalPassportSteps animalPassportSteps = homeSteps.getFoundAnimal(randomAnimalNumber);
        Assertions.assertEquals(animalPassportSteps.getIdentificationNumber(), randomAnimalNumber);

        String randomAnimalGroupNumber = dbHelper.getRandomAnimalGroupNumberFromDB();
        AnimalGroupPassportSteps animalGroupPassportSteps = homeSteps.getFoundAnimalGroup(randomAnimalGroupNumber);
        Assertions.assertEquals(animalGroupPassportSteps.getIdentificationNumber(), randomAnimalGroupNumber);
    }
}
