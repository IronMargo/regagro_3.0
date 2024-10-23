package pages.inventory;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class PreviewActPage {
    public final SelenideElement settingButton = $x("//button[contains(text(), 'Настроить')]");
    public final SelenideElement toWorkButton = $x("//button[contains(text(), 'В работу')]");
    public final SelenideElement dateField = $x("//*[contains(text(), 'Дата акта')]/following-sibling::*//input[2]");
    public final SelenideElement dateFieldInput = $x("//input[@class='form-control flatpickr input active']");
    public final SelenideElement heading = $x("//h4[text()='Предварительный акт']");
}
