package uitests.disposal;

import assertions.DisposalAssertions;
import enums.DisposalReasons;
import entities.disposals.DisposalList;
import factory.DisposalFactory;
import services.Configurations;
import io.qameta.allure.Step;
import io.qameta.allure.internal.shadowed.jackson.core.JsonProcessingException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import steps.disposal.CreateDisposalListSteps;
import steps.general.SidebarSteps;

import java.util.Map;
import java.util.stream.Stream;

import static enums.User.VET_CHECHOV;

public class DisposalFinishReasonsTest {
    private static final Configurations configurations = new Configurations();
    private final DisposalAssertions disposalAssertions = new DisposalAssertions();
    private static final DisposalFactory disposalFactory = new DisposalFactory();
    private CreateDisposalListSteps createDisposalListSteps;
    private DisposalList disposalList;
    private final SoftAssertions softAssertions = new SoftAssertions();
    private static Map<String, String> cookies;
    private String guid;

    @BeforeAll
    static void setUpAll() {
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
    @DisplayName("Регистрирация списка выбытия")
    @ParameterizedTest(name = "Регистрация списка выбытия для типа {0}")
    @MethodSource("disposalReasons")
    void registrationDisposalListForMurrain(DisposalReasons disposalReason) throws JsonProcessingException {
        getRegistrationDisposalPage();
        generateDataForDisposal(disposalReason);
        getRegisteredDisposalList(disposalList);
        addAnimalAtDisposalList(disposalList);
        getActivatedDisposalList();

        softAssertions.assertThat(disposalAssertions.isOnDisposalListPage())
                .as("Список выбытия активирован")
                .isTrue();

        guid = createDisposalListSteps.getGuidOfDisposal();

        softAssertions.assertThat(disposalAssertions.isEnterpriseAdded(disposalList))
                .as("ПП добавлена")
                .isTrue();
        softAssertions.assertThat(disposalAssertions.isSupervisedObjectAdded(disposalList))
                .as("ПО добавлено")
                .isTrue();
        softAssertions.assertThat(disposalAssertions.isAnimalAdded(disposalList))
                .as("Животное добавлено")
                .isTrue();
        softAssertions.assertThat(disposalAssertions.isActivateDateIsCurrent())
                .as("Дата активации равна текущей")
                .isTrue();
        if (disposalReason.getReason().equals("Падеж") || disposalReason.getReason().equals("Личные нужды") ||
                disposalReason.getReason().equals("Фактический убой") || disposalReason.getReason().equals("Вынужденный убой") ||
                disposalReason.getReason().equals("Выбытие за пределы РФ") || disposalReason.getReason().equals("Утеряно") ||
                disposalReason.getReason().equals("Направление на убой")) {
            softAssertions.assertThat(disposalAssertions.isAnimalSoftDeleted(disposalList))
                    .as("Животное удалено")
                    .isTrue();
        } else if (disposalReason.getReason().equals("Продажа") || disposalReason.getReason().equals("Временное перемещение") ||
                disposalReason.getReason().equals("Перемещение между объектами владельца")) {
            softAssertions.assertThat(disposalAssertions.isAnimalAtSupervisedObjectTo(disposalList))
                    .as("Животное находится на объекте назначения")
                    .isTrue();
            softAssertions.assertThat(disposalAssertions.isAnimalNotAtSupervisedObjectFrom(disposalList))
                    .as("Животное отсутствует на объекте выбытия")
                    .isTrue();
        }
        turnToDisposalsList();

        softAssertions.assertThat(disposalAssertions.isDisposalAtList(guid))
                .as("Лист выбытия присутствует в Реестре выбытий")
                .isTrue();

        softAssertions.assertAll();
    }

    static Stream<DisposalReasons> disposalReasons() {
        return Stream.of(DisposalReasons.MURRIAN, DisposalReasons.LOST, DisposalReasons.PERSONAL_NEEDS, DisposalReasons.FACT_KILL, DisposalReasons.FORCED_KILL, DisposalReasons.OUTSIDE_THE_RF,
                DisposalReasons.SALE, DisposalReasons.MOVING_BETWEEN_OWNERS_OBJECTS, DisposalReasons.TEMPORARY_MOVEMENT, DisposalReasons.DIRECTION_FOR_KILL);
    }

    @Step("Переход на страницу регистрации выбытия")
    void getRegistrationDisposalPage() {
        SidebarSteps sidebarSteps = new SidebarSteps();
        sidebarSteps.getCreateDisposal();
        Assertions.assertTrue(disposalAssertions.isOnCreateDisposalPage());
    }

    @Step("Генерация данных для создания списка выбытия")
    void generateDataForDisposal(DisposalReasons disposalReason) throws JsonProcessingException {
        createDisposalListSteps = new CreateDisposalListSteps();
        disposalList = disposalFactory.createDisposalList(disposalReason.getReason(), "\"Навуходоносор\"", cookies);
        Assertions.assertNotNull(disposalList, "Ошибка при генерации данных для листа выбытия");
    }

    @Step("Регистрация списка выбытия")
    void getRegisteredDisposalList(DisposalList disposalList) {
        createDisposalListSteps.registrationDisposalList(disposalList);
        disposalAssertions.isOnDisposalCardPage();
    }

    @Step("Добавление животного в список выбытия")
    void addAnimalAtDisposalList(DisposalList disposalList) {
        createDisposalListSteps.addAnimalForDisposal(disposalList);
        disposalAssertions.isEnterpriseAdded(disposalList);
        disposalAssertions.isSupervisedObjectAdded(disposalList);
        disposalAssertions.isAnimalAdded(disposalList);
    }

    @Step("Активация списка выбытия")
    void getActivatedDisposalList() {
        createDisposalListSteps.activateDisposalList(disposalList.getReason());
    }

    @Step("Переход в Реестр выбытий")
    void turnToDisposalsList() {
        createDisposalListSteps.turnToDisposalsList();
    }

}


