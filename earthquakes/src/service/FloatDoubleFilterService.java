package service;

import java.util.ArrayList;

/**
 * Author ZH-AlanChou
 * Date: 2017/12/7.
 * Version 1.0
 */
public class FloatDoubleFilterService implements DoubleValuesFilter {
    @Override
    public Boolean fileSystemDoubleFilter() {
        return null;
    }

    @Override
    public ArrayList<String> databaseDoubleFilter() {
        return null;
    }

    public Boolean fileSystemDoubleFilter(float earthquake, float Min, float Max) {
        return earthquake > Min && earthquake < Max;
    }

    public ArrayList<String> databaseDoubleFilter(float Min, float Max) {
        ArrayList<String> filter = new ArrayList<String>();
        filter.add(String.valueOf(Min));
        filter.add(String.valueOf(Max));
        return filter;
    }
}
