package assertions;

import com.codeborne.selenide.Selenide;
import interfaces.Animals;
import services.database.DBService;
import services.database.RegagroDBService;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

public class AnimalRegistrationAssertions {
    public boolean getSuccessMessage(String identificationNumber) {
        RegagroDBService dbHelper = DBService.getRegagroDBService();
        $x("//button[@id='openAnimalPassport']").should(appear);
        return  Selenide.$x("//button[@id='openAnimalPassport']").isDisplayed() &&
        dbHelper.isValueInDatabase("number", "animals", identificationNumber);
    }

    public boolean getErrorMessage(){
        $x("//div[@class='alert alert-danger']").should(visible);
        return $x("//div[@class='alert alert-danger']").isDisplayed();
    }

    public boolean getUnCorrectRegistration(String animalNumber) {
        RegagroDBService dbHelper = DBService.getRegagroDBService();
        return !dbHelper.isValueInDatabase("number", "animals", animalNumber);
    }

    public boolean isAnimalAtDB(Animals animal) {
        RegagroDBService dbHelper = DBService.getRegagroDBService();
        return dbHelper.isValueInDatabase("number", "animals", animal.getIdentificationNumber());
    }
    public boolean isAnimalInAnimalRegistry(String animalNumber) {
        return $x("//div[contains(text(), '" + animalNumber + "')]").should(visible).isDisplayed();
    }
    public boolean isValueCorrect(String valueActual, String valueExpected) {
        return valueActual.contains(valueExpected);
    }
}
