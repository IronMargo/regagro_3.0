package pages.inventory;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class SetDrugPage {
    public final SelenideElement addDrugButton = $x("//div[contains(text(), 'Добавить препарат')]");


    public final SelenideElement drugNameSelect = $x("//select[@name='drugs']/parent::*//child::*[@class='select2-selection select2-selection--single']");
    public final SelenideElement input = $x("//input[@class='select2-search__field']");
    public final SelenideElement settingButton = $x("//i[@class='panel bi bi-gear-fill']");
    public final SelenideElement availableInput = $x("//*[contains(text(), 'В наличии')]/parent::*//input");
    public final SelenideElement unitNameSelect = $x("//select[@name='unit_id']/following-sibling::*//*[@class='select2-selection select2-selection--single']");
    public final SelenideElement seriesInput = $x("//*[contains(text(), 'Серия')]/parent::*//input");
    public final SelenideElement numberOfBatchInput = $x("//*[contains(text(), 'Номер партии')]/parent::*//input");
    public final SelenideElement gosControlInput = $x("//*[contains(text(), 'Госконтроль')]/parent::*//input");
    public final SelenideElement dateFromSelect = $x("//*[contains(text(), 'Дата производства')]/parent::*//i[@class='bi bi-calendar3']");
    public final SelenideElement dateBeforeSelect = $x("//*[contains(text(), 'Срок годности')]/parent::*//i[@class='bi bi-calendar3']");
    public final SelenideElement okButton = $x("//button[contains(text(), 'ok')]");

}
