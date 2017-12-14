package controller;

import bean.Earthquake;

import java.util.HashMap;

/**
 * Author ZH-AlanChou
 * Date: 2017/12/7.
 * Version 1.0
 */
public interface FilterLikeController {
    boolean test(Earthquake earthquake);
    HashMap<String,Object> test();
}
