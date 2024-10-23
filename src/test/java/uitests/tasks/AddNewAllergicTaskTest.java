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
public class AddNewAllergicTaskTest {
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
        task = taskFactory.createTask("Аллергические исследования", userVet, 2, 2);
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
    @DisplayName("RAT-1411 Создание задания по аллергическим исследованиям")
    @Test
    void addNewAllergicTask() {
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
//        Assertions.assertTrue(editTaskPage.isCorrectDiseaseDisplayed(task),
//                "Заболевание, указанное при создании задания, не совпадает");
        Assertions.assertTrue(editTaskSteps.isCorrectServiceAreaDisplayed(task),
                "Территория обслуживания, указанная при создании задания, не совпадает");
        //       Assertions.assertTrue(editTaskPage.isCorrectEnterpriseDisplayed(task),
        //               "Объект, указанный при создании задания, не совпадает");
//        Assertions.assertTrue(editTaskPage.isCorrectKindsDisplayed(task),
//                "Виды животных, указанные при создании задания, не соответствуют выбранному заболеванию");
    }
    @Tag("TASK")
    @Tag("EPIZOOTOLOGY")
    @Tag("REGRESS")
    @Order(2)
    @DisplayName("RAT-1427 Отправка задания по аллергическим исследованиям")
    @Test
    void sendNewAllergicTask() {
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
        // Assertions.assertTrue(taskCardPage.isCorrectDiseaseDisplayed(task),
        //         "Заболевание, указанное при создании задания, не совпадает c данными в Карточке задания");
        Assertions.assertTrue(taskCardSteps.isCorrectServiceAreaDisplayed(task),
                "Территория обслуживания, указанная при создании задания, не совпадает c данными в Карточке задания");
//        Assertions.assertTrue(taskCardPage.isCorrectEnterpriseDisplayed(task),
//                "Объект, указанный при создании задания, не совпадает c данными в Карточке задания");
    }
    @Tag("TASK")
    @Tag("EPIZOOTOLOGY")
    @Tag("REGRESS")
    @Order(3)
    @DisplayName("RAT-3470 Получение задания по аллергическим исследованиям")
    @Test
    void getNewAllergicTask() {
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
    @DisplayName("RAT-778 Просмотр карточки задания по аллергическим исследованиям")
    @Test
    void viewNewAllergicTask() {

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
//        Assertions.assertTrue(taskCardPage.isCorrectDiseaseDisplayed(task),
        //              "Заболевание, указанное при создании задания, не совпадает c данными в Карточке задания");
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
    @Order(5)
    @DisplayName("RAT-3471 Принятие задания по аллергическим исследованиям")
    @Test
    void applyNewAllergicTask() {
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
    @DisplayName("RAT-831 Настройка акта постановки препарата")
    @Test
    void settingSetDrugAct() {
        inventoriesCardSteps.setActOfSetDrug(inventory, task);

        Assertions.assertTrue(inventoriesCardSteps.isStatusInWork(task));
    }
    @Tag("EPIZOOTOLOGY")
    @Tag("REGRESS")
    @Order(8)
    @DisplayName("RAT-833 Формирование акта проверки результата")
    @Test
    void settingCheckResultAct() {
        inventoriesCardSteps.setActOfResearchResult(inventory);

        Assertions.assertTrue(inventoriesCardSteps.isResultAvailable());
    }
    @Tag("EPIZOOTOLOGY")
    @Tag("REGRESS")
    @Order(9)
    @DisplayName("RAT-844 Указание результатов исследования")
    @Test
    void setResult() {
        inventoriesCardSteps.setResult(inventory);

        Assertions.assertTrue(inventoriesCardSteps.isCompleteButtonAvailable());
    }
    @Tag("EPIZOOTOLOGY")
    @Tag("REGRESS")
    @Order(10)
    @DisplayName("RAT-847 Выполнение описи по аллергическим исследованиям")
    @Test
    void completeInventory() {
        inventoriesCardSteps.completeInventories();

        Assertions.assertTrue(inventoriesCardSteps.isStatusComplete());
    }
}
