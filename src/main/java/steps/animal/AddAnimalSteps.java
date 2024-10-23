package steps.animal;

import abstractclass.Steps;
import interfaces.Animals;
import io.qameta.allure.Step;
import pageElements.FindSupervisedObjectPageElement;
import pages.animal.AddAnimalPage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;


public class AddAnimalSteps extends Steps {
    protected AddAnimalPage addAnimalPage = new AddAnimalPage();

    public AddAnimalSteps() {
        addAnimalPage.heading.should(appear);
    }

    @Step("Регистрация индивидуального животного")
    public void addIndividualAnimal(Animals animal, String supervisedObjectName) {
        $x("//button[contains(text(), 'Регистрация')]").should(interactable).click();
        // Выбор объекта
        selectObject(supervisedObjectName);

        // Вид животного
        selectKind(animal);

        // Идентификация
        identification(animal);

        // Масть
        selectSuit(animal);

        // Дата рождения
        selectBirthDate(animal);

        // Пол
        if (animal.getGender().contains("female")) {
            addAnimalPage.genderFemaleCheckBox.should(interactable).click();
        } else addAnimalPage.genderMaleCheckBox.should(interactable).click();

        // Кличка
        addAnimalPage.nickNameInput.should(interactable).setValue(animal.getNickName()).pressEnter();

        // Содержание
        selectFeaturesOfKeeping(animal);

        // Активация
        addAnimalPage.activateButton.should(interactable).click();
    }

    @Step("Регистрация группы животных")
    public void addAnimalGroup(Animals animalGroup, String supervisedObjectName) {
        $x("//button[contains(text(), 'Регистрация')]").should(interactable).click();
        // Выбор объекта
        selectObject(supervisedObjectName);

        // Вид животного
        selectKind(animalGroup);

        // Идентификация
        identification(animalGroup);

        // Диапазон дат рождения
        if (animalGroup.getKind().equals("Пчёлы")) {
            addAnimalPage.birthDateForBees.should(interactable).click();
            addAnimalPage.birthDateInput.should(interactable).setValue(animalGroup.getBirthDateFrom()).pressEnter();
        } else {
            selectBirthDateRange(animalGroup);
        }
        // Пол и кол-во
        if (!animalGroup.getKind().equals("Пчёлы")) {
            if (animalGroup.getGender().contains("female")) {
                addAnimalPage.genderFemaleCheckBox.should(interactable).click();
                addAnimalPage.countOfFemaleInput.should(interactable).setValue(animalGroup.getCountOfFemale());
            } else if (animalGroup.getGender().contains("male")) {
                addAnimalPage.genderMaleCheckBox.should(interactable).click();
                addAnimalPage.countOfMaleInput.should(interactable).setValue(animalGroup.getCountOfMale());
            } else {
                addAnimalPage.genderMixedCheckBox.should(interactable).click();
                addAnimalPage.countOfMaleInput.should(interactable).setValue(animalGroup.getCountOfMale());
                addAnimalPage.countOfFemaleInput.should(interactable).setValue(animalGroup.getCountOfFemale());
            }
        }

        // Содержание
        selectFeaturesOfKeeping(animalGroup);

        // Активация
        addAnimalPage.activateButton.should(interactable).click();
    }

    @Step("Переход в паспорт животного после успешной регистрации")
    public AnimalPassportSteps getAnimalPassportPage() {
        addAnimalPage.openPassport.should(interactable).click();
        return new AnimalPassportSteps();
    }

    @Step("Переход в паспорт группы животных после успешной регистрации")
    public AnimalGroupPassportSteps getAnimalGroupPassportPage() {
        addAnimalPage.openPassport.should(interactable).click();
        return new AnimalGroupPassportSteps();
    }

    // Выбор объекта
    private void selectObject(String supervisedObjectName) {
        addAnimalPage.findObjectButton.should(interactable).click();
        FindSupervisedObjectPageElement findSupervisedObjectPageElement = new FindSupervisedObjectPageElement();
        findSupervisedObjectPageElement.selectSupervisedObject(supervisedObjectName);

    }

    // Вид
    private void selectKind(Animals animal) {
        checkLoad();
        addAnimalPage.animalKindSelect.should(interactable).click();
        checkLoad();
        addAnimalPage.input.should(interactable).setValue(animal.getKind()).pressEnter();
    }

    // Идентификация
    private void identification(Animals animal) {
        // Дата маркирования
        checkLoad();
        addAnimalPage.markerDate.should(interactable).click();
        addAnimalPage.markerDateInput.should(interactable).setValue(animal.getFirstMarkerDate()).pressEnter();

        // Тип СМ
        checkLoad();
        addAnimalPage.markerTypeSelect.should(interactable).click();
        addAnimalPage.input.setValue(animal.getMarkerType()).pressEnter();

        // Идентификационный номер
        checkLoad();
        addAnimalPage.identificationNumberField.should(interactable).setValue(animal.getIdentificationNumber()).pressEnter();

        // Основание для регистрации
        checkLoad();
        addAnimalPage.foundationSelect.should(interactable).click();
        addAnimalPage.input.should(interactable).setValue(animal.getRegistrationGround()).pressEnter();

        // Место маркирования
        checkLoad();
        addAnimalPage.markerPlacesSelect.should(interactable).click();
        addAnimalPage.input.should(interactable).setValue(animal.getMarkerPlace()).pressEnter();
    }

    // Масть
    private void selectSuit(Animals animal) {
        checkLoad();
        addAnimalPage.suitSelect.should(interactable).click();
        addAnimalPage.input.should(interactable).setValue(animal.getSuit()).pressEnter();
    }

    // Дата рождения
    private void selectBirthDate(Animals animal) {
        checkLoad();
        addAnimalPage.birthDate.should(interactable).click();
        addAnimalPage.birthDateInput.should(interactable).setValue(animal.getBirthDate()).pressEnter();
    }

    // Диапазон дат рождения
    private void selectBirthDateRange(Animals animalGroup) {
        checkLoad();
        addAnimalPage.birthDateFrom.should(interactable).click();
        addAnimalPage.birthDateInput.should(interactable).setValue(animalGroup.getBirthDateFrom());
        checkLoad();
        addAnimalPage.birthDateBefore.should(interactable).click();
        addAnimalPage.birthDateInput.should(interactable).setValue(animalGroup.getBirthDateBefore());
    }

    // Условия содержания
    private void selectFeaturesOfKeeping(Animals animal) {
        // Тип содержания
        checkLoad();
        addAnimalPage.keepTypeSelect.should(interactable).click();
        addAnimalPage.input.should(interactable).setValue(animal.getKeepType()).pressEnter();

        // Место содержания
        checkLoad();
        if (!animal.getKind().equals("Пчёлы")) {
            addAnimalPage.keepPlaceSelect.should(interactable).click();
            addAnimalPage.input.should(interactable).setValue(animal.getKeepPlace()).pressEnter();
        }
        // Направление продуктивности
        checkLoad();
        addAnimalPage.productDirectionSelect.should(interactable).click();
        addAnimalPage.input.should(interactable).setValue(animal.getProductDirection()).pressEnter();
    }
    public void closeModalWindowSuccess(){
        checkLoad();
        addAnimalPage.closeModalWindow.should(interactable).click();
        }
}