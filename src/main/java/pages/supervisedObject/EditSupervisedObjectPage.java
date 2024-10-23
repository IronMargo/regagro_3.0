package pages.supervisedObject;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class EditSupervisedObjectPage {
    public final SelenideElement nameOfSupervisedObjectInput = $x("//textarea[@name='name']");
    public final SelenideElement saveButton = $x("//button[contains(text(), 'Сохранить')]");
    public final SelenideElement heading = Selenide.$x("//h2[text()='Редактирование поднадзорного объекта']");

}
