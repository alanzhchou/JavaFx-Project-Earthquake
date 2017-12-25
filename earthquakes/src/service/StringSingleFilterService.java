package service;
/**
 * @Author: Alan
 * @since : Java_8_151
 * @version: 1.0
 */
public class StringSingleFilterService implements SingleValueFilter {
    /**
     * no use, just for override
     * @return
     */
    @Override
    public Boolean fileSystemSingleFilter() {
        return null;
    }

    /**
     * no use, please overload this function
     * @return
     */
    @Override
    public String databaseSingleFilter() {
        return null;
    }

    /**
     * (for file system , a filter to judge whether the String is what we want or not)
     * @param earthquakeRigion the String input
     * @param region    the String acceptabels
     * @return  equals for True else for False
     */
    public Boolean fileSystemSingleFilter(String earthquakeRigion, String region) {
        return earthquakeRigion.equals(region);
    }

    /**
     * no use for DB system
     * @param region the String acceptabels
     * @return equals for True else for False
     */
    public String databaseSingleFilter(String region) {
        return region;
    }
}
