package datagenerator;

import entities.animals.AnimalData;
import entities.animals.AnimalIdentificationData;
import entities.animals.AnimalIdentificationResponse;
import factory.AnimalIdentificationDataFactory;
import services.AnimalResponseService;
import services.database.DBService;
import services.database.HandbooksDBService;
import services.database.RegagroDBService;
import io.qameta.allure.internal.shadowed.jackson.core.JsonProcessingException;

import java.util.Map;
import java.util.stream.Stream;

import static datagenerator.DataGeneratorDate.getCurrentDay;
import static datagenerator.DataGeneratorDate.getDateBeforeMarchString;
import static datagenerator.DataGeneratorNumbers.getNumber;
import static enums.MarkerDates.*;
import static enums.MarkerTypes.COLLAR;
import static enums.MarkerTypes.ON_FARM_BIRK;

public class DataGeneratorForCheckLists {
    private final String markerDateBeforeMarch = getDateBeforeMarchString();
    private final String currentDay = getCurrentDay();
    private final RegagroDBService regagroDBService = DBService.getRegagroDBService();
    private final HandbooksDBService handbooksDBService = DBService.getHandbooksDBService();
    private final AnimalIdentificationDataFactory identificationDataFactory = new AnimalIdentificationDataFactory();
    private final AnimalResponseService animalResponseService = new AnimalResponseService();

    private String getMarkerTypeSame(String animalNumber, Map<String, String> cookies) throws JsonProcessingException {
        AnimalIdentificationResponse animalIdentificationResponse = animalResponseService.getAnimalInfoFromAjax(animalNumber, cookies);
        return handbooksDBService.getValue(animalIdentificationResponse.getMarker_type_id(),"marker_types");
    }


    // Данные для добавления СМ при идентификации
    public Stream<AnimalIdentificationData> generateAnimalDataForAddNewNumber(String scopeType, String animalNumber, Map<String, String> cookies) throws JsonProcessingException {
        switch (scopeType){
            case "Добавление нового основного средства маркирования. Валидные данные":
                return Stream.of(
                        identificationDataFactory.createNewIdentificationData(
                                animalNumber, getNumber(15), CURRENT_DATE, cookies),
                        identificationDataFactory.createNewIdentificationData(
                                animalNumber, getNumber(15), MARKER_DATE_BEFORE_CURRENT, cookies),
                        identificationDataFactory.createNewIdentificationData(
                                animalNumber, ON_FARM_BIRK.getMarkerType(), getNumber(15), MARKER_DATE_BEFORE_MARCH, cookies),
                        identificationDataFactory.createNewIdentificationData(
                                animalNumber, COLLAR.getMarkerType(), getNumber(15), MARKER_DATE_BEFORE_MARCH, cookies));
            case "Добавление нового основного средства маркирования. Невалидные данные":
                return Stream.of(
                        identificationDataFactory.createNewIdentificationData(
                                animalNumber, ON_FARM_BIRK.getMarkerType(), getNumber(15), MARKER_DATE_AFTER_MARCH, cookies),
                        identificationDataFactory.createNewIdentificationData(
                                animalNumber, COLLAR.getMarkerType(), getNumber(15), MARKER_DATE_AFTER_MARCH, cookies));
            default:
                throw new IllegalArgumentException("Неизвестный тип группы тестов: " + scopeType);
        }
    }

    // Данные для регистрации основными неэмиссионных  СМ (РОСМ)
    public Stream<AnimalData> generateAnimalData(String scopeType, String supervisedObjectName) {
        switch (scopeType) {
            case "Групповая регистрация. Валидные данные":
                return Stream.of(
                        new AnimalData("Свиньи", "Внутрихозяйственное табло", getNumber(10), markerDateBeforeMarch));
            case "Групповая регистрация. Неалидные данные":
                return Stream.of(
                        new AnimalData(
                                "Свиньи", "Внутрихозяйственное табло", regagroDBService.getNotUniqueNumberForSupervisedObject("Внутрихозяйственное табло", supervisedObjectName), markerDateBeforeMarch),
                        new AnimalData(
                                "Свиньи", "Внутрихозяйственное табло", getNumber(10), currentDay),
                        new AnimalData(
                                "Свиньи", "Табло", DataGeneratorNumbers.getNumberWithFirst("RU2", 9), currentDay));
            case "Индивидуальная регистрация. Валидные данные":
                return Stream.of(
                        new AnimalData(
                                "Крупный рогатый скот", "Чип", getNumber(15), markerDateBeforeMarch),
                        new AnimalData(
                                "Крупный рогатый скот", "Болюс", getNumber(15), markerDateBeforeMarch),
                        new AnimalData(
                                "Куры", "Кольцо", getNumber(10), markerDateBeforeMarch),
                        new AnimalData(
                                "Куры", "Электронное кольцо", getNumber(14), markerDateBeforeMarch),
                        new AnimalData(
                                "Крупный рогатый скот", "Ошейник", getNumber(13), markerDateBeforeMarch),
                        new AnimalData(
                                "Крупный рогатый скот", "Электронный ошейник", getNumber(15), markerDateBeforeMarch),
                        new AnimalData(
                                "Крупный рогатый скот", "Электронный ошейник", getNumber(16), markerDateBeforeMarch),
                        new AnimalData(
                                "Куры", "Крыло-метка", getNumber(12), markerDateBeforeMarch),
                        new AnimalData(
                                "Куры", "Электронное крыло-метка", getNumber(14), markerDateBeforeMarch),
                        new AnimalData(
                                "Крупный рогатый скот", "Эл. метка", getNumber(15), markerDateBeforeMarch),
                        new AnimalData(
                                "Куры", "Иностранная бирка", getNumber(10), markerDateBeforeMarch),
                        new AnimalData(
                                "Крупный рогатый скот", "Внутрихозяйственная бирка", getNumber(8), markerDateBeforeMarch),
                        new AnimalData(
                                "Крупный рогатый скот", "Электронный ошейник", getNumber(15), currentDay),
                        new AnimalData(
                                "Куры", "Электронное кольцо", getNumber(15), currentDay),
                        new AnimalData(
                                "Куры", "Электронное крыло-метка", getNumber(15), currentDay),
                        new AnimalData(
                                "Крупный рогатый скот", "Болюс", getNumber(15), currentDay),
                        new AnimalData(
                                "Крупный рогатый скот", "Чип", getNumber(15), currentDay),
                        new AnimalData(
                                "Крупный рогатый скот", "Эл. метка", getNumber(15), currentDay));
            case "Индивидуальная регистрация. Неалидные данные. Ошибка валидации":
                return Stream.of(
                        new AnimalData(
                                "Крупный рогатый скот", "Внутрихозяйственная бирка", regagroDBService.getNotUniqueNumberForSupervisedObject("Внутрихозяйственная бирка", supervisedObjectName), markerDateBeforeMarch),
                        new AnimalData(
                                "Куры", "Электронное кольцо", getNumber(14), currentDay),
                        new AnimalData(
                                "Куры", "Кольцо", DataGeneratorNumbers.getNumberWithFirst("RU1", 9), currentDay),
                        new AnimalData(
                                "Крупный рогатый скот", "Болюс", regagroDBService.getNotUniqueNumber("Болюс"), currentDay),
                        new AnimalData(
                                "Крупный рогатый скот", "Номер REGAGRO", getNumber(9), currentDay),
                        new AnimalData(
                                "Крупный рогатый скот", "Чип", getNumber(14), currentDay),
                        new AnimalData(
                                "Крупный рогатый скот", "Эл. метка", getNumber(14), currentDay),
                        new AnimalData(
                                "Крупный рогатый скот", "Эл. метка", getNumber(14), markerDateBeforeMarch),
                        new AnimalData(
                                "Крупный рогатый скот", "Ошейник", DataGeneratorNumbers.getNumberWithFirst("RU1", 9), currentDay),
                        new AnimalData(
                                "Куры", "Крыло-метка", DataGeneratorNumbers.getNumberWithFirst("RU1", 9), currentDay),
                        new AnimalData(
                                "Куры", "Кольцо", DataGeneratorNumbers.getNumberWithFirst("RU1", 9), currentDay),
                        new AnimalData(
                                "Крупный рогатый скот", "Ошейник", getNumber(16), currentDay),
                        new AnimalData(
                                "Куры", "Крыло-метка", getNumber(16), currentDay));
            case "Индивидуальная регистрация. Невалидные данные с некорректной регистрацией":
                return Stream.of(
                        new AnimalData(
                                "Куры", "Электронное крыло-метка", getNumber(16), currentDay),
                        new AnimalData(
                                "Крупный рогатый скот", "Электронный ошейник", getNumber(16), currentDay));
            case "Регистрация основными СМ для видов животных":
                return Stream.of(
                        new AnimalData("Крупный рогатый скот", "Чип", getNumber(15), currentDay),
                        new AnimalData("Крупный рогатый скот", "Болюс", getNumber(15), currentDay),
                        new AnimalData("Крупный рогатый скот", "Эл. метка", getNumber(15), currentDay),
                        new AnimalData("Крупный рогатый скот", "Электронный ошейник", getNumber(15), currentDay),
                        new AnimalData("Лошади", "Чип", getNumber(15), currentDay),
                        new AnimalData("Верблюды", "Чип", getNumber(15), currentDay),
                        new AnimalData("Верблюды", "Болюс", getNumber(15), currentDay),
                        new AnimalData("Верблюды", "Эл. метка", getNumber(15), currentDay),
                        new AnimalData("Свиньи", "Чип", getNumber(15), currentDay),
                        new AnimalData("Свиньи", "Эл. метка", getNumber(15), currentDay),
                        new AnimalData("Козы", "Чип", getNumber(15), currentDay),
                        new AnimalData("Овцы", "Болюс", getNumber(15), currentDay),
                        new AnimalData("Козы", "Эл. метка", getNumber(15), currentDay),
                        new AnimalData("Овцы", "Электронный ошейник", getNumber(15), currentDay),
                        new AnimalData("Куры", "Чип", getNumber(15), currentDay),
                        new AnimalData("Куры", "Электронное кольцо", getNumber(15), currentDay),
                        new AnimalData("Гуси", "Электронное крыло-метка", getNumber(15), currentDay),
                        new AnimalData("Олени", "Чип", getNumber(15), currentDay),
                        new AnimalData("Олени", "Эл. метка", getNumber(15), currentDay),
                        new AnimalData("Олени", "Электронный ошейник", getNumber(15), currentDay),
                        new AnimalData("Кролики", "Чип", getNumber(15), currentDay),
                        new AnimalData("Лисицы", "Чип", getNumber(15), currentDay));
            default:
                throw new IllegalArgumentException("Неизвестный тип группы тестов: " + scopeType);
        }
    }
}
