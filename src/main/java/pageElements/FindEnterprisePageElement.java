package pageElements;

import abstractclass.PageElement;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import entities.objects_enterprises.SupervisedObject;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class FindEnterprisePageElement extends PageElement {

    private final SelenideElement heading = $x("//h4[contains(text(), 'Поиск площадки')]");
    private final SelenideElement nameOfEnterpriseCheckBox = $x("//label[contains(text(), 'Наименование площадки')]");
    private final SelenideElement nameOfEnterpriseInput = $x("//small[contains(text(), 'Наименование площадки')]/following-sibling::input");
    private final SelenideElement findButton = $x("//button[contains(text(),'Найти')]");
    private final SelenideElement description = $x("//a[@class='search-results-item']");
    private final SelenideElement chooseButton = $x("//button[contains(text(),'Вернуться к поиску')]/parent::div/following-sibling::div/button[contains(text(),'Выбрать')]");

    public void getEnterprise(SupervisedObject supervisedObject) {
        nameOfEnterpriseCheckBox.should(Condition.interactable).click();
        nameOfEnterpriseInput.should(Condition.interactable).setValue(supervisedObject.getEnterpriseName());
        findButton.should(Condition.interactable).click();
        description.should(Condition.interactable).click();
        chooseButton.should(Condition.interactable).click();
    }
}
