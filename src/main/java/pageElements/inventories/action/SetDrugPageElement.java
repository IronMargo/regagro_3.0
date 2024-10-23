package pageElements.inventories.action;

import abstractclass.PageElement;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import entities.tasks.Inventory;

import static com.codeborne.selenide.Selenide.$x;

public class SetDrugPageElement extends PageElement {

    private final SelenideElement drugSelect = $x("//select[@name='inventory_drug_id']/following-sibling::span//span[@class='select2-selection select2-selection--single']");
    private final SelenideElement input = $x("//input[@class='select2-search__field']");
    private final SelenideElement okButton = $x("//h4[contains(text(), 'Выбор препарата')]/parent::div/following-sibling::div//button[contains(text(), 'Ok')]");

    public SetDrugPageElement() {
        SelenideElement heading = Selenide.$x("//h4[contains(text(), 'Выбор препарата')]");
        heading.should(Condition.appear);
    }
    public void setDrug(Inventory inventory){
        drugSelect.should(Condition.interactable).click();
        input.should(Condition.interactable).setValue(inventory.getDrugName()).pressEnter();
        okButton.should(Condition.interactable).click();
    }
}
