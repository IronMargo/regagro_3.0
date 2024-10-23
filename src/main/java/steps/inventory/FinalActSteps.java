package steps.inventory;


import abstractclass.Steps;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import entities.tasks.Inventory;
import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import pageElements.inventories.acts.Setting4dActPageElement;
import pageElements.inventories.acts.SettingActPageElement;
import pageElements.inventories.acts.SettingVaccinationActPageElement;
import pages.inventory.FinalActPage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

public class FinalActSteps extends Steps {
    FinalActPage finalActPage = new FinalActPage();

    public FinalActSteps(String taskType) {
        if(taskType.equals("Вакцинация") | taskType.equals("Диагностические исследования") | taskType.equals("Дегельминтизация")) {
            finalActPage.heading.should(appear);
        }
    }
    @Step("Настройка окончательного акта для 4д")
    public void settingFinalAct4d(Inventory inventory) {
        Selenide.sleep(3000);
        finalActPage.dateActField.should(interactable).click();
        //finalActPage.dateActField.should(interactable).click();
        $x("//span[@class='flatpickr-day today']").should(Condition.interactable).click();
        finalActPage.settingButton.should(interactable).click();
        // Получение страницы (модального окна) для детальной настройки актов
        SettingActPageElement settingActPageElement = new SettingActPageElement();
        Setting4dActPageElement setting4dActPageElement = settingActPageElement
                .getSettingActPageElement("4д");
        // Датальная настройка окончательного акта (модальное окно)
        setting4dActPageElement.settingFinalActDetails4d(inventory);
        finalActPage.nextButton.should(interactable).click();
    }
    @Step("Настройка окончательного акта для Вакцинации")
    public void settingFinalActVaccination(Inventory inventory) {
        finalActPage.settingButton.should(interactable).click();
        // Получение страницы (модального окна) для детальной настройки актов
        SettingActPageElement settingActPageElement = new SettingActPageElement();
        SettingVaccinationActPageElement settingVaccinationActPageElement = settingActPageElement
                .getSettingActPageElement("Вакцинация");
        // Датальная настройка окончательного акта (модальное окно)
        settingVaccinationActPageElement.settingFinalActDetails(inventory);
        finalActPage.nextButton.should(interactable).click();
    }
    @Step("Настройка окончательного акта для диагностики")
    public void settingFinalActDiagnostic(Inventory inventory) {
        finalActPage.addTubeNumber.should(interactable).click();
        $x("//h2[contains(text(), 'Указание номеров')]").should(appear);

        // Активен таб "Без исключенных"
        $x("//button[@class='btn filter-item active'][contains(text(), 'Без исключенных')]")
                .should(appear);

        // Установить значение внутри элемента DIV
        checkLoad();
        ElementsCollection tubeFields = $$("div > div.tabulator-cell.tabulator-editable");
        JavascriptExecutor js = (JavascriptExecutor) WebDriverRunner.getWebDriver();

        // Получить объект Tabulator (предполагаем, что таблица имеет класс 'myTableClass')
        Object tabulator = js.executeScript("return $('.tabulator')[0].tabulator");

        // Обновить значения в ячейках таблицы с помощью API Tabulator
        for (int i = 0; i < tubeFields.size(); i++) {
            js.executeScript("arguments[0].updateData({column: '№ пробирки', value: '" + (i + 1) + "'}, 'row', " + i + ");", tabulator);
        }
        checkLoad();
        finalActPage.saveButton.should(interactable).click();

        finalActPage.dateOfSendingInput.should(interactable).click();
        checkLoad();
        $x("//div[@class='flatpickr-calendar animate arrowBottom arrowLeft open']//span[@class='flatpickr-day today']")
                .click();
        finalActPage.nextButton.should(interactable).click();
    }
}
