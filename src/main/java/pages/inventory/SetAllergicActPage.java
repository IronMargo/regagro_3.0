package pages.inventory;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class SetAllergicActPage {
    public final SelenideElement settingButton = $x("//button[contains(text(), 'Настроить')]");
    public final SelenideElement toWorkButton = $x("//button[contains(text(), 'В работу')]");
    public final SelenideElement nextButton = $x("//button[contains(text(), 'Продолжить')]");
    public final SelenideElement dateField = $x("//*[contains(text(), 'Дата акта')]/following-sibling::*//input[2]");
    public final SelenideElement dateFieldInput = $x("//input[@class='form-control flatpickr input active']");
    public SelenideElement settingDrugHeading = Selenide.$x("//h4[text()='Акт постановки препарата']");
    public SelenideElement checkResultsHeading = Selenide.$x("//h4[text()='Акт проверки результатов']");
}
