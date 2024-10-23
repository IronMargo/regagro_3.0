package pageElements.inventories.acts;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import entities.tasks.Inventory;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class SettingDiagnosticActPageElement extends SettingActPageElement {
    private final SelenideElement storageConditionsInput = $x("//*[contains(text(), 'Условия хранения и доставки проб')]/following-sibling::div/textarea");

    protected SettingDiagnosticActPageElement() {
    }
    @Step("Детальная настройка предварительного акта")
    public void settingPreviewActDetailsDiagnostic(Inventory inventory) {
        storageConditionsInput.should(Condition.interactable).setValue(inventory.getStorageConditions()).pressEnter();
        postField.should(Condition.interactable).setValue(inventory.getPost());
        nameField.should(Condition.interactable).setValue(inventory.getName());
        okButton.should(Condition.interactable).click();
    }
    @Step("Детальная настройка окончательного акта")
    public void settingFinalActDetailsDiagnostic(Inventory inventory) {}
}
