package steps.inventory;

import abstractclass.Steps;
import com.codeborne.selenide.Condition;
import entities.tasks.Inventory;
import entities.tasks.Task;
import io.qameta.allure.Step;
import pageElements.inventories.action.AddingBatchPageElement;
import pageElements.inventories.action.SetDrugPageElement;
import pageElements.inventories.action.SetParamsOfInjectionPageElement;
import pageElements.inventories.action.SetResultPageElement;
import pages.inventory.InventoriesCardPage;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.interactable;
import static com.codeborne.selenide.Selenide.$x;

public class InventoriesCardSteps extends Steps {
    InventoriesCardPage inventoriesCardPage = new InventoriesCardPage();

    public InventoriesCardSteps() {
        inventoriesCardPage.inventoryCardHeading.should(appear);
    }
    // Проверка наождения на странице Карточки описи
    public boolean isOnInventoriesCardPage() {
        inventoriesCardPage.inventoryCardHeading.should(appear);
        return true;
    }
    @Step("Установка препарата")
    public void setDrugs(Inventory inventory, Task task) {
        inventoriesCardPage.setDrugButtonForEnterprise.should(interactable).click();
        switch (task.getType()) {
            case "Дезинфекция":
            case "Дезинсекция":
            case "Дератизация":
                SetDrugFor4dSteps setDrugFor4dSteps = new SetDrugFor4dSteps();
                setDrugFor4dSteps.setDrug(inventory);
                break;
            case "Вакцинация":
            case "Аллергические исследования":
                SetDrugSteps setDrugPageVaccination = new SetDrugSteps();
                setDrugPageVaccination.setDrug(inventory);
                break;
        }
    }
    // Открытие модального окна для постановки препарата через Действия
    private void openModalWindowSetDrug() {
        inventoriesCardPage.actionsButton4d.should(interactable).click();
        inventoriesCardPage.addDrugButton.should(interactable).click();
    }

    @Step("Возврат на страницу Карточки описи")
    public void turnToInventoryCard() {
        inventoriesCardPage.okButton.should(interactable).click();
    }

    @Step("Проверка, следующий шаг, настройка Предварительного акта/Акта постановки препарата доступна")
    public boolean isPreviewActIsAvailable(Task task) {
        if (task.getType().equals("Аллергические исследования")) {
            $x("//button[@class='btn btn-primary w-100 btn-primary'][contains(text(), 'Акт постановки препарата')]")
                    .should(appear);
            return true;
        }
        $x("//button[@class='btn btn-primary w-100 btn-primary'][contains(text(), 'Предварительный акт')]")
                .should(appear);
        return true;
    }

    // Животное выделено
    private void isAnimalSelected() {
        $x("//div[@class='tabulator-row tabulator-selectable tabulator-row-odd tabulator-selected']")
                .should(appear);
    }
    @Step("Проверка, что опись изменила статус на 'В работе', доступен следующий шаг")
    public boolean isStatusInWork(Task task) {
        $x("//span[contains(text(), 'В работе')]").should(appear);
        if (task.getType().equals("Аллергические исследования")) {
            $x("//button[@class='btn btn-primary w-100 btn-primary'][contains(text(), 'Акт постановки препарата')]")
                    .should(appear);
            return true;
        }
        $x("//button[@class='btn btn-primary w-100 btn-primary'][contains(text(), 'Окончательный акт')]")
                .should(appear);
        return true;
    }
    @Step("Проверка, что опись изменила статус на 'Выполнено'")
    public boolean isStatusComplete() {
        $x("//span[contains(text(), 'Выполнено')]").should(appear);
        $x("//button[@class='btn w-100 btn-disabled'][contains(text(), 'Выполнить')]")
                .should(appear);
        return true;
    }

    @Step("Проверка, что кнопка Выполнить опись доступна, все шаги выполнены")
    public boolean isCompleteButtonAvailable() {
        $x("//button[@class='btn w-100 btn-primary'][contains(text(), 'Выполнить')]")
                .should(appear);
        return true;
    }
    @Step("Настройка предварительного акта")
    public void setPreviewAct(Inventory inventory, Task task) {
        inventoriesCardPage.previewActButton.should(interactable).click();
        PreviewActSteps previewActSteps = new PreviewActSteps(inventory, task);
        previewActSteps.settingPreviewAct(inventory, task);
    }
    @Step("Настройка окончательного акта")
    public void setFinalAct(Inventory inventory, Task task) {
        inventoriesCardPage.finalActButton.should(interactable).click();
        FinalActSteps finalActSteps = new FinalActSteps(task.getType());
        switch (task.getType()) {
            case "Дезинфекция":
            case "Дератизация":
            case "Дезинсекция":
                finalActSteps.settingFinalAct4d(inventory);
                break;
            case "Вакцинация":
                finalActSteps.settingFinalActVaccination(inventory);
                break;
            case "Диагностические исследования":
                finalActSteps.settingFinalActDiagnostic(inventory);
        }
    }

    @Step("Выполнение описи")
    public void completeInventories() {
        inventoriesCardPage.completeButton.should(interactable).click();
        $x("//h5[contains(text(), 'Выполнить опись?')]").should(appear);
        inventoriesCardPage.applyButton.should(interactable).click();
    }

    @Step("Применить препарат для объектов в 4 д")
    public void addDrugForEnterprise(Inventory inventory, Task task) {
        $x("//div[contains(text(), '" + task.getEnterpriseName().get(0) + "')]").should(appear);
        openModalWindowSetDrug();
        inventoriesCardPage.drugModalSelect.should(interactable).click();
        $x("//input[@class='select2-search__field']").should(interactable)
                .setValue(inventory.getTreatmentProduct()).pressEnter();
        $x("//span[@title='" + inventory.getTreatmentProduct() + "']/following::button[contains(text(), 'Применить')]")
                .should(interactable).click();
    }

    @Step("Установить параметры введения и препарат животным")
    public void addDrugForAnimals(Inventory inventory) {
        checkLoad();
        // Установить праметры введения
        inventoriesCardPage.selectAllButton.should(interactable).click();
        inventoriesCardPage.actionsButton.should(interactable).click();
        inventoriesCardPage.setParamsButton.should(interactable).click();
        SetParamsOfInjectionPageElement setParams = new SetParamsOfInjectionPageElement();
        setParams.SetParamsOfInjection(inventory);
        // Установить препарат
        inventoriesCardPage.actionsButton.should(interactable).click();
        inventoriesCardPage.setDrugButton.should(interactable).click();
        SetDrugPageElement setDrugPageElement = new SetDrugPageElement();
        setDrugPageElement.setDrug(inventory);
    }

    // Аллергические исследования
    @Step("Проверка, что кнопка Результаты доступна")
    public boolean isResultAvailable() {
        $x("//button[@class='btn btn-primary w-100 btn-primary'][contains(text(), 'Результаты')]").should(appear);
        return true;
    }
    @Step("Настройка акта постановки препарата")
    public void setActOfSetDrug(Inventory inventory, Task task) {
        setDrugs(inventory, task);
        $x("//button[contains(text(), 'Ok')]").should(Condition.interactable).click();
        addDrugForAnimals(inventory);
        inventoriesCardPage.setDrugActButton.should(interactable).click();
        SetAllergicActSteps setDrugActPage = new SetAllergicActSteps();
        setDrugActPage.settingSetDrugAct(inventory);
    }
    @Step("Настройка акта проверки результата")
    public void setActOfResearchResult(Inventory inventory) {
        inventoriesCardPage.checkResultActButton.should(interactable).click();
        $x("//h5[contains(text(), 'Все ли животные в акте постановки препарата соответствуют имеющимся на объекте?')]")
                .should(appear);
        $x("//h5[contains(text(), 'Все ли животные')]/parent::div//button[contains(text(), 'Да, продолжить')]")
                .should(interactable).click();
        SetAllergicActSteps setDrugActPage = new SetAllergicActSteps();
        setDrugActPage.settingCheckResultAct(inventory);
    }

    @Step("Установка результатов аллергических исследований")
    public void setResult(Inventory inventory) {
        inventoriesCardPage.selectAllButton.should(interactable).click();
        isAnimalSelected();
        inventoriesCardPage.setResultButton.should(interactable).click();
        SetResultPageElement setResultPageElement = new SetResultPageElement();
        setResultPageElement.setResult(inventory);
    }

    // Диагностические исследования

    @Step("Настройка второй партии")
    public void settingOfBatch(Inventory inventory, Task task) {
        inventoriesCardPage.generalButton.should(interactable).click();
        inventoriesCardPage.settingBatchButton.should(interactable).click();
        SetOfBatchSteps setOfBatchSteps = new SetOfBatchSteps();
        setOfBatchSteps.addSecondBatch(inventory, task);
    }

    @Step("Назначение партии животным")
    public void addBatchForAnimal(Inventory inventory, Task task) {
        inventoriesCardPage.generalButton.should(interactable).click();
        inventoriesCardPage.selectAllButton.should(interactable).click();
        isAnimalSelected();
        inventoriesCardPage.actionsButton.should(interactable).click();
        inventoriesCardPage.addBatchButton.should(interactable).click();
        AddingBatchPageElement addingBatchPageElement = new AddingBatchPageElement();
        addingBatchPageElement.selectBatch(inventory);
        isPreviewActIsAvailable(task);
    }

}
