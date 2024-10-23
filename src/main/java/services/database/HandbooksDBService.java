package services.database;

import java.util.List;

import static com.codeborne.selenide.Selenide.$x;
import static enums.DbNames.HANDBOOKS_DB_NAME;

public class HandbooksDBService extends DBService {
    protected HandbooksDBService() {
        conn = getConnection(HANDBOOKS_DB_NAME);
    }

    // Проверка, соответствуют ли виды животных заболеванию
    public void isKindsCorrectForDisease(String disease) {
        List<String> kinds = values("SELECT kinds.name FROM disease_kinds " +
                "JOIN diseases ON diseases.id = disease_kinds.disease_id " +
                "JOIN kinds ON disease_kinds.kind_id = kinds.id \n" +
                "WHERE diseases.name = '" + disease + "'", "name");
        for (String kind : kinds) {
            if ($x("//span[contains(text(), '" + kind + "')]").isDisplayed()) {
                return;
            }
        }
    }

    // Получение значения типа String по id
    public String getValue(int id, String tableName) {
        return values("SELECT name FROM " + tableName + " WHERE id = '" + id + "'", "name").get(0);
    }

    // Получение id элемента
    public String getId(String name, String tableName) {
        List<Integer> ids = valuesInt("SELECT id FROM " + tableName + " \n" +
                "WHERE name = '" + name + "'", "id");
        return ids.get(0).toString();
    }

    // Получение списка СМ по виду из справочника
    public List<String> getMarkerTypesForKind(int kindId) {
        return values("SELECT DISTINCT marker_types.name FROM marker_types \n" +
                "JOIN kind_marker_places ON kind_marker_places.marker_type_id = marker_types.id \n" +
                "WHERE kind_marker_places.kind_id = " + kindId + "", "name");
    }
}
