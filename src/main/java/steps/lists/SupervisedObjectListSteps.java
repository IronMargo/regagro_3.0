package steps.lists;

import abstractclass.Steps;
import pages.lists.ListsPage;

import static com.codeborne.selenide.Condition.appear;

public class SupervisedObjectListSteps extends Steps {
    private static final ListsPage locators = new ListsPage();

    public SupervisedObjectListSteps() {
        locators.supervisedObjectsHeading.should(appear);
    }




}
