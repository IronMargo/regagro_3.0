package uitests.tasks;

import assertions.InventoryAssertions;
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
public class AddNewVaccinationTaskTest {
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
        task = taskFactory.createTask("Вакцинация", userVet, 2, 2);
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
    @DisplayName("RAT-503 Создание задания по вакцинации")
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
        // Assertions.assertTrue(editTaskPage.isCorrectDiseaseDisplayed(task),
        //         "Заболевание, указанное при создании задания, не совпадает");
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
    @DisplayName("RAT-507 Отправка задания по вакцинации ветеринару")
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
        //Assertions.assertTrue(taskCardPage.isCorrectDiseaseDisplayed(task),
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
    @DisplayName("RAT-528 Получение задания по вакцинации от эпизоотолога")
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
    @DisplayName("RAT-530 Просмотр карточки задания по вакцинации")
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
        // Assertions.assertTrue(taskCardPage.isCorrectDiseaseDisplayed(task),
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
    @Order(5)
    @DisplayName("RAT-849 Принятие задания по вакцинации в работу")
    @Test
    void applyNewVaccinationTask() {
        Assertions.assertTrue(taskCardSteps.applyTask(), "Статус задания не изменен");
    }

    @Tag("EPIZOOTOLOGY")
    @Tag("REGRESS")
    @Order(6)
    @DisplayName("RAT-536 Выбор исполнителей для описи")
    @Test
    void selectExecutor() {
//        CreateInventoryPageElement modalCreateInventory = taskCardSteps.getModalCreateInventory(task);
//        modalCreateInventory.selectExecutor(task);
//
//        Assertions.assertTrue(modalCreateInventory.isCoExecutorSelect(task));
    }

    @Tag("EPIZOOTOLOGY")
    @Tag("REGRESS")
    @Order(7)
    @DisplayName("RAT-534 Создание описи по обработке")
    @Test
    void createInventories() {
        CreateInventoryPageElement modalCreateInventory = taskCardSteps.getModalCreateInventory(task);
        inventoriesCardSteps = modalCreateInventory.createInventories();

        Assertions.assertTrue(inventoriesCardSteps.isOnInventoriesCardPage());
    }

    @Tag("EPIZOOTOLOGY")
    @Tag("REGRESS")
    @Order(8)
    @DisplayName("RAT-548 Настройка препарата")
    @Test
    void settingDrugs() {
        inventoriesCardSteps.setDrugs(inventory, task);

        InventoryAssertions inventoryAssertions = new InventoryAssertions();
        Assertions.assertTrue(inventoryAssertions.isDrudAdded(inventory));

        inventoriesCardSteps.turnToInventoryCard();
        inventoriesCardSteps.addDrugForAnimals(inventory);

        Assertions.assertTrue(inventoriesCardSteps.isPreviewActIsAvailable(task));
    }

    @Tag("EPIZOOTOLOGY")
    @Tag("REGRESS")
    @Order(9)
    @DisplayName("RAT-549 Формирование предварительного акта")
    @Test
    void settingPreviewAct() {
        inventoriesCardSteps.setPreviewAct(inventory, task);

        Assertions.assertTrue(inventoriesCardSteps.isStatusInWork(task));
    }

    @Tag("EPIZOOTOLOGY")
    @Tag("REGRESS")
    @Order(10)
    @DisplayName("RAT-540 Формирование окончательного акта")
    @Test
    void settingFinalAct() {
        inventoriesCardSteps.setFinalAct(inventory, task);

        Assertions.assertTrue(inventoriesCardSteps.isCompleteButtonAvailable());
    }

    @Tag("EPIZOOTOLOGY")
    @Tag("REGRESS")
    @Order(11)
    @DisplayName("RAT- Выполнение описи с 1 препаратом")
    @Test
    void completeInventory() {
        inventoriesCardSteps.completeInventories();

        Assertions.assertTrue(inventoriesCardSteps.isStatusComplete());
    }
}
