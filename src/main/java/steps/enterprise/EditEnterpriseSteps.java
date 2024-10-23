package steps.enterprise;

import abstractclass.Steps;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import pages.enterprise.EditEnterprisePage;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.interactable;

public class EditEnterpriseSteps extends Steps {
    EditEnterprisePage editEnterprisePage = new EditEnterprisePage();

    public EditEnterpriseSteps() {
        editEnterprisePage.heading.should(appear);
        checkLoad();
    }

    @Step("Редактирование ПП")
    public void getEditEnterprise(String newNameOfEnterprise) {
        // Удаление прежнего наименования
        editEnterprisePage.nameOfEnterpriseField.should(interactable).sendKeys(Keys.CONTROL + "a");
        editEnterprisePage.nameOfEnterpriseField.should(interactable).sendKeys(Keys.DELETE);
        checkLoad();
        // Новое наименование
        editEnterprisePage.nameOfEnterpriseField.should(interactable).pressEnter();
        editEnterprisePage.nameOfEnterpriseField.should(interactable).setValue(newNameOfEnterprise).pressEnter();
        checkLoad();
        // Сохранение изменений
        editEnterprisePage.saveButton.should(interactable).click();
        checkLoad();
    }
}
