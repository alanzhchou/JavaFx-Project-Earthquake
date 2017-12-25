package dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @Author: Alan
 * @since : Java_8_151
 * @version: 1.0
 */
public class DataSourceProp {
    /**
     * static function to get the Csv file data Source location
     * @return Csv file data Source location
     */
    public static String getCsvLocation(){
        Properties defprop = new  Properties();

        Properties prop = new Properties(defprop);
        try {
            BufferedReader conf = new BufferedReader(new FileReader("dataSourceLocations.cnf"));
            prop.load(conf);
        }catch (IOException e){
            e.printStackTrace();
        }
        return prop.getProperty("locationFolder") + prop.getProperty("csv_dir");
    }

    /**
     * static function to get the DB Source location
     * @return DB Source location
     */
    public static String getDBLocation(){
        Properties defprop = new  Properties();

        Properties prop = new Properties(defprop);
        try {
            BufferedReader conf = new BufferedReader(new FileReader("dataSourceLocations.cnf"));
            prop.load(conf);
        }catch (IOException e){
            e.printStackTrace();
        }
        return "jdbc:sqlite:" + prop.getProperty("locationFolder") + prop.getProperty("DB_dir");
    }

    /**
     * static function to get the DB Source location
     * @return DB Source location
     */
    public static String getSpDBLocation(){
        Properties defprop = new  Properties();

        Properties prop = new Properties(defprop);
        try {
            BufferedReader conf = new BufferedReader(new FileReader("dataSourceLocations.cnf"));
            prop.load(conf);
        }catch (IOException e){
            e.printStackTrace();
        }
        return "jdbc:sqlite:" + prop.getProperty("locationFolder") + prop.getProperty("spDB_dir");
    }
}
