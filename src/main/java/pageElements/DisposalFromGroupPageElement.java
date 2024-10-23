package pageElements;

import abstractclass.PageElement;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import entities.disposals.DisposalList;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class DisposalFromGroupPageElement extends PageElement {
    private static final SelenideElement heading = $x("//h4[contains(text(), 'Вывести животных из группы')]");
    private static final SelenideElement disposalReasonSelect = $x("//select[@name='disposal_reason_id']/following-sibling::span//span[@aria-labelledby='select2--container']");
    private static final SelenideElement clarificationReasonSelect = $x("//select[@name='clarification_id']/following-sibling::span//span[@aria-labelledby='select2--container']");
    private static final SelenideElement female = $x("//*[contains(text(), 'Самки')]/div/input");
    private static final SelenideElement male = $x("//*[contains(text(), 'Самцы')]/div/input");
    private static final SelenideElement count = $x("//*[contains(text(), 'Количество животных ')]/div/input");
    private static final SelenideElement continueButton = $x("//button[contains(text(), 'Продолжить')]");
    public final SelenideElement input = $x("//input[@class='select2-search__field']");

    public DisposalFromGroupPageElement() {
        heading.should(Condition.appear);
    }

    // Выбор пола
    public String selectGenderForDisposal(int countOfMale) {
        if (countOfMale > 0) {
            return "Самцы";
        } else return "Самки";
    }

    @Step("Установка параметров выбытия из группы")
    public void setDisposalParameters(DisposalList disposalList, String countForDisposal) {
        // Указание причины выбытия
        disposalReasonSelect.should(Condition.interactable).click();
        input.should(Condition.interactable).setValue(disposalList.getReason()).pressEnter();
        // Указание уточнения причины выбытия
        if (disposalList.getReason().equals("Падеж") || disposalList.getReason().equals("Вынужденный убой")) {
            clarificationReasonSelect.should(Condition.interactable).click();
            input.should(Condition.interactable).setValue(disposalList.getReasonClarification()).pressEnter();
        }
        // Указание пола
        String gender = selectGenderForDisposal(disposalList.getInitialCountOfMale());
        // Указание кол-во голов для выбытия
        if (gender.equals("Самцы")) {
            male.should(Condition.interactable).setValue(countForDisposal);
            if (!(disposalList.getInitialCountOfFemale() == 0)) {
                female.should(Condition.interactable).setValue("0");
            }
        } else {
            female.should(Condition.interactable).setValue(countForDisposal);
            if (!(disposalList.getInitialCountOfMale() == 0)) {
                male.should(Condition.interactable).setValue("0");
            }
        }
        // Активация выбытия из модального окна
        continueButton.should(Condition.interactable).click();
    }
}
