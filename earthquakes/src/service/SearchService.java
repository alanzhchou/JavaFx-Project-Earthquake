package service;

import java.sql.Timestamp;
import java.util.ArrayList;

import bean.Earthquake;
import bean.Filter;
import dao.EarthquakeData_csv;

public class SearchService {
	public ArrayList<Earthquake> search(Timestamp dateFrom, Timestamp dateTo, float latitudeMin, float latitudeMax,
			float longitudeMin, float longitudeMax, float depthMin, float depthMax, float magnitudeMin,float magnitudeMax) {
		EarthquakeData_csv data = new EarthquakeData_csv();
		Filter filter = new Filter(dateFrom, dateTo, latitudeMin, latitudeMax, longitudeMin, longitudeMax, depthMin, depthMax, magnitudeMin, magnitudeMax);
		ArrayList<Earthquake> earthquakes = data.getEarthquakeList(filter);
		return earthquakes;
	}
}
