package steps.animal;

import abstractclass.Steps;
import entities.disposals.DisposalList;
import io.qameta.allure.Step;
import pageElements.DisposalFromGroupPageElement;
import pages.animal.AnimalPassportPage;

import static com.codeborne.selenide.Condition.*;

public class AnimalGroupPassportSteps extends Steps {
    private final AnimalPassportPage animalPassportPage = new AnimalPassportPage();

    public AnimalGroupPassportSteps() {
        animalPassportPage.animalGroupPassportHeading.should(appear);
    }

    // Получение номера
    public String getIdentificationNumber() {
        return animalPassportPage.identificationNumber.should(visible).getText();
    }
    @Step("Вывод животного из группы")
    public void disposeAnimalFromGroupWithDisposal(DisposalList disposalList, String countForDisposal) {
        animalPassportPage.actionsButton.should(interactable).click();
        animalPassportPage.disposalFromGroupButton.should(interactable).click();
        DisposalFromGroupPageElement disposalFromGroupPageElement = new DisposalFromGroupPageElement();
        disposalFromGroupPageElement.setDisposalParameters(disposalList, countForDisposal);
        checkLoad();
    }
}
