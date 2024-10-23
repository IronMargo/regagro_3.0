package steps.inventory;

import abstractclass.Steps;
import com.codeborne.selenide.Selenide;
import entities.tasks.Inventory;
import io.qameta.allure.Step;
import pages.inventory.SetDrugFor4dPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.interactable;

public class SetDrugFor4dSteps extends Steps {
    private final SetDrugFor4dPage setDrugFor4dPage = new SetDrugFor4dPage();

    @Step("Настройка препарата")
    public void setDrug(Inventory inventory) {
        setDrugFor4dPage.addButton.should(interactable).click();
        checkLoad();
        Selenide.sleep(3000);
        setDrugFor4dPage.drugNameSelect.should(interactable).click();
        checkLoad();
        setDrugFor4dPage.input.should(interactable).setValue(inventory.getTreatmentProduct()).pressEnter();

        setDrugFor4dPage.methodSelect.should(interactable).click();
        setDrugFor4dPage.input.should(interactable).setValue(inventory.getMethod()).pressEnter();

        setDrugFor4dPage.inputSolutionSpent.should(interactable).setValue(inventory.getSolutionSpent());

        setDrugFor4dPage.volumeSelect.should(interactable).click();
        setDrugFor4dPage.input.should(interactable).setValue(inventory.getVolume()).pressEnter();

        setDrugFor4dPage.inputConcentration.should(interactable).setValue(inventory.getConcentration()).pressEnter();
        checkLoad();

        setDrugFor4dPage.okButton.should(interactable).click();
    }
}
