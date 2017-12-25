package dao;

import bean.Earthquake;
import controller.FilterLikeController;
import service.FilterSqlFixer;

import java.util.ArrayList;
import java.sql.*;

/**
 * @Author: Alan
 * @since : Java_8_151
 * @version: 1.0
 */

public class Reader_Database_filter implements Reader {
    /**
     * the databaseDriverType like "org.sqlite.JDBC"
     */
    private String databaseType = "org.sqlite.JDBC";


    /**
     * the Sqilte DB file location
     */
    private String databaseSource;

    /**
     *  the DB connection
     */
    private Connection connection = null;

    /**
     *  some simple original sql command
     */
    private String sql = "select * FROM quakes";

    /**
     * the information list of earthquake for upper layer
     */
    private ArrayList<Earthquake> earthquakeList = new ArrayList<Earthquake>();

    /**
     * util for fix some simple original sql command to completed sql
     */
    private FilterSqlFixer fixer = new FilterSqlFixer();

    public Reader_Database_filter(){
        this.databaseSource = DataSourceProp.getDBLocation();
    }

    /**
     * no use, please overload this function
     * @return
     */
    @Override
    public ArrayList<Earthquake> getEarthquakeList() {
        return null;
    }

    /**
     * get the required earthquake list from database
     * @param filter a filter for DB system to get enough info to get correct list
     * @return ArrayList<Earthquake>, required earthquake list
     */
    public ArrayList<Earthquake> getEarthquakeList(FilterLikeController filter){
        String fixedSql = fixer.fix(sql,filter.test());
        try{
            Class.forName(databaseType);

            connection = DriverManager.getConnection(databaseSource);
            Statement stm = connection.createStatement();
            stm.setQueryTimeout(300);

            ResultSet rs = stm.executeQuery(fixedSql);

            earthquakeList.clear();
            while (rs.next()){
                Earthquake earthquake = new Earthquake();
                try {
                    earthquake.setId(rs.getInt("id"));
                    earthquake.setUTC_date(Timestamp.valueOf(rs.getString("UTC_date")));
                    earthquake.setLatitude(rs.getFloat("latitude") );
                    earthquake.setLongitude(rs.getFloat("longitude"));
                    earthquake.setDepth(rs.getFloat("depth"));
                    earthquake.setMagnitude(rs.getFloat("magnitude"));
                    earthquake.setRegion(rs.getString("region"));
                    earthquakeList.add(earthquake);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return earthquakeList;
    }
}
