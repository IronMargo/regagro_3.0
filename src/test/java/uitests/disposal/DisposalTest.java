package uitests.disposal;

import assertions.DisposalAssertions;
import enums.DisposalReasons;
import entities.disposals.DisposalList;
import factory.DisposalFactory;
import services.Configurations;
import io.qameta.allure.internal.shadowed.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import steps.disposal.CreateDisposalListSteps;
import steps.general.SidebarSteps;

import java.util.Map;

import static enums.User.VET_CHECHOV;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DisposalTest {
    private static final Configurations configurations = new Configurations();
    private final DisposalAssertions disposalAssertions = new DisposalAssertions();
    private static final DisposalFactory disposalFactory = new DisposalFactory();
    private final CreateDisposalListSteps createDisposalListSteps = new CreateDisposalListSteps();
    private static DisposalList disposalListForPersonalNeeds;
    private static String guid;
    private static Map<String, String> cookies;

    @BeforeAll
    static void setUpAll() throws JsonProcessingException {
        cookies = configurations.getCookiesMAP(VET_CHECHOV);
        configurations.setUpConfigurationsWithCookies(cookies);
        disposalListForPersonalNeeds = disposalFactory.createDisposalList(DisposalReasons.PERSONAL_NEEDS.getReason(), "\"Навуходоносор\"", cookies);
    }

    @AfterAll
    static void tearDownAll() {
        configurations.clear();
    }

    @Tag("DISPOSAL")
    @Tag("REGRESS")
    @Order(1)
    @DisplayName("RAT-1248 Зарегистрировать список выбытия по причине личные нужды")
    @Test
    void registrationDisposalListForPersonalNeeds() {
        SidebarSteps sidebarSteps = new SidebarSteps();
        sidebarSteps.getCreateDisposal();
        createDisposalListSteps.registrationDisposalList(disposalListForPersonalNeeds);

        Assertions.assertTrue(disposalAssertions.isEnterpriseAdded(disposalListForPersonalNeeds));
        Assertions.assertTrue(disposalAssertions.isSupervisedObjectAdded(disposalListForPersonalNeeds));

    }

    @Tag("DISPOSAL")
    @Tag("REGRESS")
    @Order(2)
    @DisplayName("RAT-1259 Выбор животного для выбытия")
    @Test
    void selectAnimalForDisposal() {
        createDisposalListSteps.addAnimalForDisposal(disposalListForPersonalNeeds);

        Assertions.assertTrue(disposalAssertions.isAnimalAdded(disposalListForPersonalNeeds));
    }

    @Tag("DISPOSAL")
    @Tag("REGRESS")
    @Order(3)
    @DisplayName("RAT-1264 Активация списка выбытия")
    @Test
    void activateDisposalList() {
        guid = createDisposalListSteps.getGuidOfDisposal();
        createDisposalListSteps.activateDisposalList(disposalListForPersonalNeeds.getReason());

        Assertions.assertTrue(disposalAssertions.isAnimalSoftDeleted(disposalListForPersonalNeeds));

    }

    @Tag("DISPOSAL")
    @Tag("REGRESS")
    @Order(4)
    @DisplayName("RAT-1265 Просмотр списка выбытия")
    @Test
    void viewingTheDisposalList() {

        Assertions.assertTrue(disposalAssertions.isActivateDateIsCurrent());
        Assertions.assertTrue(disposalAssertions.isEnterpriseAdded(disposalListForPersonalNeeds));
        Assertions.assertTrue(disposalAssertions.isSupervisedObjectAdded(disposalListForPersonalNeeds));
        Assertions.assertTrue(disposalAssertions.isAnimalAdded(disposalListForPersonalNeeds));
    }

    @Tag("DISPOSAL")
    @Tag("REGRESS")
    @Order(5)
    @DisplayName("Просмотр списка выбытия в Реестре выбытий")
    @Test
    void viewingAtDisposalsList() {
        createDisposalListSteps.turnToDisposalsList();

        Assertions.assertTrue(disposalAssertions.isDisposalAtList(guid));
    }
}
