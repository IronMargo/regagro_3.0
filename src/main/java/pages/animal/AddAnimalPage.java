package pages.animal;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class AddAnimalPage {
    public final SelenideElement heading = $x("//h2[contains(text(), 'Регистрация')]");
    public final SelenideElement findObjectButton = $x("//button[contains(text(),'Выбрать объект')]");
    public final SelenideElement animalKindSelect = $x("//*[contains(text(),'Вид животного')]/..//*[contains(@class,'select2-container--bootstrap-5')]");
    public final SelenideElement input = $x("//*[@class='select2-search__field']");
    public final SelenideElement markerTypeSelect = $x("//*[@name='marker_type_id']/following-sibling::*//*[@aria-labelledby='select2--container']");
    public final SelenideElement identificationNumberField = $x("//*[@name='marker_type_id']//ancestor::*[@class='identification-wrapper']/*[1]//input");
    public final SelenideElement markerPlacesSelect = $x("//*[@name='marker_place_id']/following-sibling::*[@dir='ltr']");
    public final SelenideElement markerDate = $x("//*[contains(text(), 'Дата маркирования')]//parent::*//i[@class='bi bi-calendar3']");
    public final SelenideElement markerDateInput = $x("//input[@class='form-control flatpickr input active']");
    public final SelenideElement foundationSelect = $x("//*[contains(text(), 'Основание')]/following-sibling::*//*[@dir='ltr']");
    public final SelenideElement suitSelect = $x("//*[contains(text(), 'Масть')]/following-sibling::*//*[@dir='ltr']");
    public final SelenideElement birthDate = $x("//*[contains(text(), 'Дата рождения')]/following-sibling::*//i[@class='bi bi-calendar3']");
    public final SelenideElement birthDateFrom = $x("//*[contains(text(), 'Диапазон дат рождения')]/following-sibling::*[1]//input[2]");
    public final SelenideElement birthDateBefore = $x("//*[contains(text(), 'Диапазон дат рождения')]/following-sibling::*[2]//input[2]");
    public final SelenideElement birthDateForBees = $x("//*[contains(text(), 'Дата заселения улья')]/following-sibling::*[1]//input[2]");
    public final SelenideElement birthDateInput = $x("//input[@class='form-control flatpickr input active']");
    public final SelenideElement genderFemaleCheckBox = $("input[id=female]");
    public final SelenideElement genderMaleCheckBox = $("input[id=male]");
    public final SelenideElement genderMixedCheckBox = $("input[id=mixed_gender]");
    public final SelenideElement countOfMaleInput = $x("//*[contains(text(), 'Самцы')]/parent::*//input");
    public final SelenideElement countOfFemaleInput = $x("//*[contains(text(), 'Самки')]/parent::*//input");
    public final SelenideElement nickNameInput = $x("//*[contains(text(), 'Кличка')]/following::input[@type='text']");
    public final SelenideElement keepTypeSelect = $x("//select[@name='keep_type_id']/following-sibling::*//*[@class='select2-selection select2-selection--single']");
    public final SelenideElement keepPlaceSelect = $x("//select[@name='keep_place_id']/following-sibling::*//*[@class='select2-selection select2-selection--single']");
    public final SelenideElement productDirectionSelect = $x("//select[@name='product_direction_id']/following-sibling::*//*[@class='select2-selection select2-selection--single']");
    public final SelenideElement activateButton = $x("//button/*[contains(text(),'Завершить')]");
    public final SelenideElement openPassport = $x("//button[contains(text(), 'Открыть паспорт')]");
    public final SelenideElement closeModalWindow = $x("//button[@aria-label='Close'][@class='btn-close position-absolute']");
}
