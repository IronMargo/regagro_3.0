package steps.animal;

import abstractclass.Steps;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.NoSuchElementException;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class IdentificationSteps  extends Steps {
    public IdentificationSteps() {
        SelenideElement heading = $x("//h2[text()='Идентификация животного']");
        heading.should(Condition.appear);
    }
    public void archivateNumber(String identificationNumber) {

        // ElementsCollection elementBlocks = $$x("//div//input/parent::div/parent::div/parent::div");
        ElementsCollection elementBlocks = $$x("//small[text()='Номер средства маркирования']/parent::div");
        SelenideElement needElement = null;

        for (SelenideElement block : elementBlocks) {
            SelenideElement input = block.find("input.form-control");
            String animalNumber = input.getAttribute("value");

            if (animalNumber.equals(identificationNumber)) {
                needElement = block;
                break;
            }
        }
        try {
            if (needElement != null) {
                // Находим контейнер с кнопкой, он является следующим элементом после родителя needElement
                SelenideElement buttonContainer = needElement.parent().parent().parent().parent().$("div:nth-child(2)");

// Находим кнопку "Архивировать" внутри найденного контейнера
                SelenideElement archiveButton = buttonContainer.$("button[data-bs-target^='#archivateModal']");
                archiveButton.click();
            }
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Не найден элемент с данным номером");
        }

    }

}

