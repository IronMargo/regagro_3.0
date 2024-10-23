package pageElements;

import abstractclass.PageElement;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class FindOwnerPageElement extends PageElement {
    private final SelenideElement innField = Selenide.$("#searchOwnerStep1 > div.row.align-items-end.mb-3.inn > div:nth-child(1) > input");
    private final SelenideElement okButton = $x("//button[contains(text(),'Ok')]");
    private final SelenideElement findButton = $x("//button[contains(text(),'Найти')]");
    private final SelenideElement description = $x("//a[@class='search-results-item']");
    private final SelenideElement chooseButton = $x("//button[contains(text(),'Вернуться к поиску')]/parent::div/following-sibling::div/button[contains(text(),'Выбрать')]");

    public FindOwnerPageElement() {
    }

    public void getOwner(String ownerInn) {
        innField.should(Condition.interactable).click();
        innField.should(Condition.interactable).setValue(ownerInn);
        findButton.should(Condition.interactable).click();
        description.should(Condition.interactable).click();
        chooseButton.should(Condition.interactable).click();
    }

}
