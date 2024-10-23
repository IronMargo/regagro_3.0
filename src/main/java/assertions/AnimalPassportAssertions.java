package assertions;

import com.codeborne.selenide.Condition;
import pages.animal.AnimalPassportPage;

import java.util.NoSuchElementException;

import static com.codeborne.selenide.Condition.*;

public class AnimalPassportAssertions {
    AnimalPassportPage locators = new AnimalPassportPage();
    public boolean isOnAnimalPassportPage(String animalNumber) {
        try {
            locators.animalPassportHeading.should(appear);
            return (locators.animalPassportHeading.isDisplayed() ||
                    locators.identificationNumber.should(appear).getText().equals(animalNumber));
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    public boolean isOnAnimalGroupPassport(String animalGroupNumber){
        try {
            locators.animalGroupPassportHeading.should(appear);
            return (locators.animalGroupPassportHeading.isDisplayed() ||
                    locators.identificationNumber.should(appear).getText().equals(animalGroupNumber));
        }
        catch (NoSuchElementException e){
            throw new RuntimeException("Ошибка при поиске элемента на странице");
        }
    }
}
