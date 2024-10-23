package steps.lists;

import abstractclass.Steps;
import com.codeborne.selenide.WebDriverRunner;
import entities.objects_enterprises.Enterprise;
import io.qameta.allure.Step;
import pages.lists.ListsPage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class EnterpriseListSteps extends Steps {
    ListsPage locators = new ListsPage();
    private static final String EXPECTED_URL = "https://v3.dev.regagro.ru/enterprises";

    public EnterpriseListSteps() {
        checkLoad();
        locators.enterprisesListHeading.should(appear);
    }
    @Step("Проверка, что открыта страница Реестр выбытий")
    public boolean isOnEnterprisesListPage() {
        return WebDriverRunner.url().equals(EXPECTED_URL);
    }
}
