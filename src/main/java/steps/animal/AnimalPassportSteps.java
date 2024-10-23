package steps.animal;

import abstractclass.Steps;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import pages.animal.AnimalPassportPage;
import steps.disposal.CreateDisposalListSteps;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class AnimalPassportSteps  extends Steps {
    private final AnimalPassportPage animalPassportPage = new AnimalPassportPage();
    private final SelenideElement identificationNumber = $x("//div[@class='container-fluid show-animal mb-3']//img/parent::div/following-sibling::div[1]");

    public AnimalPassportSteps() {
        animalPassportPage.animalPassportHeading.should(appear);
    }

    // Получение номера
    public String getIdentificationNumber() {
        return animalPassportPage.identificationNumber.should(visible).getText();
    }
    @Step("Переход к регистрации списка выбытия из паспорта группы")
    public CreateDisposalListSteps disposalAnimalFromPassport (){
        animalPassportPage.actionsButton.should(interactable).click();
        animalPassportPage.disposalButton.should(interactable).click();
        return new CreateDisposalListSteps();
    }
    @Step("Переход к идентификации")
    public IdentificationSteps getIdentificationSteps(){
        animalPassportPage.actionsButton.should(interactable).click();
        animalPassportPage.identificationButton.should(interactable).click();
        return new IdentificationSteps();
    }
}

