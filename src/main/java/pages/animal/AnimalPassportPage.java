package pages.animal;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class AnimalPassportPage {
    public final SelenideElement animalPassportHeading = $x("//h2[text()='Паспорт животного']");
    public final SelenideElement animalGroupPassportHeading = $x("//h2[text()='Паспорт группы животных']");
    public final SelenideElement actionsButton = $x("//a[contains(text(), 'Действия')]");
    public final SelenideElement disposalButton = $x("//a[contains(text(), 'Выбытие')]");
    public final SelenideElement identificationButton = $x("//a[contains(text(), 'Идентификация')]");
    public final SelenideElement disposalFromGroupButton = $x("//button[contains(text(), 'Вывести животных из группы')]");
    public final SelenideElement identificationNumber = $x("//*[@class='container-fluid show-animal mb-3']//img/parent::div/following-sibling::div[1]");


}
