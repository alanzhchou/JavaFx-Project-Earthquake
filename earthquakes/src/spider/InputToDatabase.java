package spider;

import dao.DataSourceProp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * @Author: H-AlanChou
 * @since : Java_8_151
 * @version: 1.0
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

    /**
     * insert DB a single earthquake info
     * @param value the whole info for a earthquake inserting into DB
     */
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

    /**
     * close the DB connection
     */
    public void closeConnection(){
        try {
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
