package service;

import java.util.ArrayList;

/**
 * @Author: Alan
 * @since : Java_8_151
 * @version: 1.0
 */

public interface DoubleValuesFilter {
    /**
     * (for file system , a filter to judge whether the float value is in required interval)
     * @return YES for True else for False
     */
    Boolean fileSystemDoubleFilter ();

    /**
     *(for DB system , a filter to get the data List what we want)
     * @return Arraylist of data what we want
     */
    ArrayList<String> databaseDoubleFilter();
}
