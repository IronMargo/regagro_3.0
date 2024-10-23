package steps.task;

import abstractclass.Steps;
import entities.tasks.Task;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import pageElements.inventories.createInventory.CreateInventoryPageElement;
import pages.task.TaskCardPage;

import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;
import static datagenerator.DataGeneratorDate.convertDateToNeedFormat;

public class TaskCardSteps extends Steps {
    private final TaskCardPage locators = new TaskCardPage();

    public TaskCardSteps() {
        locators.heading.should(appear);
    }
    @Step("Получение номера задания")
    public String getTaskNumber() {
        return $x("//span[text()='Дата создания:']/parent::span/parent::div/span")
                .should(appear).getText().replace("№ ", "");
    }
    @Step("Принятие задания в работу")
    public boolean applyTask() {
        locators.applyButton.should(interactable).click();
        return $x("//span[contains(text(), 'В работе')]").should(visible).isDisplayed();
    }
    @Step("Кнопки приятия и отклонения задания видны")
    public boolean isButtonsIsDisplayed() {
        return locators.applyButton.should(visible).isDisplayed() && locators.rejectButton.should(visible).isDisplayed();
    }
    @Step("Отображается корректный статус Отправлено")
    public boolean isCorrectStatusIsDisplayed() {
        return $x("//span[contains(text(), 'Отправлено')]").should(visible).isDisplayed();
    }
    @Step("Наименование задания корректно")
    public boolean isCorrectNameDisplayed(Task task) {
        return $x("//div[text()='" + task.getName() + "']").should(visible).isDisplayed();
    }
    @Step("Тип задания корректный")
    public boolean isCorrectTypeDisplayed(Task task) {
        return $x("//div[text()='" + task.getType() + "']").should(visible).isDisplayed();
    }
    @Step("Дата задания ОТ корректна")
    public boolean isCorrectDateFromDisplayed(Task task) {
        String dateFrom = convertDateToNeedFormat(task.getDateFrom());
        String dateBefore = convertDateToNeedFormat(task.getDateBefore());
        return $x("//div[contains(text(), '" + dateFrom + " г. по " + dateBefore + "')]").should(visible).isDisplayed();
    }
    @Step("Дата задания ДО корректна")
    public boolean isCorrectDiseaseDisplayed(Task task) {
        return $x("//p[text()='" + task.getDisease() + "']").should(visible).isDisplayed() ||
                $x("//div[text()='" + task.getDisease() + "']").should(visible).isDisplayed();
    }
    @Step("Заболевание корректно")
    public boolean isCorrectDiseaseGroupDisplayed(Task task) {
        return $x("//li[text()='" + task.getDisease() + "']").should(visible).isDisplayed();
    }
    @Step("Территория корректна")
    public boolean isCorrectServiceAreaDisplayed(Task task) {
        if (task.getType().equals("Дезинфекция") || task.getType().equals("Дезинсекция") | task.getType().equals("Дератизация")) {
            return $x("//th[contains(text(), '" + task.getServiceArea() + "')]").should(visible).isDisplayed();
        }
        return $x("//span[contains(text(), '" + task.getServiceArea() + "')]").should(visible).isDisplayed();
    }

    public boolean isCorrectEnterpriseDisplayed(Task task) {
        return $x("//div[contains(text(), '" + task.getEnterpriseName() + "')]")
                .should(visible).isDisplayed();
    }
    @Step("Вид животного корректен")
    public boolean isCorrectKindsDisplayed(Task task) {
        locators.animalsTab.should(interactable).click();
        List<String> kinds = task.getKinds();
        boolean allDisplayed = true;

        for (String kind : kinds) {
            if (!$x("//th[contains(text(), '" + kind + "')]").should(visible).isDisplayed()) {
                allDisplayed = false;
                break;
            }
        }
        return allDisplayed;
    }
    @Step("Площадка корректна")
    private boolean enterpriseSelected(Task task) {
        $x("//span[contains(text(), 'Основное')]").should(interactable).click();
        $x("//div[contains(text(), '" + task.getEnterpriseName().get(0) + "')]").should(interactable).click();
        List<WebElement> elements = Arrays.asList(
                $x("//div [contains(text(), '" + task.getEnterpriseName().get(0) + "')]" +
                        "/parent::div[@class='tabulator-row tabulator-selectable tabulator-row-odd tabulator-selected']"),
                $x("//div [contains(text(), '" + task.getEnterpriseName().get(0) + "')]" +
                        "/parent::div[@class='tabulator-row tabulator-selectable tabulator-row-even tabulator-selected']")
        );
        return elements.stream().anyMatch(WebElement::isDisplayed);
    }
    @Step("Переход к модальному окну создания описи")
    public CreateInventoryPageElement getModalCreateInventory(Task task) {
        enterpriseSelected(task);
        locators.createInventoriesButton.should(interactable).click();
        checkLoad();
        return new CreateInventoryPageElement();
    }

}