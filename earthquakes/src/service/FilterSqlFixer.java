package service;

import java.util.HashMap;

/**
 * @Author: Alan
 * @since : Java_8_151
 * @version: 1.0
 */
public class FilterSqlFixer implements SqlFixer {
    /**
     * fix some simple filter like sql command
     * @param originSql  the original sql like "SELECT * FROM quakes"
     * @param filterTest the infomation for DB system filter, like dateFrom, dateTo, mag...
     * @return String, the completed sql command to get what we want from DB system, like ""SELECT * FROM quakes"where id=1"
     */
    @Override
    public String fix(String originSql,HashMap<String,Object> filterTest){
        StringBuffer conditions = new StringBuffer(" where ");
        for (String key:filterTest.keySet()){
            if (key.contains("Min")){
                conditions.append(key.replaceAll("Min","") + ">=" + filterTest.get(key) + " and ");
            }else if (key.contains("From")){
                conditions.append("UTC_date" + ">=\"" + filterTest.get(key) + "\" and ");
            }else if (key.contains("Max")){
                conditions.append(key.replaceAll("Max","") + "<=" + filterTest.get(key) + " and ");
            }else if (key.contains("To")){
                conditions.append("UTC_date" + "<=\"" + filterTest.get(key) + "\" and ");
            }
        }
        return originSql + conditions.delete(conditions.length()-4,conditions.length()-1) + ";";
    }
}
