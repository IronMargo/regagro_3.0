package steps.task;

import abstractclass.Steps;
import com.codeborne.selenide.Selenide;
import entities.tasks.Task;
import services.database.DBService;
import services.database.HandbooksDBService;
import io.qameta.allure.Step;
import pages.task.AddKindsPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;

public class AddKindsSteps extends Steps {
    AddKindsPage addKindsPage = new AddKindsPage();
    HandbooksDBService dbHelper = DBService.getHandbooksDBService();

    public AddKindsSteps() {
        addKindsPage.heading.should(appear);
    }
    @Step("Переход к следующему шагу")
    public void goToNextStep() {
        Selenide.sleep(3000);
        addKindsPage.nextButton.should(interactable).click();
    }
    @Step("Добавление видов животных")
    public void addKinds(Task task) {
        // Добавление всех видов животных (кнопка)
        addKindsPage.setKindsButton.should(interactable).click();
        // Проверка, что виды животных соответствуют заданию
        dbHelper.isKindsCorrectForDisease(task.getDisease());
        // Очистка поля
        addKindsPage.cleanButton.should(interactable).click();
        // Выбор вида КРС
        addKindsPage.kindsSelect.should(interactable).click();
        addKindsPage.input.should(interactable).setValue("Крупный рогатый скот").pressEnter();
        // Добавление соисполнителя
//        addKindsPage.editExecutorButton.should(interactable).click();
//        addKindsPage.executorsSelect.should(interactable).click();
//        addKindsPage.input.should(interactable).setValue(task.getCoExecutor()).pressEnter();
//        addKindsPage.addButton.should(interactable).click();
        // Переход к следующему шагу
        addKindsPage.nextButton.should(interactable).click();
    }
}
