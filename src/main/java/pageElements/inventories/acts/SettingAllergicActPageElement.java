package pageElements.inventories.acts;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import entities.tasks.Inventory;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class SettingAllergicActPageElement extends SettingActPageElement {
    private final SelenideElement syringeLabel = $x("//label[contains(text(), 'Шприц')]");
    private final SelenideElement cutimeterLabel = $x("//label[contains(text(), 'Кутиметром')]");
    protected SettingAllergicActPageElement(){}
    @Step("Детальная настройка Акта постановки препарата")
    public void settingSetDrugActDetails(Inventory inventory) {
        antisepticSelect.should(Condition.interactable).click();
        inputSimple.should(Condition.interactable).setValue(inventory.getAntisepticName()).pressEnter();
        countOfAntisepticInput.should(Condition.interactable).setValue(inventory.getAntisepticCount()).pressEnter();
        cottonInput.should(Condition.interactable).setValue(inventory.getCottonCount()).pressEnter();
        syringeInput.should(Condition.interactable).setValue(inventory.getSyringeCount()).pressEnter();
        glovesInput.should(Condition.interactable).setValue(inventory.getGlovesCount()).pressEnter();
        syringeLabel.should(Condition.interactable).click();
        postField.should(Condition.interactable).setValue(inventory.getPost());
        nameField.should(Condition.interactable).setValue(inventory.getName());
        okButton.should(Condition.interactable).click();
    }
    @Step("Детальная настройка Акта проверки результата")
    public void settingCheckResultActDetails(Inventory inventory) {
        cutimeterLabel.should(Condition.interactable).click();
        postField.should(Condition.interactable).setValue(inventory.getPost());
        nameField.should(Condition.interactable).setValue(inventory.getName());
        okButton.should(Condition.interactable).click();
    }
}
