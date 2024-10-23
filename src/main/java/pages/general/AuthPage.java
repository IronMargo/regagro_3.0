package pages.general;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class AuthPage {
    public final SelenideElement emailInput = $("input[name=email]");
    public final SelenideElement passwordInput = $("input[type=password]");
    public final SelenideElement authButton = $x("//button[contains(text(),'Авторизация')]");
}
