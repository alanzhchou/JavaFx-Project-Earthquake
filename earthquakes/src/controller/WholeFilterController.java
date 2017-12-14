package controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bean.Earthquake;
import dao.Reader_Csv_Filter;
import dao.Reader_Database_filter;
import service.*;
/**
 * Author ZH-AlanChou
 * Date: 2017/12/7.
 * Version 1.0
 */
public class WholeFilterController implements FilterLikeController{
    private Timestamp dateFrom;
    private Timestamp dateTo;
    private float latitudeMin;
    private float latitudeMax;
    private float longitudeMin;
    private float longitudeMax;
    private float depthMin;
    private float depthMax;
    private float magnitudeMin;
    private float magnitudeMax;

    private FloatDoubleFilterService floatDoubleFilterService = new FloatDoubleFilterService();
    private TimestempDoubleFilterService timestempDoubleFilterService = new TimestempDoubleFilterService();
    //    private StringSingleFilterService stringSingleFilterService = new StringSingleFilterService();

    public WholeFilterController(){}

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

    public ArrayList<Earthquake> getEarthquakeListFromFile(){
        return new Reader_Csv_Filter().getEarthquakeList(this);
    }


    public ArrayList<Earthquake> getEarthquakeListFromDB(){
        return new Reader_Database_filter().getEarthquakeList(this);
    }

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

    public boolean test(Earthquake earthquake){
        return timestempDoubleFilterService.fileSystemDoubleFilter(earthquake.getUTC_date(),dateFrom,dateTo)&&
                floatDoubleFilterService.fileSystemDoubleFilter(earthquake.getLatitude(),latitudeMin,latitudeMax)&&
                floatDoubleFilterService.fileSystemDoubleFilter(earthquake.getLongitude(),longitudeMin,longitudeMax)&&
                floatDoubleFilterService.fileSystemDoubleFilter(earthquake.getDepth(),depthMin,depthMax)&&
                floatDoubleFilterService.fileSystemDoubleFilter(earthquake.getMagnitude(),magnitudeMin,magnitudeMax);
    }
}
