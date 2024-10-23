package steps.enterprise;

import abstractclass.Steps;
import entities.objects_enterprises.Enterprise;
import io.qameta.allure.Step;
import pageElements.FindOwnerPageElement;
import pages.enterprise.AddEnterprisePage;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.interactable;

public class AddEnterpriseSteps extends Steps {
    AddEnterprisePage addEnterprisePage = new AddEnterprisePage();

    public AddEnterpriseSteps() {
        addEnterprisePage.heading.should(appear);
    }

    @Step("Регистрация ПП")
    public EnterpriseCardSteps getNewEnterprise(Enterprise enterprise, String nameOfEnterprise) {
        // Выбор владельца
        addEnterprisePage.chooseOwnerButton.should(interactable).click();
        FindOwnerPageElement findOwnerPageElement = new FindOwnerPageElement();
        findOwnerPageElement.getOwner(enterprise.getOwnerInn());
        // Наименование ПП
        addEnterprisePage.nameOfEnterpriseField.should(interactable).setValue(nameOfEnterprise);
        // Тип ПП
        addEnterprisePage.typeOfEnterprise.should(interactable).click();
        addEnterprisePage.input.should(interactable).setValue(enterprise.getTypeOfEnterprise()).pressEnter();
        // Адрес ПП
        addEnterprisePage.districtSelection.should(interactable).click();
        addEnterprisePage.input.should(interactable).setValue(enterprise.getDistrict()).pressEnter();
        checkLoad();
        addEnterprisePage.citySelection.should(interactable).click();
        addEnterprisePage.input.should(interactable).setValue(enterprise.getCity()).pressEnter();
        if (enterprise.getStreet() != null) {
            addEnterprisePage.streetSelection.should(interactable).click();
            addEnterprisePage.input.should(interactable).setValue(enterprise.getStreet()).pressEnter();
        }
        addEnterprisePage.houseNumber.should(interactable).setValue(enterprise.getHouse()).pressEnter();
        // Территория ПП
        addEnterprisePage.serviceAreaSelection.should(interactable).click();
        addEnterprisePage.input.should(interactable).setValue(enterprise.getServiceArea()).pressEnter();
        // Завершение регстрации (активация)
        addEnterprisePage.activateRegistrationButton.should(interactable).click();
        checkLoad();
        return new EnterpriseCardSteps();
    }
}