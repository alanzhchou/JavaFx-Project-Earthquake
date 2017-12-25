package service;

/**
 * @Author: Alan
 * @since : Java_8_151
 * @version: 1.0
 */
public interface SingleValueFilter {
    /**
     * (for file system , a filter to judge whether the String is what we want or not)
     * @return  equals for True else for False
     */
    Boolean fileSystemSingleFilter();

    /**
     * no use for DB system
     * @return equals for True else for False
     */
    String databaseSingleFilter();
}
