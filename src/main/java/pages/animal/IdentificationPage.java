package pages.animal;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class IdentificationPage {
    public final SelenideElement heading = $x("//h2[text()='Идентификация животного']");
    public final SelenideElement changeButton = $x("//button[contains(text(), 'Изменить')]/i");
    public final SelenideElement archiveButton = $x("//button[contains(text(), 'Архивировать')]/i");
    public final SelenideElement addButton = $x("//*[contains(text(), 'Добавить')]/i");
}
