package com.alanchou.dao;

import com.alanchou.bean.Earthquake;

import java.util.ArrayList;

/**
 * Author ZH-AlanChou
 * Date: 2017/12/7.
 * Version 1.0
 */
public interface Reader {
    ArrayList<Earthquake> getEarthquakeList();
}
