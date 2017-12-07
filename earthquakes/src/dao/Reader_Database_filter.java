package dao;

import bean.Earthquake;
import controller.FilterLikeController;

import java.util.ArrayList;

/**
 * Author ZH-AlanChou
 * Date: 2017/12/7.
 * Version 1.0
 */
public class Reader_Database_filter implements Reader {
    @Override
    public ArrayList<Earthquake> getEarthquakeList() {
        return null;
    }

    public ArrayList<Earthquake> getEarthquakeList(FilterLikeController filter) {
        return null;
    }
}
