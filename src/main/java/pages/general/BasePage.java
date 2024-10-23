package pages.general;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class BasePage {
    public final SelenideElement user = $x("/html/body/nav/div/div[2]/ul[2]/li[2]/a");
    public final SelenideElement logout = $x("//*[contains(text(), 'Выход')]");
    public final SelenideElement mapButton = $x("//button[contains(text(),'Показать карту')]");
    public final SelenideElement findAnimalField = $x("//*[@title='Поиск животного (рег. номер)']");
    public final SelenideElement input = $x("//input[@class='select2-search__field']");
    public final SelenideElement result = $x("//li[@class='select2-results__option select2-results__option--selectable select2-results__option--highlighted']");

}
