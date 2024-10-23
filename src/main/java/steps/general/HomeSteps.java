package steps.general;

import abstractclass.Steps;
import enums.User;
import services.RequestSpecificationCreator;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import pages.general.BasePage;
import steps.animal.AnimalGroupPassportSteps;
import steps.animal.AnimalPassportSteps;

import java.util.Map;

import static com.codeborne.selenide.Condition.*;

public class HomeSteps extends Steps {
    private final BasePage basePage = new BasePage();

    @Step("Смена юзера")
    public HomeSteps changeUser(User newUser){
        AuthSteps authSteps = logout();
        HomeSteps homeSteps = authSteps.auth(newUser);
        basePage.mapButton.should(visible);
        return homeSteps;
    }
    @Step("Выход из профиля")
    public AuthSteps logout() {
        basePage.user.should(interactable).click();
        basePage.logout.should(interactable).click();
        return new AuthSteps();
    }
    @Step("Поиск индивидуального животного")
    public AnimalPassportSteps getFoundAnimal(String number) {
        basePage.findAnimalField.should(interactable).click();
        basePage.input.should(interactable).setValue(number);
        basePage.result.should(visible);
        basePage. input.pressEnter();
        return new AnimalPassportSteps();
    }
    @Step("Поиск группы животных")
    public AnimalGroupPassportSteps getFoundAnimalGroup(String number) {
        basePage.findAnimalField.should(interactable).click();
        basePage.input.should(visible).setValue(number);
        basePage.result.should(visible);
        basePage.input.pressEnter();
        return new AnimalGroupPassportSteps();
    }
    @Step("Выход из профиля с помощью АПИ")
    public void logoutWithApi(Map<String, String> cookies) {
        // Делаем POST-запрос на https://v3.dev.regagro.ru/logout
        RequestSpecification request = RestAssured.given()
                .spec(RequestSpecificationCreator.getReqSpec(cookies))
                .log().all();
        Response response = request.post("logout");
        int actualStatusCode = response.getStatusCode();
        // Проверяем статус-код
        Assertions.assertEquals(302, actualStatusCode, "Ошибка при выполнении запроса: https://v3.dev.regagro.ru/logout");
    }
}
