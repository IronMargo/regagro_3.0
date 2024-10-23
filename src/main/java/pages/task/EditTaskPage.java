package pages.task;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class EditTaskPage {
    public final SelenideElement sendButton = $x("//button[contains(text(), 'Отправить задание')]");
    public final SelenideElement messageSuccess = $x("//h2[contains(text(), 'отправлено')]");
    public final SelenideElement toCardButton = $x("//button[contains(text(), 'В карточку задания')]");
    public final SelenideElement heading = Selenide.$x("//h2[text()='Новое задание']");

}
