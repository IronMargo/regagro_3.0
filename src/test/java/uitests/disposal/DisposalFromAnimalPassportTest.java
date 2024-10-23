package uitests.disposal;

import assertions.DisposalAssertions;
import enums.DisposalReasons;
import services.Configurations;
import services.database.DBService;
import services.database.RegagroDBService;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import steps.animal.AnimalPassportSteps;
import steps.disposal.CreateDisposalListSteps;
import steps.general.HomeSteps;

import static enums.User.VET_CHECHOV;

public class DisposalFromAnimalPassportTest {
    private static final Configurations configurations = new Configurations();
    private final SoftAssertions softAssertions = new SoftAssertions();
    private final DisposalAssertions disposalAssertions = new DisposalAssertions();
    private static final String enterpriseName = "\"Навуходоносор\"";

    @BeforeAll
    static void setUp() {
        configurations.setUpConfigurationsApi(VET_CHECHOV);
    }

    @BeforeEach
    void getStartPage() {
        configurations.setStartPage();
    }

    @AfterAll
    static void tearDownAll() {
        configurations.clear();
    }

    @Tag("DISPOSAL")
    @Tag("REGRESS")
    @DisplayName("Выбытие из Паспорта животного")
    @Test
    void getSuccessDisposalFromAnimalPassport() {
        HomeSteps homeSteps = new HomeSteps();
        RegagroDBService dbHelper = DBService.getRegagroDBService();
        String number = dbHelper.getAnimalNumberFromEnterprise(enterpriseName);
        AnimalPassportSteps animalPassportSteps = homeSteps.getFoundAnimal(number);
        CreateDisposalListSteps createDisposalListSteps = animalPassportSteps.disposalAnimalFromPassport();
        createDisposalListSteps.getActivatedDisposalFromAnimalPassport(DisposalReasons.PERSONAL_NEEDS);
        String guid = createDisposalListSteps.getGuidOfDisposal();

        softAssertions.assertThat(disposalAssertions.isActivateDateIsCurrent())
                .as("Дата активации равна текущей")
                .isTrue();
        softAssertions.assertThat(disposalAssertions.isAnimalSoftDeletedByNumber(number))
                .as("Животное удалено")
                .isTrue();
        createDisposalListSteps.turnToDisposalsList();
        softAssertions.assertThat(disposalAssertions.isDisposalAtList(guid))
                .as("Лист выбытия присутствует в Реестре выбытий")
                .isTrue();
        softAssertions.assertAll(); // проверяем все утверждения
    }
}
