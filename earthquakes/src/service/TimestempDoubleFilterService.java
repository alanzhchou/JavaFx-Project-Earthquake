package service;

import java.sql.Timestamp;
import java.util.ArrayList;
/**
 * Author ZH-AlanChou
 * Date: 2017/12/7.
 * Version 1.0
 */
public class TimestempDoubleFilterService implements DoubleValuesFilter {
    @Override
    public Boolean fileSystemDoubleFilter() {
        return null;
    }

    @Override
    public ArrayList<String> databaseDoubleFilter() {
        return null;
    }

    public Boolean fileSystemDoubleFilter(Timestamp earthquake, Timestamp dateFrom, Timestamp dateTo) {
        return earthquake.after(dateFrom)&& earthquake.before(dateTo);
    }

    public ArrayList<String> databaseDoubleFilter(Timestamp dateFrom, Timestamp dateTo) {
        ArrayList<String> filter = new ArrayList<String>();
        filter.add(dateFrom.toString());
        filter.add(dateTo.toString());
        return filter;
    }
}
