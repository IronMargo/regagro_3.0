package pages.inventory;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class InventoriesCardPage {
    public final SelenideElement inventoryCardHeading = $x("//h2[text()='Карточка описи']");
    public final SelenideElement setDrugButtonForEnterprise = $x("//button[contains(text(), 'Настройка препарата')]");
    public final SelenideElement actionsButton4d = $x("//h4[contains(text(), 'Владельцы и объекты')]/parent::*/following-sibling::*//*[contains(text(), 'Действия')]");
    public final SelenideElement actionsButton = $x("//h4[contains(text(), 'Список животных')]/parent::*/following-sibling::*//*[contains(text(), 'Действия')]");
    public final SelenideElement addDrugButton = $x("//a[contains(text(), 'Изменить препарат')]");
    public final SelenideElement drugModalSelect = $x("//*[contains(text(), 'Препарат')]//select/following-sibling::*//*[@class='select2-selection select2-selection--single']");
    public final SelenideElement previewActButton = $x("//button[contains(text(), 'Предварительный акт')]");
    public final SelenideElement finalActButton = $x("//button[contains(text(), 'Окончательный акт')]");
    public final SelenideElement setDrugActButton = $x("//button[contains(text(), 'Акт постановки препарата')]");
    public final SelenideElement checkResultActButton = $x("//button[contains(text(), 'Акт проверки результатов')]");
    public final SelenideElement setResultButton = $x("//button[contains(text(), 'Результаты')]");
    public final SelenideElement completeButton = $x("//button[contains(text(), 'Выполнить')]");
    public final SelenideElement applyButton = $x("//*[@aria-modal='true']//*[contains(text(), 'Да, продолжить')]");
    public final SelenideElement selectAllButton = $x("//button[contains(text(), 'Выбрать все')]");
    public final SelenideElement actionButtonForAnimals = $x("//h4[contains(text(), 'Список животных')]/parent::*/following-sibling::*//*[contains(text(), 'Действия')]/parent::a");
    public final SelenideElement setParamsButton = $x("//a[contains(text(), 'Установить параметры')]");
    public final SelenideElement setDrugButton = $x("//a[contains(text(), 'Установить препарат')]");
    public final SelenideElement generalButton = $x("//button[contains(text(), 'Основное')]");
    public final SelenideElement settingBatchButton = $x("//button[contains(text(), 'Настройка партий')]");
    public final SelenideElement addBatchButton = $x ("//a[contains(text(), 'Добавить партию')]");
    public final SelenideElement okButton = $x("//button[contains(text(), 'Ok')]");

}
