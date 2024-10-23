package steps.task;

import abstractclass.Steps;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import entities.tasks.Task;
import io.qameta.allure.Step;
import pages.task.AddDiseasesPage;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.interactable;
import static com.codeborne.selenide.Selenide.$x;

public class AddDiseasesSteps extends Steps {
    AddDiseasesPage addDiseasesPage = new AddDiseasesPage();

    public AddDiseasesSteps() {
       addDiseasesPage.heading.should(appear);
    }
    @Step("Добавление заболевания")
    public AddKindsSteps addDiseases(Task task) {
        addDiseasesPage.diseaseSelect.should(interactable).click();
        addDiseasesPage.input.should(interactable).setValue(task.getDisease()).pressEnter();
        addDiseasesPage.nextButton.should(interactable).click();
        return new AddKindsSteps();
    }
    @Step("Добавление группы заболеваний")
    public AddKindsSteps addDiseasesGroup(Task task) {
        $x("//label[contains(text(), '" + task.getDiseasesGroups().get(0) + "')]").should(interactable).click();
        $x("//label[contains(text(), '" + task.getDiseasesGroups().get(1) + "')]/ancestor::button").should(interactable).click();
        $x("//small[contains(text(), '" + task.getDisease() + "')]/parent::label").should(interactable).click();
        addDiseasesPage.nextButton.should(interactable).click();
        return new AddKindsSteps();
    }
}
