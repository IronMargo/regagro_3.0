package in_work.identification;

import com.codeborne.selenide.logevents.SelenideLogger;
import entities.tasks.Inventory;
import entities.tasks.Task;
import enums.User;
import factory.InventoriesFactory;
import factory.TaskFactory;
import services.Configurations;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import steps.general.AuthSteps;
import steps.general.HomeSteps;
import steps.general.SidebarSteps;
import steps.inventory.InventoriesCardSteps;
import steps.lists.TasksListSteps;
import steps.task.EditTaskSteps;
import steps.task.TaskCardSteps;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static enums.User.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddDewormingTaskTest {
    private static final Configurations configurations = new Configurations();
    static Task task;
    static Inventory inventory;
    private EditTaskSteps editTaskSteps;
    private TasksListSteps tasksListSteps;
    private TaskCardSteps taskCardSteps;
    private InventoriesCardSteps inventoriesCardSteps;
    private String taskNumber;
    private static User userVet = KAMERER;

    @BeforeAll
    static void setUpAll() {
        configurations.setUpConfigurationsApi(EPIZ);
        TaskFactory taskFactory = new TaskFactory();
        task = taskFactory.createTask("Дегельминтизация", userVet, 2, 2);
        InventoriesFactory inventoriesFactory = new InventoriesFactory();
        inventory = inventoriesFactory.createInventorie(task);
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
        closeWebDriver();
    }
    @Tag("IN_WORK")
    @Order(1)
    @DisplayName("RAT-503 Создание задания по дегельминтизации")
    @Test
    void addNewDewormingTask() {
        SidebarSteps sidebarSteps = new SidebarSteps();
        tasksListSteps = sidebarSteps.getTasksListPage();
        tasksListSteps.addNewTask(task);
        editTaskSteps = new EditTaskSteps();
        Assertions.assertTrue(editTaskSteps.isCorrectNameDisplayed(task),
                "Название задания, указанное при создании задания, не совпадает");
        Assertions.assertTrue(editTaskSteps.isCorrectTypeDisplayed(task),
                "Тип задания, указанный при создании задания, не совпадает");
        Assertions.assertTrue(editTaskSteps.isCorrectDateFromDisplayed(task),
                "Период выполнения, указанный при создании задания, не совпадает");
        Assertions.assertTrue(editTaskSteps.isCorrectDateBeforeDisplayed(task),
                "Период выполнения, указанный при создании задания, не совпадает");
        // Assertions.assertTrue(editTaskPage.isCorrectDiseaseDisplayed(task),
        //         "Заболевание, указанное при создании задания, не совпадает");
        Assertions.assertTrue(editTaskSteps.isCorrectServiceAreaDisplayed(task),
                "Территория обслуживания, указанная при создании задания, не совпадает");
//        Assertions.assertTrue(editTaskPage.isCorrectEnterpriseDisplayed(task),
//                "Объект, указанный при создании задания, не совпадает");
//        Assertions.assertTrue(editTaskPage.isCorrectKindsDisplayed(task),
//                "Виды животных, указанные при создании задания, не соответствуют выбранному заболеванию");
    }
    @Tag("IN_WORK")
    @Order(2)
    @DisplayName("RAT-507 Отправка задания по дегельминтизации ветеринару")
    @Test
    void sendNewDewormingTask() {
        taskCardSteps = editTaskSteps.sendTask();
        taskNumber = taskCardSteps.getTaskNumber();
        Assertions.assertTrue(taskCardSteps.isCorrectStatusIsDisplayed(),
                "Статус задания - Черновик");
        Assertions.assertTrue(taskCardSteps.isCorrectNameDisplayed(task),
                "Название задания, указанное при создании задания, не совпадает c данными в Карточке задания");
        Assertions.assertTrue(taskCardSteps.isCorrectTypeDisplayed(task),
                "Тип задания, указанный при создании задания, не совпадает c данными в Карточке задания");
        Assertions.assertTrue(taskCardSteps.isCorrectDateFromDisplayed(task),
                "Период выполнения, указанный при создании задания, не совпадает c данными в Карточке задания");
        //Assertions.assertTrue(taskCardPage.isCorrectDiseaseDisplayed(task),
        //         "Заболевание, указанное при создании задания, не совпадает c данными в Карточке задания");
        Assertions.assertTrue(taskCardSteps.isCorrectServiceAreaDisplayed(task),
                "Территория обслуживания, указанная при создании задания, не совпадает c данными в Карточке задания");
//        Assertions.assertTrue(taskCardPage.isCorrectEnterpriseDisplayed(task),
//                "Объект, указанный при создании задания, не совпадает c данными в Карточке задания");
    }
    @Tag("IN_WORK")
    @Order(3)
    @DisplayName("RAT-528 Получение задания по дегельминтизации от эпизоотолога")
    @Test
    void getNewDewormingTask() {
        HomeSteps homeSteps = new HomeSteps();
        homeSteps.logout();
        AuthSteps authSteps = new AuthSteps();
        authSteps.auth(VET_BUNIN);
        SidebarSteps sidebarSteps = new SidebarSteps();
        tasksListSteps = sidebarSteps.getTasksListPage();

        Assertions.assertTrue(tasksListSteps.isTaskGetting(taskNumber),
                "Отправленное эпизоотологом задание не отображается в списке");
    }
    @Tag("IN_WORK")
    @Order(4)
    @DisplayName("RAT-530 Просмотр карточки задания по дегельминтизации")
    @Test
    void viewNewDewormingTask() {
        tasksListSteps = new TasksListSteps();
        taskCardSteps = tasksListSteps.openTaskCardPage(taskNumber);

        Assertions.assertTrue(taskCardSteps.isButtonsIsDisplayed(),
                "Не отображаются кнопки Принять и Отклонить");
        Assertions.assertTrue(taskCardSteps.isCorrectStatusIsDisplayed(),
                "Статус задания - Черновик");
        Assertions.assertTrue(taskCardSteps.isCorrectNameDisplayed(task),
                "Название задания, указанное при создании задания, не совпадает c данными в Карточке задания");
        Assertions.assertTrue(taskCardSteps.isCorrectTypeDisplayed(task),
                "Тип задания, указанный при создании задания, не совпадает c данными в Карточке задания");
        Assertions.assertTrue(taskCardSteps.isCorrectDateFromDisplayed(task),
                "Период выполнения, указанный при создании задания, не совпадает c данными в Карточке задания");
        // Assertions.assertTrue(taskCardPage.isCorrectDiseaseDisplayed(task),
        //        "Заболевание, указанное при создании задания, не совпадает c данными в Карточке задания");
        Assertions.assertTrue(taskCardSteps.isCorrectServiceAreaDisplayed(task),
                "Территория обслуживания, указанная при создании задания, не совпадает c данными в Карточке задания");
//        Assertions.assertTrue(taskCardPage.isCorrectEnterpriseDisplayed(task),
//                "Объект, указанный при создании задания, не совпадает c данными в Карточке задания");
//        Assertions.assertTrue(taskCardPage.isCorrectKindsDisplayed(task),
//                "Виды животных, указанные при создании задания, не соответствуют выбранному заболеванию c данными в Карточке задания");
    }
    @Tag("IN_WORK")
    @Order(5)
    @DisplayName("RAT-849 Принятие задания по дегельминтизации в работу")
    @Test
    void applyNewDewormingTask() {
        Assertions.assertTrue(taskCardSteps.applyTask(), "Статус задания не изменен");
    }
}

