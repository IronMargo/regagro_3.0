package pages.task;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class AddGeneralInfoPage {
    public final SelenideElement nameField = $x("//*[contains(text(), 'Наименование')]/following-sibling::*//input[@type='text']");
    public final SelenideElement fromDate = $x("//*[contains(text(), 'Период')]/following-sibling::*/*[1]/*/input[@type='text']");
    public final SelenideElement beforeDate = $x("//*[contains(text(), 'Период')]/following-sibling::*/*[2]/*/input[@type='text']");
    public final SelenideElement eventTypeSelect = $x("//*[contains(text(), 'Мероприятие')]/following-sibling::*//*[@class='select2-selection select2-selection--single']");
    public final SelenideElement nextButton = $x("//button[contains(text(), 'Далее')]");
    public final SelenideElement input = $x("//input[@class='select2-search__field']");
    public final SelenideElement heading = Selenide.$x("//h2[text()='Добавление задания']");
}
