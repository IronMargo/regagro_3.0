package pages.lists;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class ListsPage {
    public final SelenideElement disposalsListHeading = Selenide.$x("//h2[text()='Реестр выбытий']");
    public final SelenideElement animalsListHeading = Selenide.$x("//h2[text()='Реестр животных']");
    public final SelenideElement enterprisesListHeading = Selenide.$x("//h2[text()='Реестр производственных площадок']");
    public final SelenideElement supervisedObjectsHeading = Selenide.$x("//h2[text()='Реестр поднадзорных объектов']");
    public final SelenideElement tasksHeading = Selenide.$x("//h2[text()='Задания']");
    public final SelenideElement filtersButton = $x("//button[contains(text(), 'Фильтры')]");
    public final SelenideElement generalValue = $x("//button[contains(text(), 'Общие')]");
    public final SelenideElement enterpriseName = $x("//*[contains(text(), 'Наименование объекта')]/*/input");
    public final SelenideElement okButton = $x("//button[@id='applyFilterBtn']");
    public final SelenideElement addTaskButton = $x("//a[contains(text(), 'Добавить')]");

}
