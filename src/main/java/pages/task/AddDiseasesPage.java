package pages.task;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class AddDiseasesPage {
    public final SelenideElement diseaseSelect = $x("//*[contains(text(), 'Заболевание')]/following-sibling::*//*[@class='select2-selection select2-selection--single']");
    public final SelenideElement input = $x("//input[@class='select2-search__field']");
    public final SelenideElement nextButton = $x("//button[contains(text(), 'Далее')]");
    public final SelenideElement diseasesGroup = $x("//label[contains(text(), 'Цестодозы')]");
    public final SelenideElement accordionButtonFirstGroup = $x("//label[contains(text(), 'Нематодозы')]/ancestor::button");
    public final SelenideElement heading = Selenide.$x("//h4[text()='Дополнительная информация']");
}
