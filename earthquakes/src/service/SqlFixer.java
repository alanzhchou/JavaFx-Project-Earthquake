package service;

import java.util.HashMap;

/**
 * @Author: Alan
 * @since : Java_8_151
 * @version: 1.0
 */
public interface SqlFixer {
    /**
     * a interface for fixing some simple sql command
     * @param originSql the original sql like "SELECT * FROM quakes"
     * @param filterTest    the infomation for DB system filter, like dateFrom, dateTo, mag...
     * @return  the completed sql command to get what we want from DB system, like ""SELECT * FROM quakes"where id=1"
     */
    String fix(String originSql, HashMap<String, Object> filterTest);
}
