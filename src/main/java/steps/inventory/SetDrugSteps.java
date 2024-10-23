package steps.inventory;

import abstractclass.Steps;
import com.codeborne.selenide.Condition;
import entities.tasks.Inventory;
import io.qameta.allure.Step;
import pageElements.inventories.SettingDrugsPageElement;
import pages.inventory.SetDrugPage;

import static com.codeborne.selenide.Condition.interactable;
import static com.codeborne.selenide.Selenide.$x;

public class SetDrugSteps extends Steps {

   private final SetDrugPage setDrugPage = new SetDrugPage();
    @Step("Настройка препарата")
    public void setDrug(Inventory inventory) {
        setDrugPage.addDrugButton.should(Condition.interactable).click();

        // Настройка препарата в модальном окне
        SettingDrugsPageElement settingDrugsPageElement = new SettingDrugsPageElement();
        settingDrugsPageElement.isModalWindowAppear();
        settingDrugsPageElement.setParameters(inventory);
    }
}
