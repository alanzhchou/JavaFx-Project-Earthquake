package controller;

import java.sql.Timestamp;
import java.util.ArrayList;

import bean.Earthquake;
import dao.Read_Cvs_All;
import service.FloatDoubleFilterService;
import service.TimestempDoubleFilterService;

/**
 * Author ZH-AlanChou
 * Date: 2017/12/7.
 * Version 1.0
 */
public class StaticWholeFilterControl {
    private ArrayList<Earthquake> earthquakes = new Read_Cvs_All().getEarthquakeList();

    private FloatDoubleFilterService floatDoubleFilterService = new FloatDoubleFilterService();
    private TimestempDoubleFilterService timestempDoubleFilterService = new TimestempDoubleFilterService();
    //    private StringSingleFilterService stringSingleFilterService = new StringSingleFilterService();

    public ArrayList<Earthquake> wholeFilter(Timestamp dateFrom, Timestamp dateTo, float latitudeMin, float latitudeMax,
                                             float longitudeMin, float longitudeMax, float depthMin, float depthMax, float magnitudeMin, float magnitudeMax){
        ArrayList<Earthquake> filteredQuakes = new ArrayList<Earthquake>();

        earthquakes.stream().filter((q) -> (timestempDoubleFilterService.fileSystemDoubleFilter(q.getUTC_date(),dateFrom,dateTo)))
                .filter((q) -> (floatDoubleFilterService.fileSystemDoubleFilter(q.getLatitude(),latitudeMin,latitudeMax)))
                .filter((q) -> (floatDoubleFilterService.fileSystemDoubleFilter(q.getLongitude(),longitudeMin,longitudeMax)))
                .filter((q) -> (floatDoubleFilterService.fileSystemDoubleFilter(q.getDepth(),depthMin,depthMax)))
                .filter((q) -> (floatDoubleFilterService.fileSystemDoubleFilter(q.getMagnitude(),magnitudeMin,magnitudeMax)))
                .forEach((q) -> {
                    filteredQuakes.add(q);
                });
        return filteredQuakes;
    }
}
