package utilities;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String getProperty(String key) {
        String envValue = System.getProperty(key, System.getenv(key.replace(".", "_").toUpperCase()));
        return (envValue != null && !envValue.isEmpty()) ? envValue : properties.getProperty(key);
    }
}