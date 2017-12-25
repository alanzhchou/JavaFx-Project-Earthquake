package dao;

import java.util.ArrayList;
import bean.Earthquake;

/**
 * @Author: Alan
 * @since : Java_8_151
 * @version: 1.0
 */
public interface Reader {
    /**
     * @return ArrayList<Earthquake>, required earthquake list
     */
    ArrayList<Earthquake> getEarthquakeList();
}
