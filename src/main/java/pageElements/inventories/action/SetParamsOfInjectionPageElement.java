package pageElements.inventories.action;

import abstractclass.PageElement;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import entities.tasks.Inventory;

import static com.codeborne.selenide.Selenide.$x;

public class SetParamsOfInjectionPageElement extends PageElement {

    private final SelenideElement doseInput = $x("//div[contains(text(), 'Доза')]/parent::div//input");

    private final SelenideElement drugUseWaySelect = $x("//select[@name='drug_use_way_id']/following-sibling::span//span[@class='select2-selection select2-selection--single']");
    private final SelenideElement input = $x("//input[@class='select2-search__field']");
    private final SelenideElement injectionPlaceSelect = $x("//select[@name='injection_place_id']/following-sibling::span//span[@class='select2-selection select2-selection--single']");
    private final SelenideElement okButton = $x("//h4[contains(text(), 'Изменение параметров введения')]/parent::div/following-sibling::div//button[contains(text(), 'Ok')]");

    public SetParamsOfInjectionPageElement() {
        SelenideElement heading = Selenide.$x("//h4[contains(text(), 'Изменение параметров введения')]");
        heading.should(Condition.appear);
    }

    public void SetParamsOfInjection(Inventory inventory){
        doseInput.should(Condition.interactable).setValue(inventory.getDose()).pressEnter();
        drugUseWaySelect.should(Condition.interactable).click();
        input.should(Condition.interactable).setValue(inventory.getDrugUseWay()).pressEnter();
        injectionPlaceSelect.should(Condition.interactable).click();
        input.should(Condition.interactable).setValue(inventory.getInjectionPlace()).pressEnter();
        okButton.should(Condition.interactable).click();
    }

}
