package dao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;

import bean.Earthquake;
import controller.FilterLikeController;

public class Reader_Csv_Filter implements Reader{
	private String dataSrc = "earthquakes.csv";

	@Override
	public ArrayList<Earthquake> getEarthquakeList() {
		return null;
	}

	public ArrayList<Earthquake> getEarthquakeList(FilterLikeController filter) {
		ArrayList<Earthquake> earthquakeList = new ArrayList<Earthquake>();
		try {
			FileInputStream in = new FileInputStream(dataSrc);
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
