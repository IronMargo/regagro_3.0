package pages.task;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class AddKindsPage {
    public final SelenideElement setKindsButton = $x("//button[contains(text(), 'Установить виды')]");
    public final SelenideElement cleanButton = $x("//*[contains(text(), 'Очистить')]");
    public final SelenideElement kindsSelect = $x("//*[@title='Выберите вид']");
    public final SelenideElement input = $x("//input[@class='select2-search__field']");
    public final SelenideElement nextButton = $x("//button[contains(text(), 'Далее')]");
    public final SelenideElement editExecutorButton = $x("//button[contains(text(), 'Редактировать соисполнителей')]");
    public final SelenideElement executorsSelect = $x("//label[contains(text(), 'Список сотрудников организации')]/parent::*//*[@class='select2-selection select2-selection--single']");
    public final SelenideElement addButton = $x("//label[contains(text(), 'Список сотрудников организации')]/parent::*//button[contains(text(), 'Отмена')]/following-sibling::button");
    public final SelenideElement heading = Selenide.$x("//h4[contains(text(), 'Настройки выполнения')]");
    public final SelenideElement checkLoad = $x("//*[@class='modal-busy d-none']");
}
