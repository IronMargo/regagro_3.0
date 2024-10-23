package pages.inventory;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class SetDrugFor4dPage {
    public final SelenideElement addButton = $x("//*[contains(text(), 'Добавить')]");
    public final SelenideElement drugNameSelect = $x("//select[@name='treatment_products_id']/following-sibling::*//*[@class='select2-selection select2-selection--single']");
    public final SelenideElement okButton = $x("//button[contains(text(), 'Ok')]");
    public final SelenideElement input = $x("//input[@class='select2-search__field']");
    public final SelenideElement methodSelect = $x("//select[@name='treatment_method_id']/following-sibling::*//*[@class='select2-selection select2-selection--single']");
    public final SelenideElement inputSolutionSpent = $x("//*[@id='solution_spent']/input");
    public final SelenideElement volumeSelect = $x("//select[@name='solutionSpentVolume']/following-sibling::*//*[@class='select2-selection select2-selection--single']");
    public final SelenideElement inputConcentration = $x("//*/label[contains(text(), 'Концентрация')]/following::*/input");
}
