package pages.supervisedObject;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class SupervisedObjectCardPage {
    public final SelenideElement actionsButton = $x("//*[contains(text(), 'Действия')]");
    public final SelenideElement actionsMenu = $x("//ul[@class='dropdown-menu show']");
    public final SelenideElement editOwnerButton = $x("//a[contains(text(),'Редактировать владельца')]");
    public final SelenideElement editSupervisedObjectButton = $x("//a[contains(text(),'Редактировать поднадзорный объект')]");
    public final SelenideElement deleteSupervisedObjectButton = $x("//a[contains(text(),'Удалить поднадзорный объект')]");
    public final SelenideElement okButton = $x("//button[contains(text(),'Да')]");
    public final SelenideElement messageDelete = $x("//h5[contains(text(),'Вы действительно хотите удалить этот объект?')]");
    public final SelenideElement nameOfSupervisedObject = $x("//*[@class='row justify-content-between']/child::*[1]");
    public final SelenideElement heading = Selenide.$x("//h2[text()='Карточка поднадзорного объекта']");
}
