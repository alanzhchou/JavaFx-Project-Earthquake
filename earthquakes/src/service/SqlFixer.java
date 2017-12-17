package service;

import java.util.HashMap;

/**
 * __Author__: Alan
 * __date__: 2017/12/17
 * __version__: 1.0
 */
public interface SqlFixer {
    String fix(String originSql, HashMap<String, Object> filterTest);
}
