package dao;

import java.util.ArrayList;

import bean.Earthquake;
/**
 * Author ZH-AlanChou
 * Date: 2017/12/7.
 * Version 1.0
 */
public interface Reader {
    ArrayList<Earthquake> getEarthquakeList();
}
