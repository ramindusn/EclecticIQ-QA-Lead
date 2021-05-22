package utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DefaultProperties {

    private static final String propertyFilePath = "./default.properties";
    private static Properties properties;

    public DefaultProperties() {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new RuntimeException("Error in reading property file!");
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            throw new RuntimeException("default.properties not found at " + propertyFilePath);
        }
    }

    /**
     * This method will read the value for a given property name
     *
     * @param propertyName
     * @return String
     */
    public static String readProperty(String propertyName) {
        String propertyValue = properties.getProperty(propertyName);
        if (propertyValue != null) {
            return propertyValue;
        } else throw new RuntimeException("Given property name is not specified in the default.properties file");
    }

    /**
     * This method will return the current properties
     *
     * @return Properties
     */
    public Properties getProperties() {
        return properties;
    }
}
