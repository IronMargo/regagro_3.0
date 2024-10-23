package steps.enterprise;

import abstractclass.Steps;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import pages.enterprise.EnterpriseCardPage;
import steps.lists.EnterpriseListSteps;

import static com.codeborne.selenide.Condition.*;

public class EnterpriseCardSteps extends Steps {
    EnterpriseCardPage enterpriseCardPage = new EnterpriseCardPage();
    public EnterpriseCardSteps() {
        checkLoad();
        enterpriseCardPage.heading.should(appear);
    }
    @Step("Получение наименования ПП")
    public String getNameOfEnterprise() {
        checkLoad();
        return enterpriseCardPage.nameOfEnterprise.should(appear).getText();
    }
    @Step("Редактирование ПП")
    public EnterpriseCardSteps editEnterpriseName(String newName) {
        enterpriseCardPage.actionsButton.should(interactable).click();
        enterpriseCardPage.actionsMenu.should(appear);
        enterpriseCardPage.editEnterpriseButton.should(interactable).click();
        checkLoad();
        EditEnterpriseSteps editEnterpriseSteps = new EditEnterpriseSteps();
        editEnterpriseSteps.getEditEnterprise(newName);
        return new EnterpriseCardSteps();
    }
    @Step("Удаление ПП")
    public EnterpriseListSteps deleteEnterprise() {
        enterpriseCardPage.actionsButton.should(interactable).click();
        enterpriseCardPage.actionsMenu.should(appear);
        enterpriseCardPage.deleteEnterpriseButton.should(interactable).click();
        enterpriseCardPage.messageDelete.should(appear);
        enterpriseCardPage.okButton.should(interactable).click();
        return new EnterpriseListSteps();
    }
}
