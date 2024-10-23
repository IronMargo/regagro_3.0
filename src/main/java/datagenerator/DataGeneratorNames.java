package datagenerator;

import com.github.javafaker.Faker;

import java.util.Locale;

public class DataGeneratorNames {
    private static final Faker faker = new Faker(new Locale("ru"));

    private DataGeneratorNames() {
    }

    public static String getNickname() {
        return faker.name().firstName();
    }

    public static String getEnterpriseName() {
        return faker.company().name();
    }

}
