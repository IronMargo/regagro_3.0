package steps.task;

import abstractclass.Steps;
import entities.tasks.Task;
import io.qameta.allure.Step;
import pages.task.EditTaskPage;

import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;
import static datagenerator.DataGeneratorDate.convertDateToNeedFormat;

public class EditTaskSteps extends Steps {
    EditTaskPage editTaskPage = new EditTaskPage();

    public EditTaskSteps() {
        editTaskPage.heading.should(appear);
        checkLoad();
    }
    @Step("Отправка задания")
    public TaskCardSteps sendTask() {
        checkLoad();
        editTaskPage.sendButton.should(interactable).click();
        editTaskPage.messageSuccess.should(appear);
        editTaskPage.toCardButton.should(interactable).click();
        return new TaskCardSteps();
    }
    @Step("Наименование задания корректно")
    public boolean isCorrectNameDisplayed(Task task) {
        return $x("//p[text()='" + task.getName() + "']").should(visible).isDisplayed();
    }
    @Step("Тип задания корректный")
    public boolean isCorrectTypeDisplayed(Task task) {
        return $x("//p[text()='" + task.getType() + "']").should(visible).isDisplayed();
    }
    @Step("Дата задания ОТ корректна")
    public boolean isCorrectDateFromDisplayed(Task task) {
        String dateFrom = convertDateToNeedFormat(task.getDateFrom());
        return $x("//span[contains(text(), '" + dateFrom + "')]").should(visible).isDisplayed();
    }
    @Step("Дата задания ДО корректна")
    public boolean isCorrectDateBeforeDisplayed(Task task) {
        String dateBefore = convertDateToNeedFormat(task.getDateBefore());
        return $x("//span[contains(text(), '" + dateBefore + "')]").should(visible).isDisplayed();
    }
    @Step("Заболевание корректно")
    public boolean isCorrectDiseaseDisplayed(Task task) {
        return $x("//p[text()='" + task.getDisease() + "']").should(visible).isDisplayed() ||
                $x("//div[text()='" + task.getDisease() + "']").should(visible).isDisplayed();
    }
    @Step("Территория корректна")
    public boolean isCorrectServiceAreaDisplayed(Task task) {
        return ($x("//div[contains(text(), '" + task.getServiceArea() + "')]")
                .should(visible).isDisplayed());
    }
    @Step("Площадка корректна")
    public boolean isCorrectEnterpriseDisplayed(Task task) {
        return $x("//span[contains(text(), '" + task.getEnterpriseName() + "')]")
                .should(visible).isDisplayed();
    }
    @Step("Вид животного корректен")
    public boolean isCorrectKindsDisplayed(Task task) {
        List<String> kinds = task.getKinds();
        boolean allDisplayed = true;
        for (String kind : kinds) {
            if (!$x("//span[contains(text(), '" + kind + "')]").should(visible).isDisplayed()) {
                allDisplayed = false;
                break;
            }
        }
        return allDisplayed;
    }
}
