/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.server.constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 *
 * @author nkala
 */
public class DatabaseConfigManager {
     public static void saveConfig(String url, String username, String password) throws Exception {
        Properties properties = new Properties();
        properties.setProperty("db.url", url);
        properties.setProperty("db.username", username);
        properties.setProperty("db.password", password);
        
        // Kreiraj config folder ako ne postoji
        File configDir = new File("config");
        if (!configDir.exists()) {
            configDir.mkdirs();
        }
        
        try (FileOutputStream output = new FileOutputStream(MyServerConstants.CONFIG_FILE_PATH)) {
            properties.store(output, "Database Configuration");
        }
    }
    
    public static Properties loadConfig() throws Exception {
        Properties properties = new Properties();
        
        File configFile = new File(MyServerConstants.CONFIG_FILE_PATH);
        if (!configFile.exists()) {
            // Ako fajl ne postoji, kreiraj sa default vrednostima
            saveConfig(MyServerConstants.DEFAULT_URL, 
                      MyServerConstants.DEFAULT_USERNAME, 
                      MyServerConstants.DEFAULT_PASSWORD);
        }
        
        try (FileInputStream input = new FileInputStream(MyServerConstants.CONFIG_FILE_PATH)) {
            properties.load(input);
        }
        
        return properties;
    }
    
    public static String getUrl() throws Exception {
        Properties props = loadConfig();
        return props.getProperty("db.url", MyServerConstants.DEFAULT_URL);
    }
    
    public static String getUsername() throws Exception {
        Properties props = loadConfig();
        return props.getProperty("db.username", MyServerConstants.DEFAULT_USERNAME);
    }
    
    public static String getPassword() throws Exception {
        Properties props = loadConfig();
        return props.getProperty("db.password", MyServerConstants.DEFAULT_PASSWORD);
    }
}
