package steps.general;

import abstractclass.Steps;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import pages.general.SidebarPage;
import steps.animal.AddAnimalSteps;
import steps.disposal.CreateDisposalListSteps;
import steps.enterprise.AddEnterpriseSteps;
import steps.lists.AnimalsListSteps;
import steps.lists.EnterpriseListSteps;
import steps.lists.SupervisedObjectListSteps;
import steps.lists.TasksListSteps;
import steps.supervisedObject.AddSupervisedObjectSteps;

import java.lang.reflect.InvocationTargetException;

import static com.codeborne.selenide.Condition.interactable;

public class SidebarSteps extends Steps {
    SidebarPage locators = new SidebarPage();

    // Открытие аккордеона Региятрация
    public void openRegistrationAccordion() {
        locators.registrationAccordionButton.should(interactable).click();
    }

    // Универсальный метод для открытия страницы регистрации
    private <T> T openRegistrationPage(SelenideElement pageButton, Class<T> pageClass) {
        try {
            checkLoad();
            openRegistrationAccordion();
            checkLoad();
            pageButton.should(interactable).click();
            return pageClass.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            // Вывод в консоль ошибки:
             System.err.println("Ошибка при открытии страницы: " + e.getMessage());
             e.printStackTrace();
            throw new RuntimeException("Не удалось открыть страницу", e);
        }
    }

    @Step("Переход из сайдбара на страницу Регистрации площадки")
    public AddEnterpriseSteps getAddEnterprisePage() {
        return openRegistrationPage(locators.enterpriseCreateButton, AddEnterpriseSteps.class);
    }

    @Step("Переход из сайдбара на страницу Регистрации объекта")
    public AddSupervisedObjectSteps getAddSupervisedObjectPage() {
        return openRegistrationPage(locators.supervisedObjectCreateButton, AddSupervisedObjectSteps.class);
    }

    @Step("Переход из сайдбара на страницу Регистрации животного")
    public AddAnimalSteps getAddAnimalPage() {
        return openRegistrationPage(locators.animalCreateButton, AddAnimalSteps.class);
    }

    @Step("Переход из сайдбара на страницу Регистрацию группы животных")
    public AddAnimalSteps getAddAnimalGroupPage() {
        return openRegistrationPage(locators.animalGroupCreateButton, AddAnimalSteps.class);
    }

    @Step("Переход из сайдбара на страницу Реестр производственных площадок")
    public EnterpriseListSteps getEnterpriseList() {
        locators.registryAccordionButton.should(interactable).click();

        locators.enterprisesListButton.should(interactable).click();
        return new EnterpriseListSteps();
    }

    @Step("Переход из сайдбара на страницу Реестр поднадзорных объектов")
    public SupervisedObjectListSteps getSupervisedObjectList() {
        locators.registryAccordionButton.should(interactable).click();

        locators.supervisedObjectListButton.should(interactable).click();
        return new SupervisedObjectListSteps();
    }

    @Step("Переход из сайдбара на страницу Реестр животных")
    public AnimalsListSteps getAnimalsListPage() {
        locators.registryAccordionButton.should(interactable).click();
        locators.animalsListButton.should(interactable).click();
        return new AnimalsListSteps();
    }

    @Step("Переход из сайдбара на страницу создания листа выбытия")
    public CreateDisposalListSteps getCreateDisposal() {
        locators.disposalAccordionButton.should(interactable).click();
        locators.createDisposalListButton.should(interactable).click();
        return new CreateDisposalListSteps();
    }

    @Step("Переход из сайдбара на страницу Список заданий")
    public TasksListSteps getTasksListPage() {
        locators.tasksAccordionButton.should(interactable).click();
        locators.tasksListAccordionButton.should(interactable).click();
        checkLoad();
        return new TasksListSteps();
    }

}
