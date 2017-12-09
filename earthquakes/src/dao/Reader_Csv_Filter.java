package dao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;

import bean.Earthquake;
import controller.FilterLikeController;

public class Reader_Csv_Filter implements Reader{
	private String streamSrc = "/dataSource/earthquakes.csv";
	private ArrayList<Earthquake> earthquakeList = new ArrayList<Earthquake>();

	@Override
	public ArrayList<Earthquake> getEarthquakeList() {
		return null;
	}

	public ArrayList<Earthquake> getEarthquakeList(FilterLikeController filter) {
		try {
			earthquakeList.clear();
			InputStream in = this.getClass().getResourceAsStream(streamSrc);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				String str[] = line.split(",");
				Earthquake earthquake = new Earthquake();
				earthquake.setId(Integer.parseInt(str[0]));
				earthquake.setUTC_date(Timestamp.valueOf(str[1].replaceAll("\"","")));
				earthquake.setLatitude(Float.parseFloat(str[2]));
				earthquake.setLongitude(Float.parseFloat(str[3]));
				earthquake.setDepth(Float.parseFloat(str[4]));
				earthquake.setMagnitude(Float.parseFloat(str[5]));
				earthquake.setRegion(str[6].replaceAll("\"",""));
				if (filter.test(earthquake)){
					earthquakeList.add(earthquake);
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return earthquakeList;
	}
}
