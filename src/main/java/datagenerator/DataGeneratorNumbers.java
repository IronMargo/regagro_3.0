package datagenerator;

import com.github.javafaker.Faker;
import services.database.DBService;
import services.database.RegagroDBService;

import java.util.Locale;
import java.util.Random;

public class DataGeneratorNumbers {
    private static final Faker faker = new Faker(new Locale("ru"));

    private DataGeneratorNumbers() {
    }

    // Получение рандомного уникального номера животного определенной длины
    public static String getNumber(int length) {
        RegagroDBService regagroBD = DBService.getRegagroDBService();
        String number;
        do {
            number = faker.number().digits(length);
        }
        while (regagroBD.isValueInDatabase("number", "animals", number));
        return number;
    }

    // Получение числа в заданном диапазоне
    public static int range(int min, int max) {
        Random random = new Random();
        return min + random.nextInt(max - min + 1);
    }

    // Получение рандомного числа указанной длины
    public static String getCount(int length) {
        return faker.number().digits(length);
    }


    // Получение рандомного числа указанной длины с указанием начала
    public static String getNumberWithFirst(String firstNumber, int length) {
        return firstNumber + faker.number().digits(length);
    }

    // Получение числа в заданном диапазоне в виде строки
    public static String getNumberRange(int from, int before) {
        return String.valueOf(range(from, before));
    }

    // Извлечение подстроки
    public static String extractNumber(String fullString, String prefix) {
        if (fullString == null || !fullString.startsWith(prefix)) {
            throw new IllegalArgumentException("Некорректное имя партии: " + fullString);
        }

        String numberStr = fullString.substring("Партия ".length());
        try {
            return numberStr;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Не удалось извлечь номер партии из: " + fullString);
        }
    }
}
