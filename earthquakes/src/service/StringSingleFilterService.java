package service;
/**
 * Author ZH-AlanChou
 * Date: 2017/12/7.
 * Version 1.0
 */
public class StringSingleFilterService implements SingleValueFilter {
    @Override
    public Boolean fileSystemSingleFilter() {
        return null;
    }

    @Override
    public String databaseSingleFilter() {
        return null;
    }

    public Boolean fileSystemSingleFilter(String earthquakeRigion, String region) {
        return earthquakeRigion.equals(region);
    }

    public String databaseSingleFilter(String region) {
        return region;
    }
}
