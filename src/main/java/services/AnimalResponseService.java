package services;

import entities.animals.AnimalGroupResponse;
import entities.animals.AnimalIdentificationResponse;
import enums.MarkerDates;
import services.database.DBService;
import services.database.HandbooksDBService;
import services.database.RegagroDBService;
import io.qameta.allure.internal.shadowed.jackson.core.JsonProcessingException;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static datagenerator.DataGeneratorDate.*;
import static enums.MarkerTypes.NOT_EMISSION_INDIVIDUAL_MARKER_TYPES_AFTER_MARCH;
import static enums.MarkerTypes.NOT_EMISSION_INDIVIDUAL_MARKER_TYPES_BEFORE_MARCH;

public class AnimalResponseService {
    private final RegagroDBService regagroDBService = DBService.getRegagroDBService();
    private final HandbooksDBService handbooksDBService = DBService.getHandbooksDBService();
    private static final Random random = new Random();
    // Получение полной информации о группе животных
    public AnimalGroupResponse getAnimalGroupFullInfo(String animalNumber, Map<String, String> cookies) throws JsonProcessingException {
        String animalId = regagroDBService.getAnimalId(animalNumber);
        String path = "/ajax/animals/" + animalId + "";
        String responseJson = ConverterResponseService.doGetReturnItem(
                RequestSpecificationCreator.getReqSpecWithPath(cookies, path), 200);
        // Десериализация
        ObjectMapper objectMapper = new ObjectMapper();
        AnimalGroupResponse response = objectMapper.readValue(responseJson, AnimalGroupResponse.class);

        Map<String, Integer> animalCountRange = response.getAnimalCountRange();
        response.setCount_male(animalCountRange.get("count_male"));
        response.setCount_female(animalCountRange.get("count_female"));

        Map<String, Object> supervisedObjectInfo = response.getSupervisedObjectInfo();
        response.setOwner_id((Integer) supervisedObjectInfo.get("owner_id"));
        return response;
    }

    // Получение сведений об идентификации животного
    public AnimalIdentificationResponse getAnimalInfoFromAjax(String animalNumber, Map<String, String> cookies) throws JsonProcessingException {
        String animalId = regagroDBService.getAnimalId(animalNumber);
        String path = "/ajax/animals/" + animalId + "";
        String responseJson = ConverterResponseService.doGetReturnItem(RequestSpecificationCreator.getReqSpecWithPath(cookies, path), 200);
        // Десериализация
        ObjectMapper objectMapper = new ObjectMapper();
        AnimalIdentificationResponse response = objectMapper.readValue(responseJson, AnimalIdentificationResponse.class);
        return response;
    }
    // Получение даты в зависимости от необходимых условий
    public String generateDate(String animalNumber, Map<String, String> cookies, MarkerDates markerDate) throws JsonProcessingException {
        AnimalIdentificationResponse animalIdentificationResponse = getAnimalInfoFromAjax(animalNumber, cookies);
        AnimalGroupResponse animalGroupResponse = getAnimalGroupFullInfo(animalNumber, cookies);
        LocalDate markerDateCurrent = convertDateStringToLocalDate(animalIdentificationResponse.getMarker_date());
        LocalDate birthDate = convertDateStringToLocalDate(animalGroupResponse.getBirth_date());
        switch (markerDate) {
            case MARKER_DATE_BEFORE_CURRENT:
                return generateDateBetweenDates(birthDate, markerDateCurrent);
            case MARKER_DATE_BEFORE_MARCH:
                return generateDateBetweenDates(birthDate, LocalDate.of(2024, 3, 1));
            case MARKER_DATE_AFTER_MARCH:
                return generateDateBetweenDates(birthDate, LocalDate.now());
            case CURRENT_DATE:
                return getCurrentDay();
            default:
                throw new IllegalArgumentException("Не удалось определить даты у животного с номером: " + animalNumber);
        }
    }
    public String getNewMainMarkerType(String animalNumber, Map<String, String> cookies, String markerDate) throws JsonProcessingException {

        // Фильтруем список СМ и оставляем только те, которые есть в списке notEmissionIndividualMarkerTypes
        // и не равен имеющемуся у животного




        // Получаем информацию о животном и его группе.
        AnimalIdentificationResponse animalIdentificationResponse = getAnimalInfoFromAjax(animalNumber, cookies);
        AnimalGroupResponse animalGroupResponse = getAnimalGroupFullInfo(animalNumber, cookies);
                // Исключаем из списка:
        //    - тип СМ, который уже есть у животного
        //    - типы СМ, не соответствующие "не эмиссионные индивидуальные"
        List<String> markerTypes = new ArrayList<>();
        if (isDateBeforeMarch(markerDate)) {
            markerTypes = handbooksDBService.getMarkerTypesForKind(animalGroupResponse.getKind_id()).stream()
                    .filter(markerType -> !handbooksDBService.getValue(animalIdentificationResponse.getMarker_type_id(), "marker_types").equals(markerType))
                    .filter(markerType -> NOT_EMISSION_INDIVIDUAL_MARKER_TYPES_AFTER_MARCH.contains(markerType))
                    .collect(Collectors.toList());
        } else {
            markerTypes = handbooksDBService.getMarkerTypesForKind( animalGroupResponse.getKind_id()).stream()
                    .filter(markerType -> !handbooksDBService.getValue(animalIdentificationResponse.getMarker_type_id(), "marker_types").equals(markerType))
                    .filter(markerType -> NOT_EMISSION_INDIVIDUAL_MARKER_TYPES_BEFORE_MARCH.contains(markerType))
                    .collect(Collectors.toList());
        }

        // Проверяем, что список не пустой
        if (markerTypes.isEmpty()){
            throw new IllegalArgumentException("Нет доступных СМ, соответствующих условиям");
        }
        // Возвращаем случайный тип СМ из полученного списка.
        return markerTypes.get(random.nextInt(markerTypes.size()));
    }
}
