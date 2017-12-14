package service;

import java.util.ArrayList;

/**
 * Author ZH-AlanChou
 * Date: 2017/12/7.
 * Version 1.0
 */
public interface DoubleValuesFilter {
    Boolean fileSystemDoubleFilter ();
    ArrayList<String> databaseDoubleFilter();
}
