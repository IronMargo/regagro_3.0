package services.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static enums.DbNames.REGAGRO_DB_NAME;

public class RegagroDBService extends DBService {

    protected RegagroDBService() {
        conn = getConnection(REGAGRO_DB_NAME);
    }

    // Содержится ли значение String в БД
    public boolean isValueInDatabase(String columnName, String table, String value) {
        String select = "SELECT * FROM " + table + " WHERE " + columnName + " = " + " '" + value + "'";
        try {
            assert conn != null;
            PreparedStatement preparedStatement = conn.prepareStatement(select);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса: " + select, e);
        }
    }

    // Проверка успешного удаления из БД
    public boolean isSoftDeleted(String columnName, String value, String table) {
        String query = "SELECT deleted_at FROM " + table + " WHERE " + columnName + " = '" + value + "'";
        try {
            assert conn != null;
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery
                    (query);
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса: " + query, e);
        }
    }

    // Получение рандомного номера животного
    public String getRandomAnimalNumberFromDB() {
        List<String> animalNumbers = values("SELECT * " +
                "FROM animals " +
                "WHERE is_super_group = 0 AND is_group = 0 AND deleted_at IS NULL AND LENGTH (number) > 5", "number");
        return animalNumbers.get(random.nextInt(animalNumbers.size()));
    }

    // Получение рандомного номера животного определенной площадки
    public String getAnimalNumberFromEnterprise(String enterpriseName) {
        List<String> animalNumbers = values("SELECT * FROM animals \n" +
                "JOIN enterprises ON enterprises.id = animals.enterprise_id \n" +
                "WHERE animals.is_super_group = 0 AND  animals.is_group = 0 AND  animals.deleted_at IS NULL AND LENGTH ( animals.number) > 5 \n" +
                "AND enterprises.name = '" + enterpriseName + "'", "number");
        return animalNumbers.get(random.nextInt(animalNumbers.size()));
    }

    // Получение рандомного номера группы животных определенной площадки
    public String getAnimalGroupNumberFromEnterprise(String enterpriseName) {
        List<String> animalNumbers = values("SELECT * FROM animals \n" +
                "JOIN enterprises ON enterprises.id = animals.enterprise_id \n" +
                "WHERE animals.is_super_group = 0 AND  animals.is_group = 1 AND  animals.deleted_at IS NULL AND LENGTH ( animals.number) > 5 \n" +
                "AND animals.kind_id NOT LIKE 4 \n" +
                "AND animals.count > 2 \n" +
                "And animals.is_super_group = 0 \n" +
                "AND enterprises.name = '" + enterpriseName + "'", "number");
        return animalNumbers.get(random.nextInt(animalNumbers.size()));
    }

    // Получение рандомного животного определенного ПО с определенным типом маркирования
    public String getNumberFromSupervisedObject(String markerType, String supervisedObjectName) {
        List<String> numbers = values("SELECT animal_identifications.number FROM animal_identifications\n" +
                "JOIN animals \n" +
                "ON animals.id = animal_identifications.animal_id \n" +
                "JOIN regagro_3_0_handbooks.marker_types\n" +
                "ON regagro_3_0_handbooks.marker_types.id = animal_identifications.marker_type_id \n" +
                "JOIN supervised_objects \n" +
                "ON supervised_objects.id = animals.supervised_object_id \n" +
                "WHERE regagro_3_0_handbooks.marker_types.name = '" + markerType + "'\n" +
                "AND supervised_objects.name = '" + supervisedObjectName + "' " +
                "AND supervised_objects.updated_at IS NOT NULL", "name");
        return numbers.get(random.nextInt(numbers.size()));
    }

    // Получение рандомного номера группы животных ПП с учетом причины выбытия
    public String getAnimalGroupNumberFromEnterprise(String enterpriseName, String disposalReason) {
        List<String> animalNumbers;
        String baseQuery = "SELECT * FROM animals \n" +
                "JOIN enterprises ON enterprises.id = animals.enterprise_id \n" +
                "JOIN supervised_objects ON animals.supervised_object_id = supervised_objects.id \n" +
                "WHERE animals.is_super_group = 0 AND  animals.is_group = 1 AND  animals.deleted_at IS NULL AND LENGTH ( animals.number) > 5 \n" +
                "AND animals.kind_id NOT LIKE 4 \n" +
                "AND animals.count > 2 \n" +
                "And animals.is_super_group = 0 \n" +
                "AND enterprises.name = '" + enterpriseName + "'";
        String objectForKill = "AND supervised_objects.type_id = 22 ";
        if (disposalReason.equals("Фактический убой")) {
            animalNumbers = values(baseQuery + objectForKill, "number");
        } else {
            animalNumbers = values(baseQuery, "number");
        }
        return animalNumbers.get(random.nextInt(animalNumbers.size()));
    }

    // Получение рандомного номера группы животных
    public String getRandomAnimalGroupNumberFromDB() {
        List<String> animalNumbers = values("SELECT * " +
                "FROM animals " +
                "WHERE is_super_group = 0 AND is_group = 1 AND deleted_at IS NULL AND LENGTH (number) > 5", "number");
        return animalNumbers.get(random.nextInt(animalNumbers.size()));
    }

    // Получить id животного/группы по номеру
    public String getAnimalId(String number) {
        return values("SELECT id FROM animals WHERE number = '" + number + "'", "id").get(0);
    }

    // Получение неуникального номера внутри всей системы
    public String getNotUniqueNumber(String markerType) {
        List<String> numbers = values("SELECT animal_identifications.number FROM animal_identifications\n" +
                "JOIN animals \n" +
                "ON animals.id = animal_identifications.animal_id \n" +
                "JOIN regagro_3_0_handbooks.marker_types\n" +
                "ON regagro_3_0_handbooks.marker_types.id = animal_identifications.marker_type_id \n" +
                "WHERE regagro_3_0_handbooks.marker_types.name = '" + markerType + "'\n", "number");
        return numbers.get(random.nextInt(numbers.size()));
    }

    // Получение неуникального номера внутри ПО определенного типа СМ
    public String getNotUniqueNumberForSupervisedObject(String markerType, String supervisedObjectName) {
        List<String> numbers = values("SELECT animal_identifications.number FROM animal_identifications\n" +
                "JOIN animals \n" +
                "ON animals.id = animal_identifications.animal_id \n" +
                "JOIN regagro_3_0_handbooks.marker_types\n" +
                "ON regagro_3_0_handbooks.marker_types.id = animal_identifications.marker_type_id \n" +
                "JOIN supervised_objects \n" +
                "ON supervised_objects.id = animals.supervised_object_id \n" +
                "WHERE regagro_3_0_handbooks.marker_types.name = '" + markerType + "'\n" +
                "AND supervised_objects.name = '" + supervisedObjectName + "'" +
                "AND supervised_objects.updated_at IS NOT NULL AND animals.deleted_at IS NULL", "number");
        return numbers.get(random.nextInt(numbers.size()));
    }


    // Проверка, что выбывшее из группы животное выбыло (deleted_at)
    public boolean isAnimalDisposalFromGroupAsTerminated(String animalNumber, String animalGroupFromId) {
        String query = "SELECT deleted_at FROM animals WHERE number = '" + animalNumber + "' AND from_group_id = '" + animalGroupFromId + "'";
        try {
            assert conn != null;
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery
                    (query);
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса: " + query, e);
        }
    }

    // Получение id элемента таблицы
    public String getId(String name, String tableName) {
       try {
           List<Integer> ids = valuesInt("SELECT id FROM " + tableName + " \n" +
                   "WHERE name = '" + name + "'", "id");
           return ids.get(0).toString();
       }
       catch (NullPointerException e){
           throw new NullPointerException("В БД отсутствует элемент с таким именем: " + name);
       }
    }

    // Получение животного определенной ПП с одним активным СМ
    public List<String> getAnimalNumberWithOneActiveMarker(String enterpriseName) {
        return values("SELECT animals.number\n" +
                "FROM animals \n" +
                "JOIN (\n" +
                "    SELECT animal_id, COUNT(*) AS identification_count, SUM(CASE WHEN is_active = 1 THEN 1 ELSE 0 END) AS active_count\n" +
                "    FROM animal_identifications\n" +
                "    GROUP BY animal_id\n" +
                "    HAVING SUM(CASE WHEN is_active = 1 THEN 1 ELSE 0 END) = 1 \n" +
                ") AS filtered_identifications ON animals.id = filtered_identifications.animal_id\n" +
                "JOIN enterprises ON enterprises.id = animals.enterprise_id \n" +
                "WHERE animals.deleted_at IS NULL\n" +
                "AND filtered_identifications.identification_count = 1 \n" +
                "AND enterprises.name = '" + enterpriseName + "'", "number");
    }

    // Получение животного определенной ПП с одним активным СМ не равным передаваемому
    public List<String> animalNumberWithOneActiveMarkerNotLike(String enterpriseName, String marker_type) {
        return values("SELECT animals.number\n" +
                "FROM animals \n" +
                "JOIN (\n" +
                "    SELECT animal_id, COUNT(*) AS identification_count, SUM(CASE WHEN is_active = 1 THEN 1 ELSE 0 END) AS active_count\n" +
                "    FROM animal_identifications\n" +
                "    JOIN regagro_3_0_handbooks.marker_types ON regagro_3_0_handbooks.marker_types.id = animal_identifications.marker_type_id \n" +
                "    WHERE regagro_3_0_handbooks.marker_types.name NOT LIKE '" + marker_type + "' \n" +
                "    GROUP BY animal_id\n" +
                "    HAVING SUM(CASE WHEN is_active = 1 THEN 1 ELSE 0 END) = 1 \n" +
                ") AS filtered_identifications ON animals.id = filtered_identifications.animal_id\n" +
                "JOIN enterprises ON enterprises.id = animals.enterprise_id \n" +
                "WHERE animals.deleted_at IS NULL\n" +
                "AND filtered_identifications.identification_count = 1 \n" +
                "AND enterprises.name = '" + enterpriseName + "'", "number");
    }

    // Получение животного определенной ПП с одним активным СМ не основным
    public List<String> animalNumberWithOneActiveMarkerNotMain(String enterpriseName) {
        return values("SELECT animals.number\n" +
                "FROM animals \n" +
                "JOIN (\n" +
                "    SELECT animal_id, COUNT(*) AS identification_count, SUM(CASE WHEN is_active = 1 THEN 1 ELSE 0 END) AS active_count,\n" +
                "        SUM(CASE \n" +
                "            WHEN (marker_type_id = 19 AND marker_date < '2024-03-01') OR (marker_type_id IN (5, 6, 7, 9, 19) AND marker_date >= '2024-03-01') THEN 1 \n" +
                "            ELSE 0 \n" +
                "            END) AS matching_identifications \n" +
                "    FROM animal_identifications\n" +
                "    GROUP BY animal_id \n" +
                "    HAVING SUM(CASE WHEN is_active = 1 THEN 1 ELSE 0 END) = 2\n" +
                "    AND matching_identifications > 0\n" +
                ") AS filtered_identifications ON animals.id = filtered_identifications.animal_id\n" +
                "JOIN enterprises ON enterprises.id = animals.enterprise_id \n" +
                "WHERE animals.deleted_at IS NULL\n" +
                "AND enterprises.name = '" + enterpriseName + "'\n" +
                "AND filtered_identifications.identification_count = 2 ", "number");
    }

    // Получение кода региона организации
    public String getRegionCode(String userEmail) {
        List<String> codes = values("SELECT region_code FROM organization_addresses\n" +
                "JOIN users ON users.organization_id = organization_addresses.organization_id \n" +
                "WHERE users.email = '" + userEmail + "'", "region_code");
        return codes.get(0);
    }

    // Получение всех площадок юзера
    public String getEnterprisesOfUser(String userEmail){
        List<String> enterprisesName = values("SELECT enterprises.name \n" +
                "FROM enterprises\n" +
                "JOIN users ON users.id = enterprises.user_id \n" +
                "WHERE users.email = '" + userEmail +"'\n" +
                "AND enterprises.deleted_at IS NULL", "enterprises.name");
        return enterprisesName.get(random.nextInt(enterprisesName.size()));
    }
    // Получение всех объектов юзера
    public String getSupervisedObjectsOfUser(String userEmail){
        List<String> supervisedObjectsName = values("SELECT supervised_objects.name \n" +
                "FROM supervised_objects \n" +
                "JOIN enterprises ON supervised_objects.enterprise_id = enterprises.id \n" +
                "JOIN users ON users.id = enterprises.user_id \n" +
                "WHERE users.email = 'kam@kam.kam'\n" +
                "AND supervised_objects.deleted_at IS NULL", "supervised_objects.name");
        return supervisedObjectsName.get(random.nextInt(supervisedObjectsName.size()));
    }
}
