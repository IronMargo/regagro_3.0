package assertions;

import com.codeborne.selenide.Condition;
import entities.tasks.Inventory;
import org.assertj.core.api.SoftAssertions;

import static com.codeborne.selenide.Selenide.$x;

public class InventoryAssertions {
    SoftAssertions softAssertions = new SoftAssertions();
    public boolean isDrudAdded(Inventory inventory){
        $x("//div[contains(text(), '" + inventory.getDrugName() + "')]").should(Condition.appear);

        softAssertions.assertThat($x("//div[contains(text(), 'В наличии')]//following-sibling::div/p").should(Condition.appear)
                .getText().equals(inventory.getAvailableCount() + " " + inventory.getUnitName()));
        softAssertions.assertThat($x("//div[contains(text(), 'Серия:')]//following-sibling::div").should(Condition.appear)
                .getText().equals(inventory.getSeries()));
        softAssertions.assertThat($x("//div[contains(text(), 'Номер партии:')]//following-sibling::div").should(Condition.appear)
                .getText().equals(inventory.getNumberOfBatch()));
        softAssertions.assertThat($x("//div[contains(text(), 'Госконтроль:')]//following-sibling::div").should(Condition.appear)
                .getText().equals(inventory.getGosControl()));
        softAssertions.assertThat($x("//div[contains(text(), '" + inventory.getDrugName() + "')]").should(Condition.appear));

        softAssertions.assertAll();
        return true;
    }
}
