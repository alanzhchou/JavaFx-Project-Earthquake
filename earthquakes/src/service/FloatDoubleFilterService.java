package service;

import java.util.ArrayList;

/**
 * @Author: Alan
 * @since : Java_8_151
 * @version: 1.0
 */
public class FloatDoubleFilterService implements DoubleValuesFilter {
    /**
     * no use, please overload this function
     * @return
     */
    @Override
    public Boolean fileSystemDoubleFilter() {
        return null;
    }

    /**
     * no use, please overload this function
     * @return
     */
    @Override
    public ArrayList<String> databaseDoubleFilter() {
        return null;
    }

    /**
     * (for file system , a filter to judge whether the float value is in required interval)
     * @param earthquake the float input
     * @param Min the min value in the acceptable interval
     * @param Max the max value in the acceptable interval
     * @return YES for True else for False
     */
    public Boolean fileSystemDoubleFilter(float earthquake, float Min, float Max) {
        return earthquake >= Min && earthquake <= Max;
    }

    /**
     * no use for DB system
     * @param Min the min value in acceptable interval
     * @param Max the max value in the acceptable interval
     * @return Arraylist of data what we want
     */
    public ArrayList<String> databaseDoubleFilter(float Min, float Max) {
        ArrayList<String> filter = new ArrayList<String>();
        filter.add(String.valueOf(Min));
        filter.add(String.valueOf(Max));
        return filter;
    }
}
