package pageElements.inventories.acts;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import entities.tasks.Inventory;
import entities.tasks.Task;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class Setting4dActPageElement extends SettingActPageElement {
    private final SelenideElement countOfDrugField = $x("//*[contains(text(), 'Количество препарата')]//input");
    private final SelenideElement temperatureField = $x("//*[contains(text(), 'Температура')]//input");
    private final SelenideElement spentSolutionField = $x("//*[contains(text(), 'Расход')]//input");
    private final SelenideElement squareOfPlaceField = $x("//*[contains(text(),'Площадь помещений')]/following::div[2]//input");
    private final SelenideElement squareOfWalkingField = $x("//*[contains(text(),'Площадь помещений')]/following::div[2]/following-sibling::div[1]//input");
    private final SelenideElement squareOfArea = $x("//*[contains(text(),'Площадь территорий')]/following::div[2]//input");
    private final SelenideElement volumeOfLiquidCollector = $x("//*[contains(text(),'Площадь территорий')]/following::div[2]/following-sibling::div[1]//input");
    private final SelenideElement laboratorySelect = $x("//select[@name='laboratory_id']/following-sibling::span//span[@class= 'select2-selection select2-selection--single']");
    private final SelenideElement input = $x("//span[@class='select2-container select2-container--bootstrap-5 select2-container--open']//input");
    private final SelenideElement temperatureAtPlaceField = $x("//*[contains(text(), 'Температура воздуха в помещении')]//input");
    private final SelenideElement closeTimeField = $x("//*[contains(text(), 'оставлено закрытым')]//input");

    protected Setting4dActPageElement(){}
    @Step("Детальная настройка предварительного акта для 4д")
    public void settingPreviewActDetails(Inventory inventory, Task task) {
        countOfDrugField.should(Condition.interactable).setValue(inventory.getCountOfDrug());
        temperatureField.should(Condition.interactable).setValue(inventory.getTemperature());
        spentSolutionField.should(Condition.interactable).setValue(inventory.getSolutionSpentFact());
        squareOfPlaceField.should(Condition.interactable).setValue(inventory.getSquareOfPlace());
        squareOfWalkingField.should(Condition.interactable).setValue(inventory.getSquareOfWalking());
        squareOfArea.should(Condition.interactable).setValue(inventory.getSquareOfArea());
        volumeOfLiquidCollector.should(Condition.interactable).setValue(inventory.getVolumeOfLiquidCollector());
        if (task.getType().equals("Дезинфекция")){
            laboratorySelect.should(Condition.interactable).click();
            input.should(Condition.interactable).setValue(inventory.getLaboratory());
        }
        postField.should(Condition.interactable).setValue(inventory.getPost());
        nameField.should(Condition.interactable).setValue(inventory.getName());
        okButton.should(Condition.interactable).click();
    }
    @Step("Детальная настройка окончательного акта для 4д")
    public void settingFinalActDetails4d(Inventory inventory){
        temperatureAtPlaceField.should(Condition.interactable).setValue(inventory.getTemperatureAtPlace());
        closeTimeField.should(Condition.interactable).setValue(inventory.getCloseTime());
        okButton.should(Condition.interactable).click();
    }
}
