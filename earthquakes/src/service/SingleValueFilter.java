package service;

import bean.Earthquake;

import java.util.ArrayList;

/**
 * Author ZH-AlanChou
 * Date: 2017/12/7.
 * Version 1.0
 */
public interface SingleValueFilter {
    Boolean fileSystemSingleFilter();
    String databaseSingleFilter();
}
