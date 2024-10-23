package pages.inventory;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class SetOfBatchPage {
    public final SelenideElement addBatchButton = $x("//button[contains(text(), 'Партия')]");
    public final SelenideElement nameOfBatchInput = $x("//h3[contains(text(), 'Партия 2')]/ancestor::*[@class='mb-3']//*[contains(text(), 'Наименование партии')]/following-sibling::*//input");
    public final SelenideElement input = $x("//input[@class='select2-search__field']");
    public final SelenideElement materialResearchTwoSelect = $x("//h3[contains(text(), 'Партия 2')]/ancestor::*[@class='mb-3']/descendant::*[contains(text(), 'Материал исследования')]//*[@class='select2-selection select2-selection--single']");
    public final SelenideElement methodResearchTwoSelect = $x("//h3[contains(text(), 'Партия 2')]/ancestor::*[@class='mb-3']/descendant::*[contains(text(), 'Метод исследования')]//*[@class='select2-selection select2-selection--single']");
    public final SelenideElement diseaseBatchSelect = $x("//h3[contains(text(), 'Партия 2')]/ancestor::*[@class='mb-3']/descendant::select[@name='disease_id']/following-sibling::*//*[@class='select2-selection select2-selection--single']");
    public final SelenideElement researchCountTwoInput = $x("//h3[contains(text(), 'Партия 2')]/ancestor::*[@class='mb-3']/descendant::*[contains(text(), 'Количество исследований')]/parent::*/following-sibling::*/descendant::input");
    public final SelenideElement saveButton = $x("//button[contains(text(), 'Сохранить и сформировать партии')]");
    public final SelenideElement messageSuccess = $x("//h4[contains(text(), 'Партии сформированы')]");
    public final SelenideElement okButton = $x("//h4[contains(text(), 'Партии сформированы')]/following-sibling::*/*/button");

}
