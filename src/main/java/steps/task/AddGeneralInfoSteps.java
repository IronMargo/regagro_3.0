package steps.task;

import abstractclass.Steps;
import entities.tasks.Task;
import io.qameta.allure.Step;
import pages.task.AddGeneralInfoPage;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.interactable;

public class AddGeneralInfoSteps extends Steps {
    AddGeneralInfoPage addGeneralInfoPage = new AddGeneralInfoPage();

    public AddGeneralInfoSteps() {
        addGeneralInfoPage.heading.should(appear);
    }
    @Step("Добавление общей информации")
    public AddEnterprisesSteps addGeneralInfo(Task task) {
        addGeneralInfoPage.nameField.should(interactable).setValue(task.getName());
        addGeneralInfoPage.fromDate.should(interactable).setValue(task.getDateFrom());
        addGeneralInfoPage.beforeDate.should(interactable).setValue(task.getDateBefore());
        addGeneralInfoPage.eventTypeSelect.should(interactable).click();
        addGeneralInfoPage.input.should(interactable).setValue(task.getType()).pressEnter();
        addGeneralInfoPage.nextButton.should(interactable).click();
        return new AddEnterprisesSteps();
    }
}
