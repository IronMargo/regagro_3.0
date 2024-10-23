package pages.disposal;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class CreateDisposalListPage {
    public final SelenideElement headerRegistrationDisposal = $x("//h2[contains(text(), 'Регистрация списка выбытия')]");
    public final SelenideElement headerDisposalCard = $x("//h2[contains(text(), 'Карточка списка выбытия')]");
    public final SelenideElement headerDisposalList = $x("//h2[contains(text(), 'Список выбытия')]");
    public final SelenideElement disposalListButton = $x("//div[contains(text(),'Список выбытия')]");
    public final SelenideElement disposalReasonSelect = $x("//select[@name='disposal_reason_id']/following-sibling::*//*[@class='select2-selection select2-selection--single']");
    public final SelenideElement input = $x("//input[@class='select2-search__field']");
    public final SelenideElement disposalReasonClarificationSelect = $x("//select[@name='clarification_id']/following-sibling::*//*[@class='select2-selection select2-selection--single']");
    public final SelenideElement supervisedObjectFromButton = $x("//input[@name='supervised_object_from_id']/preceding-sibling::button");
    public final SelenideElement supervisedObjectToButton = $x("//input[@name='supervised_object_to_id']/preceding-sibling::button");
    public final SelenideElement supervisedObjectToSelectForSameOwner = $x("//select[@name='supervised_object_to_id']/following-sibling::*//*[@class='select2-selection select2-selection--single']");
    public final SelenideElement registrationDisposalListButton = $x("//button[contains(text(),'Зарегистрировать список выбытия')]");
    public final SelenideElement animalKindSelect = $x("//*[contains(text(), 'Вид животного')]/following-sibling::*/*//*[@class='select2-selection select2-selection--single']");
    public final SelenideElement addAnimalButton = $x("//button[contains(text(), 'Выбрать')]");
    public final SelenideElement filtersButton = $x("//button[contains(text(), 'Фильтры')]");
    public final SelenideElement animalNumberInput = $x("//*[contains(text(), 'Номер средства маркирования ')]/input");
    public final SelenideElement applyButton = $x("//*[@aria-labelledby='tableFilterModalLabel']//button[contains(text(), 'Применить')]");
    public final SelenideElement addToListButton = $x("//button[contains(text(), 'Добавить в список')]");
    public final SelenideElement turnToListButton = $x("//button[contains(text(), 'Вернуться ')]");
    public final SelenideElement makeAtListButton = $x("//button[contains(text(), 'Составить список')]");
    public final SelenideElement activateButton = $x("//button[contains(text(),'Активировать')]");

    public final SelenideElement okButton = $x("//button[contains(text(),'Ok')]");
    public final SelenideElement guidInput = $x("//div[contains(text(), 'GUID Списка')]/input");
}
