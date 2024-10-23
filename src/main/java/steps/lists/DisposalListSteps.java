package steps.lists;

import com.codeborne.selenide.Condition;
import pages.lists.ListsPage;

import static com.codeborne.selenide.Condition.*;

public class DisposalListSteps {
    ListsPage locators = new ListsPage();
    public DisposalListSteps() {
        locators.disposalsListHeading.should(appear);
    }
}
