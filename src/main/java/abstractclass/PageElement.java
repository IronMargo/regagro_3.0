package abstractclass;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public abstract class PageElement {
    protected static SelenideElement closeButton = $x("//button[@class='btn-close position-absolute']");

    public static void closeModalWindow() {
        Selenide.sleep(3500);
        if (closeButton.isDisplayed()) {
            closeButton.should(Condition.interactable).click();
        }
    }
}
