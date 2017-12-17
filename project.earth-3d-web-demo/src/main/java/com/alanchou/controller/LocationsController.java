package com.alanchou.controller;

import com.alanchou.bean.Earthquake;
import org.json.JSONException;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

/**
 * __Author__: Alan
 * __date__: 2017/12/17
 * __version__: 1.0
 */

@RestController
public class LocationsController implements MappingController{
    private WholeFilterController wholeFilterController = new WholeFilterController();
    private ArrayList<Earthquake> earthquakes = null;

    @PostMapping(value = "/getLocations")
    public String getLocations(@RequestParam Map<String,Object> param) throws JSONException {
        System.out.println(param);
        Timestamp dateFrom = Timestamp.valueOf(param.get("dateFrom").toString().replaceAll("/","-") + ":00.000");
        Timestamp dateTo = Timestamp.valueOf(param.get("dateTo").toString().replaceAll("/","-") + ":00.000");

        String[] lons = param.get("lon").toString().split(",");
        float longitudeMin = Float.parseFloat(lons[0]);
        float longitudeMax = Float.parseFloat(lons[1]);

        String[] lats = param.get("lat").toString().split(",");
        float latitudeMin = Float.parseFloat(lats[0]);
        float latitudeMax = Float.parseFloat(lats[1]);

        String[] depths = param.get("depth").toString().split(",");
        int depthMin = Integer.parseInt(depths[0]);
        int depthMax = Integer.parseInt(depths[1]);

        String[] mags = param.get("mag").toString().split(",");
        float magnitudeMin = Float.parseFloat(mags[0]);
        float magnitudeMax = Float.parseFloat(mags[1]);

        wholeFilterController.setValues(dateFrom,dateTo,latitudeMin,latitudeMax,longitudeMin,longitudeMax,depthMin,depthMax,magnitudeMin,magnitudeMax);
        earthquakes = wholeFilterController.getEarthquakeListFromDB();

        ArrayList<String> quakes = new ArrayList<String>();

        for (int i=0; i<earthquakes.size(); i++){
            quakes.add(earthquakes.get(i).toJson().toString());
        }
        return quakes.toString();
    }
}
