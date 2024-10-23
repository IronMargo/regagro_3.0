package services;

import abstractclass.PageElement;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import enums.User;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.Cookie;
import steps.general.AuthSteps;
import steps.general.HomeSteps;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;

public class Configurations {
    private String baseUrl;

    public void setStartPage() {
        baseUrl = ConfigReader.getProperty("environment").equals("dev") ? ConfigReader.getProperty("baseUrl_dev") : ConfigReader.getProperty("baseUrl_prod");
        open(baseUrl);
    }

    public void setUpConfigurationsWithAuth(User user) {
        SelenideLogger.addListener("allure", new AllureSelenide());
        Configuration.timeout = Integer.parseInt(ConfigReader.getProperty("timeout"));
        Configuration.holdBrowserOpen = Boolean.parseBoolean(ConfigReader.getProperty("holdBrowserOpen"));
        Configuration.headless = Boolean.parseBoolean(ConfigReader.getProperty("headless"));
        baseUrl = ConfigReader.getProperty("environment").equals("dev") ? ConfigReader.getProperty("baseUrl_dev") : ConfigReader.getProperty("baseUrl_prod");

        open(baseUrl);
        AuthSteps authSteps = new AuthSteps();
        authSteps.auth(user);
    }

    public void setUpConfigurationsWithCookies(Map<String, String> cookies) {
        SelenideLogger.addListener("allure", new AllureSelenide());
        Configuration.timeout = Integer.parseInt(ConfigReader.getProperty("timeout"));
        Configuration.holdBrowserOpen = Boolean.parseBoolean(ConfigReader.getProperty("holdBrowserOpen"));
        Configuration.headless = Boolean.parseBoolean(ConfigReader.getProperty("headless"));
        baseUrl = ConfigReader.getProperty("environment").equals("dev") ? ConfigReader.getProperty("baseUrl_dev") : ConfigReader.getProperty("baseUrl_prod");
        open(baseUrl);

        // Добавляем куки в браузер (кроме CSRF-токена)
        cookies.entrySet().stream()
                .filter(entry -> !entry.getKey().equals("X-Csrf-Token"))
                .forEach(entry -> {
                    Cookie cookie = new Cookie(entry.getKey(), entry.getValue());
                    WebDriverRunner.getWebDriver().manage().addCookie(cookie);
                });

        // Обновляем страницу, чтобы куки вступили в силу
        Selenide.refresh();
    }

    public void setUpConfigurationsApi(User user) {
        Map<String, String> cookies = getCookiesMAP(user);
        SelenideLogger.addListener("allure", new AllureSelenide());
        Configuration.timeout = Integer.parseInt(ConfigReader.getProperty("timeout"));
        Configuration.holdBrowserOpen = Boolean.parseBoolean(ConfigReader.getProperty("holdBrowserOpen"));
        Configuration.headless = Boolean.parseBoolean(ConfigReader.getProperty("headless"));
        baseUrl = ConfigReader.getProperty("environment").equals("dev") ? ConfigReader.getProperty("baseUrl_dev") : ConfigReader.getProperty("baseUrl_prod");

        open(baseUrl);

        // Добавляем куки в браузер (кроме CSRF-токена)
        cookies.entrySet().stream()
                .filter(entry -> !entry.getKey().equals("X-Csrf-Token"))
                .forEach(entry -> {
                    Cookie cookie = new Cookie(entry.getKey(), entry.getValue());
                    WebDriverRunner.getWebDriver().manage().addCookie(cookie);
                });

        // Обновляем страницу, чтобы куки вступили в силу
        Selenide.refresh();

    }

    public Map<String, String> getCookiesMAP(User user) {
        // 2. Делаем GET-запрос
        Response first = RestAssured.given()
                .spec(RequestSpecificationCreator.getBaseSpec())
                .get();
        Cookies cookiesFirst = first.getDetailedCookies();
        Response second = RestAssured.given()
                .spec(RequestSpecificationCreator.getBaseSpec())
                .cookies(cookiesFirst)
                .get("login");

        // Парсим HTML с помощью Jsoup
        Document doc = Jsoup.parse(second.asString());
        Elements metaTags = doc.select("meta[name=csrf-token]");
        String csrfToken = metaTags.first().attr("content");

        // 1. Создаем тело запроса
        LinkedHashMap<String, String> requestBody = new LinkedHashMap<>();
        requestBody.put("email", ConfigReader.getUserEmail(user.getRole()));
        requestBody.put("password", ConfigReader.getUserPassword(user.getRole()));

        // 3. Делаем POST-запрос на https://v3.dev.regagro.ru/login
        RequestSpecification request = RestAssured.given()
                .spec(RequestSpecificationCreator.getBaseSpec())
                .header("X-Csrf-Token", csrfToken)
                .cookies(second.getCookies()) // Устанавливем cookies из шага 2
                .body(requestBody)
                .log().all();
        Response response = request.post("login"); // Сохраняем ответ

        // 4. Получаем куки из response (после редиректа)
        Cookies cookiesForRoot = response.getDetailedCookies();
        String xsrfToken = cookiesForRoot.getValue("XSRF-TOKEN");
        String laravelSession = cookiesForRoot.getValue("laravel_session"); // Получаем обновленную куки

        Map<String, String> cookiesMap = new HashMap<>();
        cookiesMap.put("XSRF-TOKEN", xsrfToken);
        cookiesMap.put("laravel_session", laravelSession);
        cookiesMap.put("X-Csrf-Token", csrfToken);

        return cookiesMap;
    }

//    public Map<String, String> getCookies(User user) {
//        SelenideLogger.addListener("allure", new AllureSelenide());
//        Configuration.timeout = Integer.parseInt(ConfigReader.getProperty("timeout"));
//        Configuration.headless = Boolean.parseBoolean(ConfigReader.getProperty("headless"));
//        baseUrl = ConfigReader.getProperty("environment").equals("dev") ? ConfigReader.getProperty("baseUrl_dev") : ConfigReader.getProperty("baseUrl_prod");
//        email = ConfigReader.getUserEmail(user.getRole());
//        password = ConfigReader.getUserPassword(user.getRole());
//
//        open(baseUrl);
//        AuthSteps authSteps = new AuthSteps();
//        authSteps.authWithCredentials(email, password);
//        Map<String, String> cookies = new HashMap<>();
//        Set<Cookie> selenideCookies = WebDriverRunner.getWebDriver().manage().getCookies();
//        for (Cookie selenideCookie : selenideCookies) {
//            cookies.put(selenideCookie.getName(), selenideCookie.getValue());
//        }
//        return cookies;
//    }


    public void clear() {
        HomeSteps homeSteps = new HomeSteps();
        PageElement.closeModalWindow();
        homeSteps.logout();
        SelenideLogger.removeListener("allure");
        closeWebDriver();
        clearBrowserCache();
        clearBrowserCookies();
    }
}