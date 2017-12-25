package service;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * @Author: Alan
 * @since : Java_8_151
 * @version: 1.0
 */

public class TimestempDoubleFilterService implements DoubleValuesFilter {
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
     * (for file system) A filter to judge whether the date is in the acceptable interval
     * @param earthquake the dateTime of the earthquake you want to judge
     * @param dateFrom  the min value in acceptable interval
     * @param dateTo    the max value in the acceptable interval
     * @return  if in the interval return True else False
     */
    public Boolean fileSystemDoubleFilter(Timestamp earthquake, Timestamp dateFrom, Timestamp dateTo) {
        return earthquake.after(dateFrom)&& earthquake.before(dateTo);
    }


    /**
     * no use for DB system
     * @param dateFrom the min value in acceptable interval
     * @param dateTo the max value in the acceptable interval
     * @return Arraylist of data what we want
     */
    public ArrayList<String> databaseDoubleFilter(Timestamp dateFrom, Timestamp dateTo) {
        ArrayList<String> filter = new ArrayList<String>();
        filter.add(dateFrom.toString());
        filter.add(dateTo.toString());
        return filter;
    }
}
