package service;

import java.util.HashMap;

/**
 * __Author__: Alan
 * __date__: 2017/12/17
 * __version__: 1.0
 */

public class FilterSqlFixer implements SqlFixer {
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
