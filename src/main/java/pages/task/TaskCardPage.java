package pages.task;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class TaskCardPage {
    public final SelenideElement animalsTab = $x("//*[contains(text(), 'Животные')]/parent::button");
    public final SelenideElement applyButton = $x("//button[contains(text(), 'Принять')]");
    public final SelenideElement rejectButton = $x("//button[contains(text(), 'Отклонить')]");
    public final SelenideElement createInventoriesButton = $x("//button[contains(text(), 'Создать опись')]");
    public final SelenideElement heading = $x("//h2[text()='Карточка задания']");
    public final SelenideElement statusInWork = $x("//*[contains(text(), 'В работе')]");
}
