package pageElements;

import abstractclass.PageElement;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class FindSupervisedObjectPageElement extends PageElement {
    private final SelenideElement nameOfEnterpriseCheckbox = $x("//label[contains(text(),'Наименование площадки')]");
    private final SelenideElement nameOfEnterpriseField = $x("//*[contains(text(), 'Наименование площадки')]/following-sibling::input");
    private final SelenideElement nameOfSupervisedObjectCheckBox = $x("//label[contains(text(),'Наименование ПО')]");
    private final SelenideElement nameOfSupervisedObjectInput = $x("//*[contains(text(), 'Наименование ПО')]/following-sibling::input");

    private final SelenideElement nameOfSupervisedObjectFromCheckBox = $x("//*[contains(text(), 'Объект выбытия')]/following-sibling::div//label[contains(text(),'Наименование ПО')]");
    private final SelenideElement nameOfSupervisedObjectFromInput = $x("//*[contains(text(), 'Объект выбытия')]/following-sibling::div//div[contains(text(),'Наименование ПО')]/following-sibling::input");
    public final SelenideElement findObjectFromButtonModalWindow = $x("//*[contains(text(), 'Объект выбытия')]/following-sibling::div//button[contains(text(),'Найти')]");
    public final SelenideElement findObjectToButtonModalWindow = $x("//*[contains(text(), 'Объект назначения')]/following-sibling::div//button[contains(text(),'Найти')]");
    public final SelenideElement nameOfSupervisedObjectToCheckBox = $x("//*[contains(text(), 'Объект назначения')]/following-sibling::div//label[contains(text(),'Наименование ПО')]");
    public final SelenideElement nameOfSupervisedObjectToInput = $x("//*[contains(text(), 'Объект назначения')]/following-sibling::div//div[contains(text(),'Наименование ПО')]/following-sibling::input");
    private final SelenideElement findButtonModalWindow = $x("//button[contains(text(),'Найти')]");
    private final SelenideElement description = $x("//a[@class='search-results-item d-block']");
    private final SelenideElement chooseButton = $x("//button[contains(text(),'Выбрать')]");

    public void selectSupervisedObject(String supervisedObjectName) {
        nameOfSupervisedObjectCheckBox.should(Condition.interactable).click();
        nameOfSupervisedObjectInput.should(Condition.interactable).setValue(supervisedObjectName);
        findButtonModalWindow.should(Condition.interactable).click();
        description.should(Condition.interactable).click();
        chooseButton.should(Condition.interactable).click();
    }

    public void selectSupervisedObjectFromForDisposal(String supervisedObjectName) {
        nameOfSupervisedObjectFromCheckBox.should(Condition.interactable).click();
        nameOfSupervisedObjectFromInput.should(Condition.interactable).setValue(supervisedObjectName);
        findObjectFromButtonModalWindow.should(Condition.interactable).click();
        description.should(Condition.interactable).click();
        chooseButton.should(Condition.interactable).click();
    }

    public void selectSupervisedObjectToForDisposal(String supervisedObjectName) {
        nameOfSupervisedObjectToCheckBox.should(Condition.interactable).click();
        nameOfSupervisedObjectToInput.should(Condition.interactable).setValue(supervisedObjectName);
        findObjectToButtonModalWindow.should(Condition.interactable).click();
        description.should(Condition.interactable).click();
        chooseButton.should(Condition.interactable).click();
    }
}

