package pageElements.inventories.acts;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import entities.tasks.Inventory;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class SettingVaccinationActPageElement extends SettingActPageElement {
    private final SelenideElement destroyedDrugsInput = $x("//*[contains(text(), 'Уничтожено препарата')]/parent::div//input");
    protected SettingVaccinationActPageElement() {
    }
    @Step("Детальная настройка предварительного акта для Вакцинации")
    public void settingPreviewActDetailsVaccination(Inventory inventory) {
        postField.should(Condition.interactable).setValue(inventory.getPost());
        nameField.should(Condition.interactable).setValue(inventory.getName());
        okButton.should(Condition.interactable).click();
    }
    @Step("Детальная настройка окончательного акта для Вакцинации")
    public void settingFinalActDetails(Inventory inventory) {
        destroyedDrugsInput.should(Condition.interactable).setValue(inventory.getDestroyedDrugsCount()).pressEnter();
        antisepticSelect.should(Condition.interactable).click();
        inputSimple.should(Condition.interactable).setValue(inventory.getAntisepticName()).pressEnter();
        countOfAntisepticInput.should(Condition.interactable).setValue(inventory.getAntisepticCount()).pressEnter();
        cottonInput.should(Condition.interactable).setValue(inventory.getCottonCount()).pressEnter();
        syringeInput.should(Condition.interactable).setValue(inventory.getSyringeCount()).pressEnter();
        glovesInput.should(Condition.interactable).setValue(inventory.getGlovesCount()).pressEnter();
        okButton.should(Condition.interactable).click();
    }
}
