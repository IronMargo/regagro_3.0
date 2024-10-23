package steps.disposal;

import abstractclass.Steps;
import assertions.DisposalAssertions;
import enums.DisposalReasons;
import entities.disposals.DisposalList;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import pageElements.FindSupervisedObjectPageElement;
import pages.disposal.CreateDisposalListPage;
import steps.animal.AnimalGroupPassportSteps;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class CreateDisposalListSteps extends Steps {
    private final DisposalAssertions disposalsAssertions = new DisposalAssertions();
    private final FindSupervisedObjectPageElement findSupervisedObjectPageElement = new FindSupervisedObjectPageElement();
    CreateDisposalListPage locators = new CreateDisposalListPage();

    @Step("Регистрация и активация выбытия из пасспорта животного по окончательной причине ")
    public void getActivatedDisposalFromAnimalPassport(DisposalReasons disposalReasons) {
        locators.headerRegistrationDisposal.should(appear);
        // Причина выбытия
        locators.disposalReasonSelect.should(interactable).click();
        locators.input.should(interactable).setValue(disposalReasons.getReason()).pressEnter();
        // Регистрация списка выбытия
        locators.registrationDisposalListButton.should(interactable).click();
        locators.headerDisposalCard.should(appear);
        // Составление списка выбытия (шаг для указанных ниже причин)
        if (disposalReasons.getReason().equals("Падеж") || disposalReasons.getReason().equals("Фактический убой")
                || disposalReasons.getReason().equals("Утеряно") || disposalReasons.getReason().equals("Личные нужды")) {
            locators.makeAtListButton.should(interactable).click();
        }
        // Активация Списка выбытия
        activateDisposalList(disposalReasons.getReason());
    }

    @Step("Регистрация выбытия из сайдбара")
    public void registrationDisposalList(DisposalList disposalList) {
        locators.headerRegistrationDisposal.should(appear);
        checkLoad();
        // Причина
        locators.disposalReasonSelect.should(interactable).click();
        locators.input.should(interactable).setValue(disposalList.getReason()).pressEnter();
        // Указание уточнения причины выбытия
        if (disposalList.getReason().equals("Падеж") || disposalList.getReason().equals("Вынужденный убой") ||
                disposalList.getReason().equals("Временное перемещение")) {
            locators.disposalReasonClarificationSelect.should(interactable).click();
            locators.input.should(interactable).setValue(disposalList.getReasonClarification()).pressEnter();
        }
        // Выбор объекта Выбытия
        locators.supervisedObjectFromButton.should(interactable).click();
        findSupervisedObjectPageElement.selectSupervisedObjectFromForDisposal(disposalList.getSupervisedObjectFrom());
        checkLoad();
        // Выбор объекта назначения
        if (disposalList.getReason().equals("Продажа") || disposalList.getReason().equals("Временное перемещение") ||
                disposalList.getReason().equals("Направление на убой")) {
            locators.supervisedObjectToButton.should(interactable).click();
            findSupervisedObjectPageElement.selectSupervisedObjectToForDisposal(disposalList.getSupervisedObjectTo());
        } else if (disposalList.getReason().equals("Перемещение между объектами владельца")) {
            checkLoad();
            locators.supervisedObjectToSelectForSameOwner.should(interactable).click();
            locators.input.should(interactable).setValue(disposalList.getSupervisedObjectTo()).pressEnter();
        }
        checkLoad();
        // Выбор животного
        locators.animalKindSelect.should(interactable).click();
        locators.input.should(interactable).setValue(disposalList.getAnimalKind()).pressEnter();
        locators.registrationDisposalListButton.should(interactable).click();
        locators.headerDisposalCard.should(appear);
    }

    @Step("Выбор животного для выбытия")
    public void addAnimalForDisposal(DisposalList disposalList) {
        locators.headerDisposalCard.should(appear);
        checkLoad();
        locators.addAnimalButton.should(interactable).click();

        // Фильтрация по номеру
        locators.filtersButton.should(interactable).click();
        locators.animalNumberInput.should(interactable).setValue(disposalList.getAnimalNumber()).pressEnter();
        locators.applyButton.should(interactable).click();

        // Выбор найденного животного
        $x("//div[1]/div[contains(text(), '" + disposalList.getAnimalNumber() + "')]//preceding-sibling::div/input[@type='checkbox']")
                .should(interactable).click();
        locators.addToListButton.should(interactable).click();
        locators.turnToListButton.should(interactable).click();
        if (disposalList.getReason().equals("Падеж") || disposalList.getReason().equals("Фактический убой")
                || disposalList.getReason().equals("Утеряно") || disposalList.getReason().equals("Личные нужды")) {
            locators.makeAtListButton.should(interactable).click();
        }
    }
    @Step("Активация списка выбытия")
    public void activateDisposalList(String reason) {
        locators.activateButton.should(interactable).click();
        if (reason.equals("Падеж") || reason.equals("Фактический убой")
                || reason.equals("Утеряно") || reason.equals("Личные нужды")) {
            Assertions.assertTrue(disposalsAssertions.isMessageSuccess());
            turnToDisposalList();
        }
    }
    @Step("Переход на страницу Список выбытия")
    public void turnToDisposalList() {
        checkLoad();
        locators.disposalListButton.should(appear).click();
    }
    @Step("Получение ГУИД выбытия")
    public String getGuidOfDisposal() {
        locators.guidInput.should(appear);
        return locators.guidInput.getAttribute("value");
    }
    @Step("Переход на страницу Реестр выбытий")
    public void turnToDisposalsList() {
        locators.okButton.should(interactable).click();
    }
}