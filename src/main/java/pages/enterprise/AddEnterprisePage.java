package pages.enterprise;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class AddEnterprisePage {
    public final SelenideElement heading = $x("//h2[text()='Регистрация производственной площадки']");
    public final SelenideElement chooseOwnerButton = $x("//button[contains(text(),'Выбрать владельца')]");
    public final SelenideElement nameOfEnterpriseField = $x("//textarea[@name='name']");
    public final SelenideElement typeOfEnterprise = $x("//select[@name='enterprise_type_id']/following-sibling::*/*/*[@aria-controls='select2--container']");
    public final SelenideElement input = $x("//*[@class='select2-search__field']");
    public final SelenideElement districtSelection = $x("//select[@name='district_code']/following-sibling::*/*/*[@aria-controls='select2--container']");
    public final SelenideElement citySelection = $x("//select[@name='locality_code']/following-sibling::*/*/*[@aria-controls='select2--container']");
    public final SelenideElement streetSelection = $x("//select[@name='street_code']/following-sibling::*/*/*[@aria-controls='select2--container']");
    public final SelenideElement houseNumber = $x("//*[contains(text(),'Дом')]/..//*[@class='col-6 mb-3 form-group pb-3']//input[@class='form-control']");
    public final SelenideElement serviceAreaSelection = $x("//select[@name='service_area_id']/following::*[@aria-labelledby='select2--container']");
    public final SelenideElement activateRegistrationButton = $("#submitFormsBtn");
}
