package controller;

import bean.Earthquake;

import java.util.HashMap;

/**
 * @Author: Alan
 * @since : Java_8_151
 * @version: 1.0
 */
public interface FilterLikeController {
    /**
     * for file system filter to judge
     * @param earthquake a earthquake instance to judge
     * @return whether the given instance eatthquake is required or not
     */
    boolean test(Earthquake earthquake);

    /**
     * for DB system filter to judge
     * @return the filter info HashMap
     */
    HashMap<String,Object> test();
}
