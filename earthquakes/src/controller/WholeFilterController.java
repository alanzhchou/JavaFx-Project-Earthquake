package controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bean.Earthquake;
import dao.Reader_Csv_Filter;
import dao.Reader_Database_filter;
import dao.Reader_SpDatabase_filter;
import service.*;
/**
 * @Author: Alan
 * @since : Java_8_151
 * @version: 1.0
 */
public class WholeFilterController implements FilterLikeController{
    /**
     * from date in the filter interval
     */
    private Timestamp dateFrom;

    /**
     * end date in the filter interval
     */
    private Timestamp dateTo;

    /**
     * from lat in the filter interval
     */
    private float latitudeMin;

    /**
     * end lat in the filter interval
     */
    private float latitudeMax;

    /**
     * from long in the filter interval
     */
    private float longitudeMin;

    /**
     * end long in the filter interval
     */
    private float longitudeMax;

    /**
     * from depth in the filter interval
     */
    private float depthMin;

    /**
     * end depth in the filter interval
     */
    private float depthMax;

    /**
     * from mag in the filter interval
     */
    private float magnitudeMin;

    /**
     * end mag in the filter interval
     */
    private float magnitudeMax;

    /**
     * filter util to check a float whether in the interval or not
     */
    private FloatDoubleFilterService floatDoubleFilterService = new FloatDoubleFilterService();

    /**
     * filter util to check a TimeStemp whether in the interval or not
     */
    private TimestempDoubleFilterService timestempDoubleFilterService = new TimestempDoubleFilterService();

    public WholeFilterController(){}

    /**
     * set filter values ready
     * @param dateFrom
     * @param dateTo
     * @param latitudeMin
     * @param latitudeMax
     * @param longitudeMin
     * @param longitudeMax
     * @param depthMin
     * @param depthMax
     * @param magnitudeMin
     * @param magnitudeMax
     */
    public void setValues(Timestamp dateFrom, Timestamp dateTo, float latitudeMin, float latitudeMax,
                     float longitudeMin, float longitudeMax, float depthMin, float depthMax, float magnitudeMin,float magnitudeMax){
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.latitudeMin = latitudeMin;
        this.latitudeMax = latitudeMax;
        this.longitudeMin = longitudeMin;
        this.longitudeMax = longitudeMax;
        this.depthMin = depthMin;
        this.depthMax = depthMax;
        this.magnitudeMin = magnitudeMin;
        this.magnitudeMax = magnitudeMax;
    }

    /**
     * for file system ,get a required earthquake list
     * @return required earthquake list
     */
    public ArrayList<Earthquake> getEarthquakeListFromFile(){
        return new Reader_Csv_Filter().getEarthquakeList(this);
    }

    /**
     * for DB system ,get a required earthquake list
     * @return required earthquake list
     */
    public ArrayList<Earthquake> getEarthquakeListFromDB(){
        return new Reader_Database_filter().getEarthquakeList(this);
    }

    /**
     * for spDB system ,get a required earthquake list
     * @return required earthquake list
     */
    public ArrayList<Earthquake> getEarthquakeListFromSpDB(){
        return new Reader_SpDatabase_filter().getEarthquakeList(this);
    }

    /**
     * filter test for file system
     * @param earthquake some instance read from Csv file
     * @return required for True or False
     */
    public boolean test(Earthquake earthquake){
        return timestempDoubleFilterService.fileSystemDoubleFilter(earthquake.getUTC_date(),dateFrom,dateTo)&&
                floatDoubleFilterService.fileSystemDoubleFilter(earthquake.getLatitude(),latitudeMin,latitudeMax)&&
                floatDoubleFilterService.fileSystemDoubleFilter(earthquake.getLongitude(),longitudeMin,longitudeMax)&&
                floatDoubleFilterService.fileSystemDoubleFilter(earthquake.getDepth(),depthMin,depthMax)&&
                floatDoubleFilterService.fileSystemDoubleFilter(earthquake.getMagnitude(),magnitudeMin,magnitudeMax);
    }

    /**
     * filter test for DB system
     * @return HashMap of database search info
     */
    public HashMap<String,Object> test(){
        Map<String,Object> conditions = new HashMap<String, Object>();
        conditions.put("dateFrom",dateFrom);
        conditions.put("dateTo",dateTo);
        conditions.put("latitudeMax",latitudeMax);
        conditions.put("latitudeMin",latitudeMin);
        conditions.put("longitudeMax",longitudeMax);
        conditions.put("longitudeMin",longitudeMin);
        conditions.put("depthMax",depthMax);
        conditions.put("depthMin",depthMin);
        conditions.put("magnitudeMax",magnitudeMax);
        conditions.put("magnitudeMin",magnitudeMin);
        return (HashMap<String, Object>) conditions;
    }
}
