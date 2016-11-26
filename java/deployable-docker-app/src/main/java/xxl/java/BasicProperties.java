package xxl.java;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BasicProperties {

    private static Properties properties = readBasicProperties();

    public static String forKey(String key) {
        return properties.getProperty(key);
    }

    public static String databaseUrl() {
        return forKey("db.jdbc.url");
    }

    public static String databaseUser() {
        return forKey("db.jdbc.user");
    }

    public static String databasePassword() {
        return forKey("db.jdbc.pass");
    }

    public static String databaseDialect() {
        return forKey("db.jdbc.dialect");
    }

    private static Properties readBasicProperties() {
        try {
            Properties properties = new Properties();
            InputStream inputStream = BasicProperties.class.getResourceAsStream("/basic.properties");
            properties.load(inputStream);
            return properties;
        }
        catch (IOException e) {
            throw new RuntimeException("Property-file missing", e);
        }
    }
}
