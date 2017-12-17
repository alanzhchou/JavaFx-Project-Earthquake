package dao;

import bean.Earthquake;
import controller.FilterLikeController;
import service.FilterSqlFixer;

import java.util.ArrayList;
import java.sql.*;

/**
 * Author ZH-AlanChou
 * Date: 2017/12/7.
 * Version 1.0
 */
public class Reader_Database_filter implements Reader {
    private String databaseType = "org.sqlite.JDBC";
    private String databaseSource = "jdbc:sqlite:earthquakes.sqlite";
    private Connection connection = null;
    private String sql = "select * FROM quakes";

    private ArrayList<Earthquake> earthquakeList = new ArrayList<Earthquake>();
    private FilterSqlFixer fixer = new FilterSqlFixer();

    @Override
    public ArrayList<Earthquake> getEarthquakeList() {
        return null;
    }

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
