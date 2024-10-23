package assertions;

import entities.disposals.DisposalList;
import services.AnimalResponseService;
import services.database.DBService;
import services.database.RegagroDBService;
import io.qameta.allure.internal.shadowed.jackson.core.JsonProcessingException;
import org.assertj.core.api.SoftAssertions;
import pages.disposal.CreateDisposalListPage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class DisposalAssertions {
    private final CreateDisposalListPage locators = new CreateDisposalListPage();
    private final SoftAssertions softAssertions = new SoftAssertions();
    private final AnimalResponseService animalResponseService = new AnimalResponseService();
    private RegagroDBService dbHelper;

    public boolean isOnCreateDisposalPage() {
        locators.headerRegistrationDisposal.should(appear);
        softAssertions.assertThat(locators.headerRegistrationDisposal.isDisplayed())
                .as("Отображается заголовок 'Регистрация списка выбытия'")
                .isTrue();
        locators.disposalReasonSelect.should(appear);
        softAssertions.assertThat(locators.disposalReasonSelect.isDisplayed())
                .as("Отображается поле 'Причина выбытия'")
                .isTrue();
        locators.supervisedObjectFromButton.should(appear);
        softAssertions.assertThat(locators.supervisedObjectFromButton.isDisplayed())
                .as("Отображается поле 'Объект выбытия'")
                .isTrue();
        locators.supervisedObjectToButton.should(appear);
        softAssertions.assertThat(locators.supervisedObjectToButton.isDisplayed())
                .as("Отображается поле 'Объект назначения'")
                .isTrue();
        locators.animalKindSelect.should(appear);
        softAssertions.assertThat(locators.animalKindSelect.isDisplayed())
                .as("Отображается поле 'Вид животного'")
                .isTrue();
        softAssertions.assertAll();
        return true;
    }

    public boolean isOnDisposalCardPage() {
        locators.headerDisposalCard.should(appear);
        $x("//div[contains(text(), 'Вид животного')]/following-sibling::div//span[@class='select2-selection select2-selection--single select2-selection--clearable']")
                .should(appear);
        softAssertions.assertThat($x("//div[contains(text(), 'Вид животного')]/following-sibling::div//span[@class='select2-selection select2-selection--single select2-selection--clearable']")
                        .isDisplayed())
                .as("Отображается наименование ПП")
                .isTrue();
        softAssertions.assertAll();
        return true;
    }

    public boolean isEnterpriseAdded(DisposalList disposalList) {
        $x("//div[contains(text(),'Наименование площадки')]/following-sibling::h4").should(appear);
        softAssertions.assertThat($x("//div[contains(text(),'Наименование площадки')]/following-sibling::h4")
                        .getText().contains(disposalList.getEnterpriseFrom()))
                .as("Отображается наименование ПП")
                .isTrue();
        softAssertions.assertAll();
        return true;
    }

    public boolean isSupervisedObjectAdded(DisposalList disposalList) {
        $x("//div[contains(text(),'Наименование поднадзорного объекта')]/following-sibling::h4").should(appear);
        softAssertions.assertThat(disposalList.getSupervisedObjectFrom().contains($x("//div[contains(text(),'Наименование поднадзорного объекта')]/following-sibling::h4").getText()))
                .as("Отображается наименование ПО")
                .isTrue();
        softAssertions.assertAll();
        return true;
    }

    public boolean isAnimalAdded(DisposalList disposalList) {
        $x("//div[contains(text(), 'Номер средства маркирования')]/following-sibling::div").should(appear);
        softAssertions.assertThat($x("//div[contains(text(), 'Номер средства маркирования')]/following-sibling::div").getText().contains(disposalList.getAnimalNumber()))
                .as("Выбранное животное добавлено")
                .isTrue();
        softAssertions.assertAll();
        return true;
    }

    public boolean isOnDisposalListPage() {
        locators.headerDisposalList.should(appear);
        softAssertions.assertThat(locators.headerDisposalList.isDisplayed())
                .as("Отображается заголовок 'Список выбытия'")
                .isTrue();
        $x("//div[contains(text(), 'Дата активации списка')]/input").should(appear);
        softAssertions.assertThat(Objects.requireNonNull($x("//div[contains(text(), 'Дата активации списка')]/input").getAttribute("value"))
                        .contains(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))))
                .as("Дата активации равна текущей дате")
                .isTrue();
        softAssertions.assertAll();
        return true;
    }

    public boolean isActivateDateIsCurrent() {
        $x("//div[contains(text(), 'Дата активации списка')]/input").should(appear);
        softAssertions.assertThat(Objects.requireNonNull($x("//div[contains(text(), 'Дата активации списка')]/input").getAttribute("value"))
                        .contains(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))))
                .as("Дата активации равна текущей дате")
                .isTrue();
        softAssertions.assertAll();
        return true;
    }

    public boolean isDisposalAtList(String guid) {
        $x("//div[contains(text(), '" + guid + "')]").should(visible);
        softAssertions.assertThat($x("//div[contains(text(), '" + guid + "')]").isDisplayed())
                .as("Список выбытия отображается в Реестре выбытий")
                .isTrue();
        softAssertions.assertAll();
        return true;
    }

    public boolean isMessageSuccess() {
        $x("//h3[contains(text(), 'Список активирован')]").should(interactable);
        softAssertions.assertThat($x("//h3[contains(text(), 'Список активирован')]").isDisplayed())
                .as("Получено сообщение об успешной активации списка выбытия")
                .isTrue();
        softAssertions.assertAll();
        return true;
    }

    public boolean isAnimalSoftDeleted(DisposalList disposalList) {
        dbHelper = DBService.getRegagroDBService();
        softAssertions.assertThat(dbHelper.isSoftDeleted("number", disposalList.getAnimalNumber(), "animals"))
                .as("К живодному в БД применен soft delete")
                .isTrue();
        softAssertions.assertAll();
        return true;
    }

    public boolean isAnimalSoftDeletedByNumber(String number) {
        dbHelper = DBService.getRegagroDBService();
        softAssertions.assertThat(dbHelper.isSoftDeleted("number", number, "animals"))
                .as("К живодному в БД применен soft delete")
                .isTrue();
        softAssertions.assertAll();
        return true;
    }

    public boolean isDisposalFromGroupSuccess(String animalNumber, Map<String, String> cookies, int initCount, String countForDisposal) throws JsonProcessingException {
        locators.headerDisposalList.should(appear);
        dbHelper = DBService.getRegagroDBService();
        int actualCount = animalResponseService.getAnimalGroupFullInfo(animalNumber, cookies).getCount();
        softAssertions.assertThat(actualCount == initCount - Integer.parseInt(countForDisposal))
                .as("Количество животных в группе уменьшилось на кол-во выбывших животных")
                .isTrue();
        softAssertions.assertAll();
        return true;
    }

    public boolean isDisposalAnimalAtAnimalTerminatedList(String animalNumber, String animalGroupFromId) {
        dbHelper = DBService.getRegagroDBService();
        softAssertions.assertThat(dbHelper.isAnimalDisposalFromGroupAsTerminated(animalNumber, animalGroupFromId))
                .as("Животное с дублем номера группы находится в БД с применением soft delete")
                .isTrue();
        softAssertions.assertAll();
        return true;
    }

    public boolean isAnimalAtSupervisedObjectTo(DisposalList disposalList) {
        dbHelper = DBService.getRegagroDBService();
        List<String> animals = dbHelper.values("SELECT * FROM animals\n" +
                "JOIN supervised_objects ON animals.supervised_object_id = supervised_objects.id \n" +
                "WHERE animals.number='" + disposalList.getAnimalNumber() + "'\n" +
                "AND supervised_objects.name = '" + disposalList.getSupervisedObjectTo() + "'", "animals.id");
        return (!animals.isEmpty());
    }

    public boolean isAnimalNotAtSupervisedObjectFrom(DisposalList disposalList) {
        dbHelper = DBService.getRegagroDBService();
        List<String> animals = dbHelper.values("SELECT * FROM animals\n" +
                "JOIN supervised_objects ON animals.supervised_object_id = supervised_objects.id \n" +
                "WHERE animals.number='" + disposalList.getAnimalNumber() + "'\n" +
                "AND supervised_objects.name = '" + disposalList.getSupervisedObjectFrom() + "'", "animals.id");
        return (animals.isEmpty());
    }
}
