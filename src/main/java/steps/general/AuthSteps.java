package steps.general;

import abstractclass.Steps;
import enums.User;
import services.ConfigReader;
import pages.general.AuthPage;

import static com.codeborne.selenide.Condition.*;

public class AuthSteps extends Steps {
    private final AuthPage locators = new AuthPage();

    public AuthSteps() {
        locators.emailInput.should(interactable);
        locators.passwordInput.should(interactable);
    }

    //Авторизация в пользовательском интерфейсе
    public HomeSteps auth(User user){
        locators.emailInput.shouldBe(appear);
        locators.emailInput.should(interactable).setValue(ConfigReader.getUserEmail(user.getRole()));
        locators.passwordInput.should(interactable).setValue(ConfigReader.getUserPassword(user.getRole()));
        locators.authButton.should(interactable).click();
        return new HomeSteps();
    }
}
