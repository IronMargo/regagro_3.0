package uitests.disposal;

import assertions.DisposalAssertions;
import enums.DisposalReasons;
import entities.disposals.DisposalList;
import factory.DisposalFactory;
import services.Configurations;
import services.database.DBService;
import services.database.RegagroDBService;
import io.qameta.allure.Step;
import io.qameta.allure.internal.shadowed.jackson.core.JsonProcessingException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import steps.animal.AnimalGroupPassportSteps;
import steps.general.HomeSteps;

import java.util.Map;
import java.util.stream.Stream;

import static enums.User.VET_CHECHOV;

public class DisposalFromGroupTest {
    private static final Configurations configurations = new Configurations();
    private AnimalGroupPassportSteps animalGroupPassportSteps;
    private DisposalList disposalList;
    private final SoftAssertions softAssertions = new SoftAssertions();
    private String animalGroupNumber;
    private int initialCount;
    private static Map<String, String> cookies;
    private final String enterpriseName = "\"Навуходоносор\"";
    private String countForDisposal;

    @BeforeAll
    static void setUp() {
        cookies = configurations.getCookiesMAP(VET_CHECHOV);
        configurations.setUpConfigurationsWithCookies(cookies);
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
    @DisplayName("Выбытие из простой группы без регистрации")
    @ParameterizedTest
    @MethodSource("disposalFinishReasons")
    void getSuccessDisposalFromAnimalPassport(DisposalReasons disposalReason) throws JsonProcessingException {
        countForDisposal = "1";
        getAnimalPassportPage(disposalReason.getReason());
        generateDataForDisposal(disposalReason);
        activateDisposalListFromAnimalGroup();
        DisposalAssertions disposalAssertions = new DisposalAssertions();
        softAssertions.assertThat(disposalAssertions.isDisposalFromGroupSuccess(disposalList.getAnimalNumber(), cookies, initialCount, countForDisposal))
                .as("Количество животных в группе уменьшилось на кол-во выбывших")
                .isTrue();
        softAssertions.assertThat(disposalAssertions.isDisposalAnimalAtAnimalTerminatedList(animalGroupNumber, disposalList.getAnimalId()))
                .as("Животное с таким же номером считается окончательно выбывшим животным")
                .isTrue();
        softAssertions.assertAll();
    }

    @Step("Переход в Пасспорт группы животных")
    public void getAnimalPassportPage(String disposalReason) {
        HomeSteps homeSteps = new HomeSteps();
        RegagroDBService dbHelper = DBService.getRegagroDBService();
        animalGroupNumber = dbHelper.getAnimalGroupNumberFromEnterprise(enterpriseName, disposalReason);
        animalGroupPassportSteps = homeSteps.getFoundAnimalGroup(animalGroupNumber);
    }

    @Step("Генерация данных для вывода из группы")
    public void generateDataForDisposal(DisposalReasons disposalReason) throws JsonProcessingException {
        DisposalFactory disposalFactory = new DisposalFactory();
        disposalList = disposalFactory.createDisposalListFromGroup(disposalReason.getReason(), animalGroupNumber, enterpriseName, cookies);
        initialCount = disposalList.getInitialCount();

    }

    @Step("Активация листа выбытия путем вывода из простой группы")
    public void activateDisposalListFromAnimalGroup() {
        animalGroupPassportSteps.disposeAnimalFromGroupWithDisposal(disposalList, countForDisposal);
    }

    static Stream<DisposalReasons> disposalFinishReasons() {
        return Stream.of(DisposalReasons.MURRIAN, DisposalReasons.LOST, DisposalReasons.FACT_KILL, DisposalReasons.FORCED_KILL);
    }
}

