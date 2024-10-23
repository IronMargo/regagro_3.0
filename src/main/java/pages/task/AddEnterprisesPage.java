package pages.task;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class AddEnterprisesPage {
    public final SelenideElement serviceAreaSelect = $x("//*[contains(text(), 'Территория')]/following-sibling::*//*[@class='select2-selection select2-selection--single']");
    public final SelenideElement addEnterpriseButton = $x("//button[contains(text(), 'Выбрать площадку')]");
    public final SelenideElement findEnterpriseHeading = $x("//h2[contains(text(), 'Все площадки владельцев')]");
    public final SelenideElement filtersButton = $x("//button[contains(text(), 'Фильтры')]");
    public final SelenideElement ownersAndEnterprisesButton = $x("//button[contains(text(), 'Владелец и площадка')]");
    public final SelenideElement nameOfEnterprisesField = $x("//*[contains(text(), 'Наименование площадки')]//input");
    public final SelenideElement applyButton = $x("//*[@aria-labelledby='tableFilterModalLabel']//button[contains(text(), 'Применить')]");
    public final SelenideElement addToTaskButton = $x("//button[contains(text(), 'Добавить в список')]");
    public final SelenideElement turnToTaskButton = $x("//button[contains(text(), 'Вернуться ')]");
    public final SelenideElement nextButton = $x("//button[contains(text(), 'Далее')]");
    public final SelenideElement input = $x("//input[@class='select2-search__field']");
    public final SelenideElement heading = Selenide.$x("//h4[contains(text(), 'территорий')]");
}
