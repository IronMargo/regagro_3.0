package pages.inventory;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class FinalActPage {
    public final SelenideElement settingButton = $x("//button[contains(text(), 'Настроить')]");
    public final SelenideElement nextButton = $x("//button[contains(text(), 'Продолжить')]");
    public final SelenideElement addTubeNumber = $x("//button[contains(text(), 'Внести номера')]");
    public final SelenideElement saveButton = $x("//button[contains(text(), 'Сохранить')]");
    public final SelenideElement dateOfSendingInput = $x("//*[contains(text(), 'Дата фактической отправки')]/parent::*//input[2]");
    public final SelenideElement heading = $x("//h4[text()='Окончательный акт']");
    public final SelenideElement dateActField = $x("//*[contains(text(), 'Дата акта')]/following-sibling::*//input[2]");

}
