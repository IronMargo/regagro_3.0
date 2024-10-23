package pageElements.inventories.action;

import abstractclass.PageElement;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import entities.tasks.Inventory;

import static com.codeborne.selenide.Selenide.$x;

public class SetResultPageElement extends PageElement {
    private final SelenideElement resultSelect = $x("//select[@name='research_result_id']/following-sibling::span//span[@class='select2-selection select2-selection--single']");
    private final SelenideElement input = $x("//input[@class='select2-search__field']");
    private final SelenideElement thicknessInput = $x("//*[contains(text(), 'Толщина складки')]/parent::div//input");
    private final SelenideElement okButton = $x("//h4[contains(text(), 'Указание результата')]/parent::div/following-sibling::div//button[contains(text(), 'Ok')]");
    public SetResultPageElement(){
        SelenideElement heading = $x("//h4[contains(text(), 'Указание результата')]");
        heading.should(Condition.appear);
    }
    public void setResult(Inventory inventory){
        resultSelect.should(Condition.interactable).click();
        input.should(Condition.interactable).setValue(inventory.getResult()).pressEnter();
        thicknessInput.should(Condition.interactable).setValue(inventory.getThickness()).pressEnter();
        okButton.should(Condition.interactable).click();
    }
}
