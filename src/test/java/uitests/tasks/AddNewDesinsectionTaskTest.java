package uitests.tasks;

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
import pageElements.inventories.createInventory.CreateInventoryPageElement;
import steps.general.AuthSteps;
import steps.general.HomeSteps;
import steps.general.SidebarSteps;
import steps.inventory.InventoriesCardSteps;
import steps.lists.TasksListSteps;
import steps.task.EditTaskSteps;
import steps.task.TaskCardSteps;

import static enums.User.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddNewDesinsectionTaskTest {
    private static final Configurations configurations = new Configurations();
    private static Task task;
    private static Inventory inventory;
    private static EditTaskSteps editTaskSteps;
    private static TasksListSteps tasksListSteps;
    private static TaskCardSteps taskCardSteps;
    private static InventoriesCardSteps inventoriesCardSteps;
    private static String taskNumber;
    private static User userVet = KAMERER;

    @BeforeAll
    static void setUpAll() {
        configurations.setUpConfigurationsApi(EPIZ);
        TaskFactory taskFactory = new TaskFactory();
        task = taskFactory.createTask("Дезинсекция", userVet, 2, 2);
        InventoriesFactory inventoriesFactory = new InventoriesFactory();
        inventory = inventoriesFactory.createInventorie(task);
    }

    @AfterAll
    static void tearDownAll() {
        configurations.clear();
    }
    @Tag("TASK")
    @Tag("EPIZOOTOLOGY")
    @Tag("REGRESS")
    @Order(1)
    @DisplayName("RAT-2489 Создание задания по дезинсекции")
    @Test
    void addNewVaccinationTask() {
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
        Assertions.assertTrue(editTaskSteps.isCorrectServiceAreaDisplayed(task),
                "Территория обслуживания, указанная при создании задания, не совпадает");
//        Assertions.assertTrue(editTaskPage.isCorrectEnterpriseDisplayed(task),
//                "Объект, указанный при создании задания, не совпадает");
    }
    @Tag("TASK")
    @Tag("EPIZOOTOLOGY")
    @Tag("REGRESS")
    @Order(2)
    @DisplayName("RAT-2498 Отправка задания по дезинсекции")
    @Test
    void sendNewVaccinationTask() {
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
        Assertions.assertTrue(taskCardSteps.isCorrectServiceAreaDisplayed(task),
                "Территория обслуживания, указанная при создании задания, не совпадает c данными в Карточке задания");
//        Assertions.assertTrue(taskCardPage.isCorrectEnterpriseDisplayed(task),
//                "Объект, указанный при создании задания, не совпадает c данными в Карточке задания");
    }
    @Tag("TASK")
    @Tag("EPIZOOTOLOGY")
    @Tag("REGRESS")
    @Order(3)
    @DisplayName("RAT-2445 Получение задания по дезинсекции")
    @Test
    void getNewVaccinationTask() {
        HomeSteps homeSteps = new HomeSteps();
        homeSteps.changeUser(userVet);
        SidebarSteps sidebarSteps = new SidebarSteps();
        tasksListSteps = sidebarSteps.getTasksListPage();

        Assertions.assertTrue(tasksListSteps.isTaskGetting(taskNumber),
                "Отправленное эпизоотологом задание не отображается в списке");
    }
    @Tag("TASK")
    @Tag("EPIZOOTOLOGY")
    @Tag("REGRESS")
    @Order(4)
    @DisplayName("RAT-2446 Просмотр карточки задания по дезинсекции")
    @Test
    void viewNewVaccinationTask() {
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
        Assertions.assertTrue(taskCardSteps.isCorrectServiceAreaDisplayed(task),
                "Территория обслуживания, указанная при создании задания, не совпадает c данными в Карточке задания");
//        Assertions.assertTrue(taskCardPage.isCorrectEnterpriseDisplayed(task),
//                "Объект, указанный при создании задания, не совпадает c данными в Карточке задания");
    }
    @Tag("TASK")
    @Tag("EPIZOOTOLOGY")
    @Tag("REGRESS")
    @Order(5)
    @DisplayName("RAT-2456 Принятие задания по дезинсекции")
    @Test
    void applyNewVaccinationTask() {
        Assertions.assertTrue(taskCardSteps.applyTask(), "Статус задания не изменен");
    }

    @Tag("EPIZOOTOLOGY")
    @Tag("REGRESS")
    @Order(6)
    @DisplayName("RAT-2463 Создание описи по обработке")
    @Test
    void createInventories() {
        CreateInventoryPageElement modalCreateInventory = taskCardSteps.getModalCreateInventory(task);
        inventoriesCardSteps = modalCreateInventory.createInventories();

        Assertions.assertTrue(inventoriesCardSteps.isOnInventoriesCardPage());
    }

    @Tag("EPIZOOTOLOGY")
    @Tag("REGRESS")
    @Order(7)
    @DisplayName("RAT-2470 Настройка препарата")
    @Test
    void settingDrugs() {
        inventoriesCardSteps.setDrugs(inventory, task);
        inventoriesCardSteps.addDrugForEnterprise(inventory, task);

        Assertions.assertTrue(inventoriesCardSteps.isPreviewActIsAvailable(task));
    }

    @Tag("EPIZOOTOLOGY")
    @Tag("REGRESS")
    @Order(8)
    @DisplayName("RAT-2484 Формирование предварительного акта")
    @Test
    void settingPreviewAct() {
        inventoriesCardSteps.setPreviewAct(inventory, task);

        Assertions.assertTrue(inventoriesCardSteps.isStatusInWork(task));
    }

    @Tag("EPIZOOTOLOGY")
    @Tag("REGRESS")
    @Order(9)
    @DisplayName("RAT-2486 Формирование окончательного акта")
    @Test
    void settingFinalAct() {
        inventoriesCardSteps.setFinalAct(inventory, task);

        Assertions.assertTrue(inventoriesCardSteps.isCompleteButtonAvailable());
    }

    @Tag("EPIZOOTOLOGY")
    @Tag("REGRESS")
    @Order(10)
    @DisplayName("RAT-2479 Выполнение описи с 1 препаратом")
    @Test
    void completeInventorie() {
        inventoriesCardSteps.completeInventories();

        Assertions.assertTrue(inventoriesCardSteps.isStatusComplete());
    }
}
