package apitests;

import com.codeborne.selenide.logevents.SelenideLogger;
import enums.User;
import services.Configurations;
import services.RequestSpecificationCreator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import steps.general.HomeSteps;

import java.util.Map;

import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewPagesDEVTest {
    private final Configurations configurations = new Configurations();
    private static Map<String, String> cookies;
    private final HomeSteps homeSteps = new HomeSteps();
    private User user;
    private static String currentRole;

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
        closeWebDriver();
    }

    private void checkRole(String role) {
        if (cookies == null) {
            user = User.getUserByRole(role);
            assert user != null;
            cookies = configurations.getCookiesMAP(user);
            currentRole = role;
        }
        if (!currentRole.equals(role)) {
            homeSteps.logoutWithApi(cookies);
            cookies = configurations.getCookiesMAP(User.getUserByRole(role));
            currentRole = role;
        }
    }

    @Tag("API_TEST")
    @Tag("REGRESS")
    @ParameterizedTest(name = "{index} - {0} - {2}")
    @CsvFileSource(resources = "/endpoints/ajaxEndpoints.csv")
    @DisplayName("Загрузка таблиц")
    void getTable200Vet(String role, String path, String message) {
        checkRole(role);
        // Создаем запрос с параметрами
        RequestSpecification request = given()
                .spec(RequestSpecificationCreator.getReqSpec(cookies))
                .log().all(); // Включаем логирование
        // Проверяем наличие '?' в пути и формируем полный путь
        String fullPath = path.contains("?") ?
                path + "&per_page=10&page=1&size=10" :
                path + "?per_page=10&page=1&size=10";
        // Отправляем GET-запрос
        Response response = request.get(fullPath);
        // Получаем статус-код ответа
        int actualStatusCode = response.getStatusCode();
        // Проверяем статус-код
        assertEquals(200, actualStatusCode, "Ошибка при выполнении запроса: " + message);
    }
    @Tag("API_TEST")
    @Tag("REGRESS")
    @ParameterizedTest(name = "{index} - {0} - {2}")
    @CsvFileSource(resources = "/endpoints/endpoints.csv")
    @DisplayName("Загрузка страниц")
    void getPages200Vet(String role, String path, String message) {
        checkRole(role);
        // Создаем запрос с параметрами
        RequestSpecification request = given()
                .spec(RequestSpecificationCreator.getReqSpec(cookies))
                .log().all(); // Включаем логирование
        // Отправляем GET-запрос
        Response response = request.get(path);
        // Получаем статус-код ответа
        int actualStatusCode = response.getStatusCode();
        // Проверяем статус-код
        assertEquals(200, actualStatusCode, "Ошибка при загрузке страницы: " + message);
    }
}
