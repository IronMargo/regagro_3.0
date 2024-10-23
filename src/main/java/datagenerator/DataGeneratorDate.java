package datagenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DataGeneratorDate {
    private static final Random random = new Random();
    private static final LocalDate markerDateBeforeMarch = LocalDate.of(2024, Month.FEBRUARY, 28);

    private DataGeneratorDate() {
    }

    public static String getDateBeforeMarchString() {
        return markerDateBeforeMarch.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
    }

    public static String getCurrentDay() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
    }

    public static List<String> getDateRange(String ground) {
        List<String> dateRange = new ArrayList<>();
        int randomInt;

        if (ground.contains("Рождение")) {
            randomInt = random.nextInt(6); // Рандомное число от 1 до 6 для "Рождение"
        } else {
            randomInt = random.nextInt(10) + 1; // Рандомное число от 1 до 10 для других случаев
        }

        LocalDate fromDate = markerDateBeforeMarch.minusMonths(randomInt);
        LocalDate toDate = fromDate.plusMonths(1);

        if (fromDate.isAfter(markerDateBeforeMarch)) {
            fromDate = markerDateBeforeMarch;
        }

        dateRange.add(fromDate.format(DateTimeFormatter.ofPattern("ddMMyyyy")));
        dateRange.add(toDate.format(DateTimeFormatter.ofPattern("ddMMyyyy")));

        return dateRange;
    }

    public static boolean isDateBeforeMarch(LocalDate date) {
        return !date.isAfter(markerDateBeforeMarch);
    }

    public static boolean isDateBeforeMarch(String date) {
        LocalDate dateConvert = convertDateStringToLocalDate(date);
        return !dateConvert.isAfter(markerDateBeforeMarch);
    }

    public static LocalDate convertDateStringToLocalDate(String date) {
        List<DateTimeFormatter> formatters = Arrays.asList(
                DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if (date == null | date.isEmpty()) {
            throw new IllegalArgumentException("Дата отсутствует или указана некорректно");
        }
        for (DateTimeFormatter formatter : formatters) {
            try {
                LocalDateTime needDate = LocalDateTime.parse(date, formatter);
                return needDate.toLocalDate();
            } catch (DateTimeParseException e) {
                // Пропускаем ошибку и переходим к следующему формату
            }
        }
        throw new IllegalArgumentException("Не удалось преобразовать дату: " + date);
    }

    // Парсинг входной даты из строки в объект Date
    public static LocalDate convertStringToDate(String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        LocalDateTime convertDate = LocalDateTime.parse(date, formatter);
        return convertDate.toLocalDate();
    }

    // Парсинг входной даты из строки в строку нужного формата "ddMMyyyy"
    public static String convertDateToNeedFormat(String date) {
        try {
            // Парсинг входной даты из строки в объект Date
            SimpleDateFormat inputFormat = new SimpleDateFormat("ddMMyyyy");
            Date inputDate = inputFormat.parse(date);

            // Форматирование объекта Date в строку с нужным форматом
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy");
            return outputFormat.format(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String generateDateBetweenDates(LocalDate birthDate, LocalDate markerDate) {
        if (markerDate == null || birthDate == null) {
            throw new IllegalArgumentException("Дата маркирования или дата рождения не могут быть null");
        }
        if (markerDate.isBefore(birthDate) || markerDate.isEqual(birthDate)) {
            throw new IllegalArgumentException("Дата маркирования не может быть меньше или равна дате рождения");
        }
        int daysBetweenDates = (int) ChronoUnit.DAYS.between(birthDate, markerDate);
        if (daysBetweenDates <= 0) {
            throw new IllegalArgumentException(
                    "Между датой рождения: " + birthDate + " и датой маркирования: " + markerDate + " меньше одного дня");
        }
        long randomDaysCount = random.nextInt(daysBetweenDates);
        LocalDate newMarkerDate = birthDate.plusDays(randomDaysCount);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        return newMarkerDate.format(formatter);
    }


}
