package assertions;

import com.codeborne.selenide.Condition;
import entities.tasks.Inventory;
import entities.tasks.Task;

import static com.codeborne.selenide.Selenide.$x;
import static datagenerator.DataGeneratorNumbers.extractNumber;

public class BatchAssertions {
    public boolean isMethodAdded(Inventory inventory, String nameOfBatch) {
        switch (nameOfBatch) {
            case "Партия 1":
                $x("//button[contains(text(), 'Партия 1')]").should(Condition.interactable).click();
                return isMethodCorrect(inventory.getMethod(), nameOfBatch);
            case "Партия 2":
                $x("//button[contains(text(), 'Партия 2')]").should(Condition.interactable).click();
                return isMethodCorrect(inventory.getMethodResearchBatchTwo(), nameOfBatch);
            default:
                throw new IllegalArgumentException("Неверное имя партии: " + nameOfBatch);
        }
    }
    public boolean isDiseaseAdded( Task task, String nameOfBatch) {
        String numberOfBatch = String.valueOf(Integer.parseInt(extractNumber(nameOfBatch, "Партия ")) + 1);
        switch (nameOfBatch) {
            case "Партия 1":
                $x("//button[contains(text(), 'Партия 1')]").should(Condition.interactable).click();
                break;
            case "Партия 2":
                $x("//button[contains(text(), 'Партия 2')]").should(Condition.interactable).click();
                break;
            default:
                throw new IllegalArgumentException("Неверное имя партии: " + nameOfBatch);
        }
        return $x("//div[@id='v-pills-tabContent']/div[" + numberOfBatch + "]" +
                "//td[contains(text(), 'Заболевание')]/parent::tr/following-sibling::tr/td[1]").should(Condition.appear).getText().equals(task.getDisease());
    }
    public boolean isMaterialAdded(Inventory inventory, String nameOfBatch) {
        switch (nameOfBatch) {
            case "Партия 1":
                $x("//button[contains(text(), 'Партия 1')]").should(Condition.interactable).click();
                return isMaterialCorrect(inventory.getMaterialResearch(), nameOfBatch);
            case "Партия 2":
                $x("//button[contains(text(), 'Партия 2')]").should(Condition.interactable).click();
                return isMaterialCorrect(inventory.getMaterialResearchBatchTwo(), nameOfBatch);
            default:
                throw new IllegalArgumentException("Неверное имя партии: " + nameOfBatch);
        }
    }
    public boolean isResearchCountAdded(Inventory inventory, String nameOfBatch) {
        switch (nameOfBatch) {
            case "Партия 1":
                $x("//button[contains(text(), 'Партия 1')]").should(Condition.interactable).click();
                return isResearchCountCorrect(inventory.getResearchCounts(), nameOfBatch);
            case "Партия 2":
                $x("//button[contains(text(), 'Партия 2')]").should(Condition.interactable).click();
                return isResearchCountCorrect(inventory.getResearchCountsBatchTwo(), nameOfBatch);
            default:
                throw new IllegalArgumentException("Неверное имя партии: " + nameOfBatch);
        }
    }
    public boolean isLabAdded(Inventory inventory, String nameOfBatch) {
        String numberOfBatch = String.valueOf(Integer.parseInt(extractNumber(nameOfBatch, "Партия ")) + 1);
        switch (nameOfBatch) {
            case "Партия 1":
                $x("//button[contains(text(), 'Партия 1')]").should(Condition.interactable).click();
                break;
            case "Партия 2":
                $x("//button[contains(text(), 'Партия 2')]").should(Condition.interactable).click();
                break;
            default:
                throw new IllegalArgumentException("Неверное имя партии: " + nameOfBatch);
        }
        String lab = $x("//div[@id='v-pills-tabContent']/div[" + numberOfBatch + "]//div[text()='Лаборатория:']/following-sibling::div")
                .should(Condition.appear).getText().toLowerCase();
        return (lab.contains(inventory.getLaboratory()));
    }

    private boolean isMethodCorrect
            (String method, String nameOfBatch){
        String numberOfBatch = String.valueOf(Integer.parseInt(extractNumber(nameOfBatch, "Партия ")) + 1);
        return $x("//div[@id='v-pills-tabContent']/div[" + numberOfBatch + "]" +
                "//div[contains(text(), 'Методы исследования:')]/following-sibling::div").should(Condition.appear).getText().equals(method);
    }
    private boolean isMaterialCorrect
            (String material, String nameOfBatch){
        String numberOfBatch = String.valueOf(Integer.parseInt(extractNumber(nameOfBatch, "Партия ")) + 1);
        return $x("//div[@id='v-pills-tabContent']/div[" + numberOfBatch + "]" +
                "//div[contains(text(), 'Материал исследования: ')]/following-sibling::div").should(Condition.appear).getText().equals(material);
    }
    private boolean isResearchCountCorrect
            (String researchCount, String nameOfBatch){
        String numberOfBatch = String.valueOf(Integer.parseInt(extractNumber(nameOfBatch, "Партия ")) + 1);
        return $x("//div[@id='v-pills-tabContent']/div[" + numberOfBatch + "]" +
                "//td[contains(text(), 'Заболевание')]/parent::tr/following-sibling::tr/td[2]").should(Condition.appear).getText().equals(researchCount);
    }

}
