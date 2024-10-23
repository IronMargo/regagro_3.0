package services;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class RequestSpecificationCreator {
    private RequestSpecificationCreator() {
    }

    public static RequestSpecification getReqSpec(Map<String, String> cookies) {
        String baseUri = ConfigReader.getProperty("environment").equals("dev") ? "https://v3.dev.regagro.ru" : "https://v3.regagro.ru";
        String sessionCookieName = ConfigReader.getProperty("environment").equals("dev") ? "laravel_session" : "regagro_session";

        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                //.addCookies(cookies)
                .addHeader("Cookie", sessionCookieName + "=" + cookies.get(sessionCookieName))
                .addHeader("X-XSRF-TOKEN", cookies.get("XSRF-TOKEN"))
                .setContentType(ContentType.JSON)
                .build();
    }

    public static RequestSpecification getReqSpecWithPath(Map<String, String> cookies, String path) {
        String baseUri = ConfigReader.getProperty("environment").equals("dev") ? "https://v3.dev.regagro.ru" : "https://v3.regagro.ru";
        String sessionCookieName = ConfigReader.getProperty("environment").equals("dev") ? "laravel_session" : "regagro_session";

        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setBasePath(path)
                .addHeader("Cookie", sessionCookieName + "=" + cookies.get(sessionCookieName))
                .addHeader("X-XSRF-TOKEN", cookies.get("XSRF-TOKEN"))
                .setContentType(ContentType.JSON)
                .build();
    }

    public static RequestSpecification getReqSpecWithPath(Cookies cookies, String path) {
        String baseUri = ConfigReader.getProperty("environment").equals("dev") ? "https://v3.dev.regagro.ru" : "https://v3.regagro.ru";
        String sessionCookieName = ConfigReader.getProperty("environment").equals("dev") ? "laravel_session" : "regagro_session";

        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setBasePath(path)
                .addHeader("Cookie", sessionCookieName + "=" + cookies.get(sessionCookieName))
                .addHeader("X-XSRF-TOKEN", String.valueOf(cookies.get("XSRF-TOKEN")))
                .setContentType(ContentType.JSON)
                .build();
    }

    public static RequestSpecification getBaseSpec() {
        String baseUri = ConfigReader.getProperty("environment").equals("dev") ? "https://v3.dev.regagro.ru" : "https://v3.regagro.ru";
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setContentType(ContentType.JSON)
                .build();
    }
}