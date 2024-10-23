package pages.general;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class SidebarPage {

    // Сайдбар

    // Карта объектов
    public final SelenideElement mapAccordionButton = $x("//*[contains(text(), 'Карта объектов')]/parent::a");

    //Регистрация
    public final SelenideElement registrationAccordionButton = $x("//button[contains(text(), 'Регистрация')]");
    public final SelenideElement enterpriseCreateButton = $x("//a[@href='/enterprises/add']");
    public final SelenideElement supervisedObjectCreateButton = $x("//a[@href='/supervised_objects/create']");
    public final SelenideElement animalCreateButton = $x("//a[@href='/animals/add']");
    public final SelenideElement animalGroupCreateButton = $x("//a[@href='/animal_groups/add']");

    // Реестры
    public final SelenideElement registryAccordionButton = $x("//button[contains(text(), 'Реестры')]");
    public final SelenideElement enterprisesListButton = $x("//a[@href='/enterprises']");
    public final SelenideElement supervisedObjectListButton = $x("//a[@href='/supervised_objects']");
    public final SelenideElement terminatedAnimalsListButton = $x("//a[@href='/terminated_animals']");
    public final SelenideElement animalsListButton = $x("//a[@href='/animals']");

    // Список заданий
    public final SelenideElement tasksAccordionButton = $x("//button[contains(text(), 'Задания')]");
    public final SelenideElement tasksListAccordionButton = $x("//a[@href='/tasks']");

    // Выбытие

    public final SelenideElement disposalAccordionButton = $x("//button[contains(text(), 'Выбытие')]");
    public final SelenideElement createDisposalListButton = $x("//a[@href='/disposal_lists/create']");


}
