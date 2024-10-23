package steps.lists;

import abstractclass.Steps;
import entities.tasks.Task;
import io.qameta.allure.Step;
import pages.lists.ListsPage;
import steps.task.AddDiseasesSteps;
import steps.task.AddEnterprisesSteps;
import steps.task.AddGeneralInfoSteps;
import steps.task.AddKindsSteps;
import steps.task.EditTaskSteps;
import steps.task.TaskCardSteps;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class TasksListSteps extends Steps {
    ListsPage locators = new ListsPage();

    public TasksListSteps() {
        locators.tasksHeading.should(appear);
    }
    @Step("Создание нового задания")
    public EditTaskSteps addNewTask(Task task) {
        if (task.getType().equals("Дезинфекция") || task.getType().equals("Дезинсекция") || task.getType().equals("Дератизация")) {
            locators.addTaskButton.should(interactable).click();
            // Общая информация
            AddGeneralInfoSteps addGeneralInfoSteps = new AddGeneralInfoSteps();
            AddEnterprisesSteps firstStep = addGeneralInfoSteps.addGeneralInfo(task);
            // Площадки и территории
            firstStep.addEnterprise(task);
            AddKindsSteps secondStep = new AddKindsSteps();
            secondStep.goToNextStep();
        } else {
            locators.addTaskButton.should(interactable).click();
            // Общая информация
            AddGeneralInfoSteps addGeneralInfoSteps = new AddGeneralInfoSteps();
            AddEnterprisesSteps firstStep = addGeneralInfoSteps.addGeneralInfo(task);
            // Площадки и территории
            firstStep.addEnterprise(task);
            AddDiseasesSteps secondStep = new AddDiseasesSteps();
            // Заболевание
            AddKindsSteps thirdStep;
            if (task.getType().equals("Дегельминтизация")) {
                thirdStep = secondStep.addDiseasesGroup(task);
            } else {
                thirdStep = secondStep.addDiseases(task);
            }
            // Виды животных
            thirdStep.addKinds(task);
        }
        return new EditTaskSteps();
    }
    @Step("Открыть карточку задания")
    public TaskCardSteps openTaskCardPage(String taskNumber) {
        $x("//div[contains(text(), '" + taskNumber + "')]").should(interactable).click();
        return new TaskCardSteps();
    }
    @Step("Проверка, что задание получено")
    public boolean isTaskGetting(String taskNumber) {
        return $x("//div[contains(text(), '" + taskNumber + "')]").should(visible).isDisplayed();
    }

}
