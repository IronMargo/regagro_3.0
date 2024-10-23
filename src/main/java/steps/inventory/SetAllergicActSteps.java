package steps.inventory;

import abstractclass.Steps;
import com.codeborne.selenide.Condition;
import entities.tasks.Inventory;
import io.qameta.allure.Step;
import pageElements.inventories.acts.SettingActPageElement;
import pageElements.inventories.acts.SettingAllergicActPageElement;
import pages.inventory.SetAllergicActPage;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.interactable;
import static com.codeborne.selenide.Selenide.$x;

public class SetAllergicActSteps extends Steps {
    SetAllergicActPage setAllergicActPage = new SetAllergicActPage();
    @Step("Настройка Акта постановки препарата")
    public void settingSetDrugAct(Inventory inventory) {
        setAllergicActPage.settingDrugHeading.should(appear);
        // Дата
        setAllergicActPage.dateField.should(interactable).click();
        //setAllergicActPage.dateFieldInput.should(interactable).setValue(inventory.getDate()).pressEnter();
        $x("//span[@class='flatpickr-day today']").should(Condition.interactable).click();
        // Детальная настройка
        setAllergicActPage.settingButton.should(interactable).click();
        SettingActPageElement settingActPageElement = new SettingActPageElement();
        SettingAllergicActPageElement settingAllergicActPageElement = settingActPageElement
                .getSettingActPageElement("Аллергические исследования");
        settingAllergicActPageElement.settingSetDrugActDetails(inventory);
        // Перевод "В работу"
        setAllergicActPage.toWorkButton.should(interactable).click();
    }
    @Step("Настройка Акта проверки результата")
    public void settingCheckResultAct(Inventory inventory) {
        setAllergicActPage.checkResultsHeading.should(appear);
        // Детальная настройка
        setAllergicActPage.settingButton.should(interactable).click();
        SettingActPageElement settingActPageElement = new SettingActPageElement();
        SettingAllergicActPageElement settingAllergicActPageElement = settingActPageElement
                .getSettingActPageElement("Аллергические исследования");
        settingAllergicActPageElement.settingCheckResultActDetails(inventory);
        // Переход к следующему шагу
        setAllergicActPage.nextButton.should(interactable).click();
    }
}
