package services;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties;

    static { // Статический блок инициализации. Загрузка свойств один раз при запуске
        properties = loadProperties();
    }

    private static Properties loadProperties() {
        Properties props = new Properties();
        try (FileReader fileReader = new FileReader("src/test/resources/config.properties")) {
            PropertiesConfiguration config = new PropertiesConfiguration();
            config.read(fileReader);

            // Переносим свойства из config в props
            config.getKeys().forEachRemaining(key ->
                    props.setProperty(key, config.getProperty(key).toString())
            );

        } catch (ConfigurationException | IOException e) {
            e.printStackTrace();
            System.err.println("Ошибка при загрузке файла конфигурации конфигурации");
        }
        return props;
    }

    public static String getProperty(String key) {
        String prop = properties.getProperty(key);
        return prop;
    }

    public static String getUserEmail(String role) {
        return getProperty("user." + role);
    }
    public static String getUserServiceArea(String role) {
        return getProperty("user." + role + ".service_area");
    }
    public static String getUserPassword(String role) {
        return getProperty("user." + role + ".password");
    }
}