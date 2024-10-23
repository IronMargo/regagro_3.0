package steps.supervisedObject;

import abstractclass.Steps;
import com.codeborne.selenide.Selenide;
import entities.objects_enterprises.SupervisedObject;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import pages.supervisedObject.EditSupervisedObjectPage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class EditSupervisedObjectSteps extends Steps {
    EditSupervisedObjectPage editSupervisedObjectPage = new EditSupervisedObjectPage();

    public EditSupervisedObjectSteps() {
        editSupervisedObjectPage.heading.should(appear);
        checkLoad();
    }
    @Step("Редактирование ПО")
    public void getEditSupervisedObject(String newNameOfSupervisedObject) {
        // Удаление прежнего наименования
        checkLoad();
        editSupervisedObjectPage.nameOfSupervisedObjectInput.should(interactable).sendKeys(Keys.CONTROL + "a");
        editSupervisedObjectPage.nameOfSupervisedObjectInput.sendKeys(Keys.DELETE);
        // Новое наименование
       // editSupervisedObjectPage.nameOfSupervisedObjectInput.should(interactable).pressEnter();
        editSupervisedObjectPage.nameOfSupervisedObjectInput.should(interactable).setValue(newNameOfSupervisedObject).pressEnter();
        checkLoad();
        // Сохранение изменений
        editSupervisedObjectPage.saveButton.should(interactable).click();
        checkLoad();
    }
}
