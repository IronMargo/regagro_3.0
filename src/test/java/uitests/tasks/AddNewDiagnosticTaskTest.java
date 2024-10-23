package uitests.tasks;

import assertions.BatchAssertions;
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
public class AddNewDiagnosticTaskTest {

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
        task = taskFactory.createTask("Диагностические исследования", userVet, 2, 2);
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
    @DisplayName("RAT-1155 Создание задания по диагностическим исследованиям")
    @Test
    void addNewDiagnosticTask() {
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
        //Assertions.assertTrue(editTaskPage.isCorrectDiseaseDisplayed(task),
        //        "Заболевание, указанное при создании задания, не совпадает");
        Assertions.assertTrue(editTaskSteps.isCorrectServiceAreaDisplayed(task),
                "Территория обслуживания, указанная при создании задания, не совпадает");
//        Assertions.assertTrue(editTaskPage.isCorrectEnterpriseDisplayed(task),
//                "Объект, указанный при создании задания, не совпадает");
//        Assertions.assertTrue(editTaskPage.isCorrectKindsDisplayed(task),
//                "Виды животных, указанные при создании задания, не соответствуют выбранному заболеванию");
    }
    @Tag("TASK")
    @Tag("EPIZOOTOLOGY")
    @Tag("REGRESS")
    @Order(2)
    @DisplayName("RAT-1455 Отправка задания по диагностическим исследованиям")
    @Test
    void sendNewDiagnosticTask() {
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
        //        "Заболевание, указанное при создании задания, не совпадает c данными в Карточке задания");
        Assertions.assertTrue(taskCardSteps.isCorrectServiceAreaDisplayed(task),
                "Территория обслуживания, указанная при создании задания, не совпадает c данными в Карточке задания");
//        Assertions.assertTrue(taskCardPage.isCorrectEnterpriseDisplayed(task),
//                "Объект, указанный при создании задания, не совпадает c данными в Карточке задания");
//        Assertions.assertTrue(taskCardPage.isCorrectKindsDisplayed(task),
//                "Виды животных, указанные при создании задания, не соответствуют выбранному заболеванию c данными в Карточке задания");
    }
    @Tag("TASK")
    @Tag("EPIZOOTOLOGY")
    @Tag("REGRESS")
    @Order(3)
    @DisplayName("RAT-850 Получение задания по диагностическим исследованиям")
    @Test
    void getNewDiagnosticTask() {
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
    @DisplayName("RAT-851 Просмотр карточки задания по диагностическим исследованиям")
    @Test
    void viewNewDiagnosticTask() {
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
        //Assertions.assertTrue(taskCardPage.isCorrectDiseaseDisplayed(task),
        //        "Заболевание, указанное при создании задания, не совпадает c данными в Карточке задания");
        Assertions.assertTrue(taskCardSteps.isCorrectServiceAreaDisplayed(task),
                "Территория обслуживания, указанная при создании задания, не совпадает c данными в Карточке задания");
//        Assertions.assertTrue(taskCardPage.isCorrectEnterpriseDisplayed(task),
//                "Объект, указанный при создании задания, не совпадает c данными в Карточке задания");
    }
    @Tag("TASK")
    @Tag("EPIZOOTOLOGY")
    @Tag("REGRESS")
    @Order(5)
    @DisplayName("RAT-1137 Принятие задания по диагностическим исследованиям")
    @Test
    void applyNewDiagnosticTask() {
        Assertions.assertTrue(taskCardSteps.applyTask(), "Статус задания не изменен");
    }

//    @Tag("EPIZOOTOLOGY")
//    @Tag("REGRESS")
//    @Order(6)
//    @DisplayName("RAT-1140 Создание описи по заданию с выбором объекта")
//    @Test
//    void createInventory() {
//        CreateInventoryPageElement modalCreateInventory = taskCardSteps.getModalCreateInventory(task);
//        inventoriesCardSteps = modalCreateInventory.createInventories();
//
//        Assertions.assertTrue(inventoriesCardSteps.isOnInventoriesCardPage());
//
//        BatchAssertions batchAssertions = new BatchAssertions();
//        Assertions.assertTrue(batchAssertions.isLabAdded(inventory, "Партия 1"));
////        Assertions.assertTrue(batchAssertions.isMethodAdded(inventory, "Партия 1"));
//        Assertions.assertTrue(batchAssertions.isMaterialAdded(inventory, "Партия 1"));
//        Assertions.assertTrue(batchAssertions.isResearchCountAdded(inventory, "Партия 1"));
//        Assertions.assertTrue(batchAssertions.isDiseaseAdded(task, "Партия 1"));
//    }
//
//    @Tag("EPIZOOTOLOGY")
//    @Tag("REGRESS")
//    @Order(7)
//    @DisplayName("RAT-1152 Настройка партии")
//    @Test
//    void settingOfBatch() {
//        inventoriesCardSteps.settingOfBatch(inventory, task);
//        BatchAssertions batchAssertions = new BatchAssertions();
//
//        Assertions.assertTrue(batchAssertions.isLabAdded(inventory, "Партия 2"));
//        //       Assertions.assertTrue(batchAssertions.isMaterialAdded(inventory, "Партия 2"));
//        Assertions.assertTrue(batchAssertions.isResearchCountAdded(inventory, "Партия 2"));
//        Assertions.assertTrue(batchAssertions.isDiseaseAdded(task, "Партия 2"));
//    }
//
//    @Tag("EPIZOOTOLOGY")
//    @Tag("REGRESS")
//    @Order(8)
//    @DisplayName("RAT-1158 Настройка предварительных актов")
//    @Test
//    void settingPreviewAct() {
//        inventoriesCardSteps.addBatchForAnimal(inventory, task);
//        inventoriesCardSteps.setPreviewAct(inventory, task);
//
//        Assertions.assertTrue(inventoriesCardSteps.isStatusInWork(task));
//    }
//
//    @Tag("EPIZOOTOLOGY")
//    @Tag("REGRESS")
//    @Order(9)
//    @DisplayName("RAT-1166 Настройка окончательного акта")
//    @Test
//    void settingFinalAct() {
//        inventoriesCardSteps.setFinalAct(inventory, task);
//
//        Assertions.assertTrue(inventoriesCardSteps.isCompleteButtonAvailable());
//    }
//
//    @Tag("EPIZOOTOLOGY")
//    @Tag("REGRESS")
//    @Order(10)
//    @DisplayName("RAT-1172 Выполнение описи с ожиданием результатов")
//    @Test
//    void completeInventorie() {
//        inventoriesCardSteps.completeInventories();
//
//        Assertions.assertTrue(inventoriesCardSteps.isStatusComplete());
//    }
}
