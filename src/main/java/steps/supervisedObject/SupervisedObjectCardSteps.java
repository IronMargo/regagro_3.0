package steps.supervisedObject;

import abstractclass.Steps;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import entities.objects_enterprises.SupervisedObject;
import io.qameta.allure.Step;
import pageElements.FindOwnerPageElement;
import pages.supervisedObject.SupervisedObjectCardPage;
import steps.lists.SupervisedObjectListSteps;

import static com.codeborne.selenide.Condition.exist;

public class SupervisedObjectCardSteps extends Steps {
    SupervisedObjectCardPage locators = new SupervisedObjectCardPage();

    public SupervisedObjectCardSteps() {
        checkLoad();
        locators.heading.should(Condition.appear);
    }

    public String getNameOfSupervisedObject() {
        checkLoad();
        return locators.nameOfSupervisedObject.should(Condition.appear).getText();
    }
    @Step("Редактирование ПО")
    public SupervisedObjectCardSteps editSupervisedObjectName(String newName) {
        locators.actionsButton.should(Condition.interactable).click();
        locators.actionsMenu.should(Condition.appear);
        locators.editSupervisedObjectButton.should(Condition.interactable).click();
        EditSupervisedObjectSteps editSupervisedObjectSteps = new EditSupervisedObjectSteps();
        editSupervisedObjectSteps.getEditSupervisedObject(newName);
        return new SupervisedObjectCardSteps();
    }
    @Step("Удаление ПО")
    public SupervisedObjectListSteps deleteSupervisedObject() {
        locators.actionsButton.should(Condition.interactable).click();
        locators.actionsMenu.should(Condition.appear);
        locators.deleteSupervisedObjectButton.should(Condition.interactable).click();
        locators.messageDelete.should(Condition.appear);
        locators.okButton.should(Condition.interactable).click();
        checkLoad();
        return new SupervisedObjectListSteps();
    }
}
