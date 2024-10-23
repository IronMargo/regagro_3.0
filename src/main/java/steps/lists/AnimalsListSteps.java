package steps.lists;
import abstractclass.Steps;
import io.qameta.allure.Step;
import pages.lists.ListsPage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class AnimalsListSteps extends Steps {
    ListsPage listsPage = new ListsPage();

    public AnimalsListSteps() {
        listsPage.animalsListHeading.should(appear);
    }
    @Step("Проверка, что животное находится в Реестре животных")
    public boolean isAnimalInList(String animalNumber) {
        return $x("//div[contains(text(), '" + animalNumber + "')]").should(visible).isDisplayed();
    }

}
