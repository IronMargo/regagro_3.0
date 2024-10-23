package pageElements.inventories.acts;

import abstractclass.PageElement;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SettingActPageElement extends PageElement {
    protected final SelenideElement postField = $x("//*[contains(text(), 'Должность')]/parent::div/div[2]//input");
    protected final SelenideElement nameField = $x("//*[contains(text(), 'ФИО')]/parent::div/div[3]/following-sibling::div//input");
    protected final SelenideElement okButton = $x("//button[contains(text(), 'Ok')]");
    protected final SelenideElement antisepticSelect = $x("//select[@name='antiseptic_id']/following-sibling::span//span[@class='select2-selection select2-selection--single']");
    protected final SelenideElement countOfAntisepticInput = $x("//*[contains(text(), 'Количество *:')]/parent::div//input");
    protected final SelenideElement cottonInput = $x("//*[contains(text(), 'Ваты')]/parent::div//input");
    protected final SelenideElement syringeInput = $x("//*[contains(text(), 'Шприцов')]/parent::div//input");
    protected final SelenideElement glovesInput = $x("//*[contains(text(), 'Перчаток')]/parent::div//input");
    protected final SelenideElement inputSimple = $x("//input[@class='select2-search__field']");

    public SettingActPageElement() {
        SelenideElement heading1 = Selenide.$x("//h4[text()='Детальная настройка акта']");
        SelenideElement heading2 = Selenide.$x("//h4[text()='Настройка предварительного акта']");
        assertTrue(heading1.isDisplayed() || heading2.isDisplayed());
    }

    // Универсальный метод получения страниц (модальных окон) для детальной настройки актов в зависимости от типа Ветмероприятия
    public <T extends SettingActPageElement> T getSettingActPageElement(String eventType) {
        switch (eventType) {
            case "Вакцинация":
                return (T) new SettingVaccinationActPageElement();
            case "Аллергические исследования":
                return (T) new SettingAllergicActPageElement();
            case "Диагностические исследования":
                return (T) new SettingDiagnosticActPageElement();
            case "4д":
                return (T) new Setting4dActPageElement();
            default:
                throw new IllegalArgumentException("Неизвестный тип мероприятия: " + eventType);
        }
    }
}