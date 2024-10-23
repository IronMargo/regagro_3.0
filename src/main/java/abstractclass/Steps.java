package abstractclass;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$x;

public abstract class Steps {
    public final SelenideElement checkLoad = $x("//*[@class='modal-busy d-none']");

    // Проверка, что загрузка элементов на странице завершена
    public void checkLoad(){
        checkLoad.should(exist);
    }
}
