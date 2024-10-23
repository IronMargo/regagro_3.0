package services.database;

import enums.DbNames;
import services.ConfigReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static enums.DbNames.HANDBOOKS_DB_NAME;

public abstract class DBService {
    protected Connection conn;
    Random random = new Random();

    public static HandbooksDBService getHandbooksDBService() {
        return new HandbooksDBService();
    }

    public static RegagroDBService getRegagroDBService() {
        return new RegagroDBService();
    }


    // Закрытие соединения
    public RuntimeException closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            return new RuntimeException("Нет активного подключения");
        }
        return null;
    }

    // Объединяем методы получения соединения
    protected Connection getConnection(DbNames dbName) {
        try {
            String url;
            if (dbName.equals(HANDBOOKS_DB_NAME)) {
                url = ConfigReader.getProperty("url.db_handbooks");
            } else {
                url = ConfigReader.getProperty("url.db");
            }
            return DriverManager.getConnection(url, ConfigReader.getProperty("user.db"), ConfigReader.getProperty("pass.db"));
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при подключении к базе данных", e);
        }
    }

    // Получить список строковых значений
    public List<String> values(String query, String columnName) {
        List<String> values = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery
                    (query);
            while (resultSet.next()) {
                values.add(resultSet.getString(columnName));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса", e);
        }
        return values;
    }

    // Получить список значений типа int
    public List<Integer> valuesInt(String query, String columnName) {
        List<Integer> values = new ArrayList<>();
        try {
            assert conn != null;
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery
                    (query);
            while (resultSet.next()) {
                values.add(resultSet.getInt(columnName));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса", e);
        }
        return values;
    }
}