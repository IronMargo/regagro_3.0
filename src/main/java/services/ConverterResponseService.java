package services;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ConverterResponseService {
    public static <T> T doGetReturnItem(RequestSpecification specification,
                                        int httpStatusCode,
                                        Class<T> clazz) {
        return given()
                .spec(specification)
                .when()
                .log().all()
                .get()
                .then().statusCode(httpStatusCode)
                .log().all()
                .extract().body()
                .as(clazz);
    }
    public static String doGetReturnItem(RequestSpecification specification,
                                        int httpStatusCode) {
        return given()
                .spec(specification)
                .when()
                .log().all()
                .get()
                .then().statusCode(httpStatusCode)
                .log().all()
                .extract().body()
                .asString();
    }
}
