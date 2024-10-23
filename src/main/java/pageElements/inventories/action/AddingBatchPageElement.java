package pageElements.inventories.action;

import abstractclass.PageElement;
import /**/com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import entities.tasks.Inventory;

import static com.codeborne.selenide.Selenide.$x;

public class AddingBatchPageElement extends PageElement {
    private final SelenideElement batchSelect = $x("//select[@name='batches']/following-sibling::span//span[@class='select2-selection select2-selection--single']");
    private final SelenideElement input = $x("//input[@class='select2-search__field']");
    private final SelenideElement addButton = $x("//button[contains(text(), 'Добавить')]");

    public AddingBatchPageElement() {
        SelenideElement heading = $x("//h4[contains(text(), 'Добавление партии')]");
        heading.should(Condition.appear);
    }

    public void selectBatch(Inventory inventory){
        batchSelect.should(Condition.interactable).click();
        input.should(Condition.interactable).setValue(inventory.getNameOfBatchTwo()).pressEnter();
        addButton.should(Condition.interactable).click();
    }

}
