package assertions;

import com.codeborne.selenide.Condition;
import pages.animal.IdentificationPage;

import java.util.NoSuchElementException;

import static enums.User.ADMIN_ORG;
import static enums.User.SUPER_ADMIN;

public class IdentificationAssertions {
    IdentificationPage locators = new IdentificationPage();

    public boolean isOnIdentificationPage(String userRole) {
        try {
            if (userRole == ADMIN_ORG.getRole() | userRole == SUPER_ADMIN.getRole()) {
                locators.archiveButton.should(Condition.appear);
            }
            locators.heading.should(Condition.appear);
            locators.changeButton.should(Condition.appear);
            locators.addButton.should(Condition.appear);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Ошибка при поиске элемента на странице");
        }
        return true;
    }
}
