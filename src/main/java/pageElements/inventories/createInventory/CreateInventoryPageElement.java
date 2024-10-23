package pageElements.inventories.createInventory;

import abstractclass.PageElement;
import entities.tasks.Task;
import steps.inventory.InventoriesCardSteps;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import entities.tasks.Inventory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$x;

public class CreateInventoryPageElement extends PageElement {
    private final SelenideElement planDateField = $x("//input[@placeholder='Введите дату'][2]");
    private final SelenideElement executorSelect = $x("//select[@name='inventoryExecutor']/following-sibling::span//span[@class='select2-selection select2-selection--single select2-selection--clearable']");
    private final SelenideElement input = $x("//input[@class='select2-search__field']");
    private final SelenideElement okButton = $x("//button[contains(text(), 'Ok')]");
    private final SelenideElement laboratorySelect = $x("//select[@name='laboratory_id']/following-sibling::span//span[@class='select2-selection select2-selection--single']");
    private final SelenideElement materialSelect = $x("//*[contains(text(), 'Материал исследования')]/parent::div/div//span[@class='select2-selection select2-selection--single']");
    private final SelenideElement methodSelect = $x("//*[contains(text(), 'Метод исследования')]/parent::div/div//span[@class='select2-selection select2-selection--single']");
    private final SelenideElement countInput = $x("//span[contains(text(), 'Количество исследований по заболеванию')]/parent::div//input");
    private final SelenideElement checkLoad = $x("//*[@class='modal-busy d-none']");

    public InventoriesCardSteps createInventories(){
        planDateField.should(Condition.interactable).setValue(LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"))).pressEnter();
        okButton.should(Condition.interactable).click();
        return new InventoriesCardSteps();
    }
    public void selectExecutor(Task task){
        executorSelect.should(Condition.interactable).click();
        input.should(Condition.interactable).setValue(task.getCoExecutor()).pressEnter();
    }
    public InventoriesCardSteps createDiagnosticInventories(Inventory inventorie){
        checkLoad.should(exist);
        laboratorySelect.should(Condition.interactable).click();
        input.should(Condition.interactable).setValue(inventorie.getLaboratory()).pressEnter();
        countInput.should(Condition.interactable).setValue(inventorie.getResearchCounts()).pressEnter();
        materialSelect.should(Condition.interactable).click();
        input.should(Condition.interactable).setValue(inventorie.getMaterialResearch()).pressEnter();
        methodSelect.should(Condition.interactable).click();
        input.should(Condition.interactable).setValue(inventorie.getMethodResearch()).pressEnter();
        planDateField.should(Condition.interactable).setValue(LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"))).pressEnter();
        okButton.should(Condition.interactable).click();
        return new InventoriesCardSteps();
    }
    public boolean isCoExecutorSelect(Task task){
        return $x("//select[@name='inventoryExecutor']/following-sibling::span//span[@class='select2-selection__rendered']")
                .should(Condition.appear).getText().equals(task.getCoExecutor());
    }

}
