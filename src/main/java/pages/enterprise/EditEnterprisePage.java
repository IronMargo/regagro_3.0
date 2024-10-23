package pages.enterprise;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class EditEnterprisePage {
    public final SelenideElement nameOfEnterpriseField = $x("//textarea[@name='name']");
    public final SelenideElement saveButton = $x("//button[@id='submitFormsBtn']");
    public final SelenideElement heading = Selenide.$x("//h2[text()='Редактирование производственной площадки']");
}
