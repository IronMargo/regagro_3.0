package steps.inventory;

import abstractclass.Steps;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import entities.tasks.Inventory;
import entities.tasks.Task;
import io.qameta.allure.Step;
import pageElements.inventories.acts.Setting4dActPageElement;
import pageElements.inventories.acts.SettingActPageElement;
import pageElements.inventories.acts.SettingDiagnosticActPageElement;
import pageElements.inventories.acts.SettingVaccinationActPageElement;
import pages.inventory.PreviewActPage;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.interactable;
import static com.codeborne.selenide.Selenide.$x;

public class PreviewActSteps extends Steps {
    PreviewActPage previewActPage = new PreviewActPage();

    public PreviewActSteps(Inventory inventory, Task task) {
        SelenideElement heading;
        if (task.getType().equals("Диагностические исследования")) {
            heading = Selenide.$x("//div[@class='col-6']/h5[contains(text(), '" + inventory.getNameOfBatchTwo() + "')]");
        } else {
            heading = previewActPage.heading;
        }
        heading.should(appear);
    }
    @Step("Настройка предварительного акта")
    public void settingPreviewAct(Inventory inventory, Task task) {
        // Дата
        previewActPage.dateField.should(interactable).click();
        //previewActPage.dateFieldInput.should(interactable).setValue(inventory.getDate()).pressEnter();
        $x("//span[@class='flatpickr-day today']").should(Condition.interactable).click();
        // Детальная настройка
        previewActPage.settingButton.should(interactable).click();
        SettingActPageElement settingActPageElement = new SettingActPageElement();
        switch (task.getType()) {
            case "Дезинфекция":
            case "Дератизация":
            case "Дезинсекция":
                Setting4dActPageElement setting4DActPageElement = settingActPageElement.getSettingActPageElement("4д");
                setting4DActPageElement.settingPreviewActDetails(inventory, task);
                break;
            case "Вакцинация":
                SettingVaccinationActPageElement settingVaccinationActPageElement = settingActPageElement
                        .getSettingActPageElement("Вакцинация");
                settingVaccinationActPageElement.settingPreviewActDetailsVaccination(inventory);
                break;
            case "Диагностические исследования":
                SettingDiagnosticActPageElement settingDiagnosticActPageElement = settingActPageElement
                        .getSettingActPageElement("Диагностические исследования");
                settingDiagnosticActPageElement.settingPreviewActDetailsDiagnostic(inventory);
        }
        // Перевод "В работу"
        previewActPage.toWorkButton.should(interactable).click();
    }
}
