package steps.supervisedObject;

import abstractclass.Steps;
import entities.objects_enterprises.SupervisedObject;
import io.qameta.allure.Step;
import pageElements.FindEnterprisePageElement;
import pageElements.FindOwnerPageElement;
import pages.supervisedObject.AddSupervisedObjectPage;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.interactable;

public class AddSupervisedObjectSteps extends Steps {
    AddSupervisedObjectPage locators = new AddSupervisedObjectPage();

    public AddSupervisedObjectSteps() {
        locators.heading.should(appear);
        checkLoad();
    }
    @Step("Регистрация ПО")
    public void getNewSupervisedObject(SupervisedObject supervisedObject, String nameOfSupervisedObject) {
        // Выбор площадки
        locators.chooseEnterpriseButton.should(interactable).click();
        FindEnterprisePageElement findEnterprisePageElement = new FindEnterprisePageElement();
        findEnterprisePageElement.getEnterprise(supervisedObject);
        // Выбор владельца
        locators.chooseOwnerButton.should(interactable).click();
        FindOwnerPageElement findOwnerPageElement = new FindOwnerPageElement();
        findOwnerPageElement.getOwner(supervisedObject.getOwnerInn());
        // Наименование ПО
        locators.nameOfSupervisedObjectInput.should(interactable).setValue(nameOfSupervisedObject);
        //Тип ПО
        locators.typeOfSupervisedObjectSelect.should(interactable).click();
        locators.input.should(interactable).setValue(supervisedObject.getType()).pressEnter();
        // Вид деятельности
        locators.activityOfSupervisedObjectSelect.should(interactable).click();
        locators.input.should(interactable).setValue(supervisedObject.getActivity()).pressEnter();
        // Завершение регистрации (активация)
        locators.activateRegistrationButton.should(interactable).click();
        locators.getSupervisedObjectCardButton.should(interactable).click();
    }
}