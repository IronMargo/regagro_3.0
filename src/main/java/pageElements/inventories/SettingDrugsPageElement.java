package pageElements.inventories;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import entities.tasks.Inventory;
import pages.inventory.SetDrugPage;

import static com.codeborne.selenide.Selenide.$x;

public class SettingDrugsPageElement {
    private final SetDrugPage  setDrugPage = new SetDrugPage();
    private final String heading = "Настройка препарата";

    public void isModalWindowAppear() {
        SelenideElement headElement = $x("//h4[contains(text(), '" + heading + "')]");
        headElement.should(Condition.appear);
    }
    public void setParameters(Inventory inventory) {
        // Выбор препарата
        setDrugPage.drugNameSelect.should(Condition.interactable).click();
        setDrugPage.input.should(Condition.interactable).setValue(inventory.getDrugName()).pressEnter();

        // Настройки препарата
        setDrugPage.availableInput.should(Condition.interactable).setValue(inventory.getAvailableCount()).pressEnter();
        setDrugPage.unitNameSelect.should(Condition.interactable).click();
        setDrugPage.input.should(Condition.interactable).setValue(inventory.getUnitName()).pressEnter();
        setDrugPage.seriesInput.should(Condition.interactable).setValue(inventory.getSeries()).pressEnter();
        setDrugPage.numberOfBatchInput.should(Condition.interactable).setValue(inventory.getNumberOfBatch()).pressEnter();
        setDrugPage.gosControlInput.should(Condition.interactable).setValue(inventory.getGosControl()).pressEnter();
        setDrugPage.dateFromSelect.should(Condition.interactable).click();
        $x("//div[contains(text(), 'Дата производства')]/parent::div//input[@class='form-control flatpickr input active']")
                .should(Condition.interactable).setValue(inventory.getDateFrom()).pressEnter();
        setDrugPage.dateBeforeSelect.should(Condition.interactable).click();
        $x("//div[contains(text(), 'Срок годности')]/parent::div//input[@class='form-control flatpickr input active']")
                .should(Condition.interactable).setValue(inventory.getDateBefore()).pressEnter();

        setDrugPage.okButton.should(Condition.interactable).click();
    }
}
