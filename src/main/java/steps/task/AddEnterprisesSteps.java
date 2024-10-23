package steps.task;

import abstractclass.Steps;
import entities.tasks.Task;
import io.qameta.allure.Step;
import pages.task.AddEnterprisesPage;

import java.util.List;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.interactable;
import static com.codeborne.selenide.Selenide.$x;

public class AddEnterprisesSteps extends Steps {
    AddEnterprisesPage addEnterprisesPage = new AddEnterprisesPage();

    public AddEnterprisesSteps() {
        addEnterprisesPage.heading.should(appear);
    }
    @Step("Добавление площадок")
    public void addEnterprise(Task task) {
        // Выбор территории
        addEnterprisesPage.serviceAreaSelect.should(interactable).click();
        addEnterprisesPage.input.should(interactable).setValue(task.getServiceArea()).pressEnter();
        $x("//div[contains(text(), '" + task.getServiceArea() + "')]")
                .should(appear);
        // Выбор площадок
        addEnterprisesPage.addEnterpriseButton.should(interactable).click();
        addEnterprisesPage.findEnterpriseHeading.should(appear);
        // Выбор с помощью фильтра площадок, указанных при генерации данных задания
        List<String> enterprises = task.getEnterpriseName();
        boolean firstIteration = true;
        for (String enterprise : enterprises) {
            addEnterprisesPage.filtersButton.should(interactable).click();
            if (firstIteration) {
                addEnterprisesPage.ownersAndEnterprisesButton.should(interactable).click();
                firstIteration = false;
            }
            addEnterprisesPage.nameOfEnterprisesField.should(interactable).setValue(enterprise);
            addEnterprisesPage.applyButton.should(interactable).click();
            if (!($x("//div[contains(text(), 'не найдено')]").isDisplayed())) {
                addEnterprisesPage.addToTaskButton.should(interactable).click();
                $x("//div[1]/div[contains(text(), '" + enterprise + "')]")
                        .should(interactable).click();
                addEnterprisesPage.addToTaskButton.should(interactable).click();
            }
        }
        // Возвращение в Карточку задания
        addEnterprisesPage.turnToTaskButton.should(interactable).click();
        addEnterprisesPage.nextButton.should(interactable).click();
    }
}
