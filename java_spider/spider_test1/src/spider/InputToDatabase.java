package spider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Author ZH-AlanChou
 * Date: 2017/12/7.
 * Version 1.0
 */
public class InputToDatabase {
    private String databaseType = "org.sqlite.JDBC";
    private String databaseSource = DataSourceProp.getSpDBLocation();
    private Connection connection = null;
    private String sql = "INSERT into quakes(UTC_date,latitude,longitude,depth,magnitude,region) values ";

    public InputToDatabase(){
        try {
            Class.forName(databaseType);
            connection = DriverManager.getConnection(databaseSource);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void insertValue(String value){
        String fixedSql = sql + "(" + value + ");";
        try {
            Statement stm = connection.createStatement();
            stm.execute(fixedSql);
        }catch (Exception e){
            System.out.println(fixedSql);
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
