package steps.inventory;

import abstractclass.Steps;
import entities.tasks.Inventory;
import entities.tasks.Task;
import io.qameta.allure.Step;
import pages.inventory.SetOfBatchPage;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.interactable;
import static com.codeborne.selenide.Selenide.$x;

public class SetOfBatchSteps extends Steps {
    SetOfBatchPage setOfBatchPage = new SetOfBatchPage();
    @Step("Добавление второй партии")
    public void addSecondBatch(Inventory inventory, Task task) {
        $x("//span[@title='" + task.getDisease() + "']").should(appear);
        setOfBatchPage.addBatchButton.should(interactable).click();

        $x("//h3[contains(text(), 'Партия 2')]").should(appear);
        setOfBatchPage.nameOfBatchInput.should(interactable).setValue("Партия 2").pressEnter();

        setOfBatchPage.materialResearchTwoSelect.should(interactable).click();
        setOfBatchPage.input.should(interactable).setValue(inventory.getMaterialResearchBatchTwo()).pressEnter();

        setOfBatchPage.methodResearchTwoSelect.should(interactable).click();
        setOfBatchPage.input.should(interactable).setValue(inventory.getMethodResearchBatchTwo()).pressEnter();

        setOfBatchPage.diseaseBatchSelect.should(interactable).click();
        setOfBatchPage.input.should(interactable).setValue(task.getDisease()).pressEnter();

        setOfBatchPage.researchCountTwoInput.should(interactable).setValue(inventory.getResearchCountsBatchTwo()).pressEnter();

        setOfBatchPage.saveButton.should(interactable).click();
        setOfBatchPage.messageSuccess.should(appear);
        setOfBatchPage.okButton.should(interactable).click();
    }
}
