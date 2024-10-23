package pages.supervisedObject;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class AddSupervisedObjectPage {
    public final SelenideElement chooseEnterpriseButton = $x("//button[contains(text(),'Выбрать площадку')]");
    public final SelenideElement chooseOwnerButton = $x("//button[contains(text(),'Выбрать владельца')]");
    public final SelenideElement nameOfSupervisedObjectInput = $x("//textarea[@name='name']");
    public final SelenideElement typeOfSupervisedObjectSelect = $x("//select[@name='type_id']/following-sibling::*/*/*[@aria-controls='select2--container']");
    public final SelenideElement input = $x("//*[@class='select2-search__field']");
    public final SelenideElement activityOfSupervisedObjectSelect = $x("//select[@name='activities']/following-sibling::*/*/*[@aria-controls='select2--container']");
    public final SelenideElement activateRegistrationButton = $x("//button[contains(text(), 'Завершить регистрацию')]");
    public final SelenideElement getSupervisedObjectCardButton = $x("//button[contains(text(), 'Перейти в карточку')]");
    public final SelenideElement heading = $x("//h2[text()='Регистрация поднадзорного объекта']");

}
