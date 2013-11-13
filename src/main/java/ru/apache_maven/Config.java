package ru.apache_maven;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final String PROPERTIES_FILE = "./app.properties";

    public static int PORT;
    public static int HISTORY_SIZE;

    static {
        Properties properties = new Properties();
        FileInputStream propertiesFile = null;

        try {
            propertiesFile = new FileInputStream(PROPERTIES_FILE);
            properties.load(propertiesFile);

            PORT = Integer.parseInt(properties.getProperty("PORT"));
            HISTORY_SIZE = Integer.parseInt(properties.getProperty("HISTORY_LENGTH"));
        } catch (FileNotFoundException ex) {
            System.err.println("Properties config file not found");
        } catch (IOException ex) {
            System.err.println("Error while reading file");
        } finally {
            try {
                assert propertiesFile != null;
                propertiesFile.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

