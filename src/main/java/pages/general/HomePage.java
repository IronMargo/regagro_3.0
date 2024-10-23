package pages.general;

import com.codeborne.selenide.Condition;

public class HomePage {
    BasePage locators = new BasePage();

    public HomePage() {
        locators.mapButton.should(Condition.appear);
    }
}
