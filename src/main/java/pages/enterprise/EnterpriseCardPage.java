package pages.enterprise;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class EnterpriseCardPage {
    public final SelenideElement heading = Selenide.$x("//h2[text()='Карточка площадки']");
    public final SelenideElement actionsButton = $x("//*[contains(text(), 'Действия')]");
    public final SelenideElement actionsMenu = $x("//ul[@class='dropdown-menu show']");
    public final SelenideElement editOwnerButton = $x("//a[contains(text(),'Редактировать владельца')]");
    public final SelenideElement editEnterpriseButton = $x("//a[contains(text(),'Редактировать площадку')]");
    public final SelenideElement deleteEnterpriseButton = $x("//a[contains(text(),'Удалить площадку')]");
    public final SelenideElement okButton = $x("//button[contains(text(),'Да')]");
    public final SelenideElement messageDelete = $x("//h5[contains(text(),'Вы действительно хотите удалить эту площадку?')]");
    public final SelenideElement nameOfEnterprise = $x("//*[@class='row justify-content-between']/child::*[1]");
}
