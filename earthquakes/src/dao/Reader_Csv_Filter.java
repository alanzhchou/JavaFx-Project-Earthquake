package dao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;

import bean.Earthquake;
import controller.FilterLikeController;

/**
 * @Author: Alan
 * @since : Java_8_151
 * @version: 1.0
 */
public class Reader_Csv_Filter implements Reader{
	/**
	 * the Csv file location
	 */
	private String fileSource;

	/**
	 * the information list of earthquake for upper layer
	 */
	private ArrayList<Earthquake> earthquakeList = new ArrayList<Earthquake>();

	public Reader_Csv_Filter(){
		this.fileSource = DataSourceProp.getCsvLocation();
	}

	/**
	 * no use, please overload this function
	 * @return
	 */
	@Override
	public ArrayList<Earthquake> getEarthquakeList() {
		return null;
	}

	/**
	 * get the required earthquake list from Csv file
	 * @param filter a filter for DB system to get enough info to get correct list
	 * @return ArrayList<Earthquake>, required earthquake list
	 */
	public ArrayList<Earthquake> getEarthquakeList(FilterLikeController filter) {
		try {
			earthquakeList.clear();
			InputStream in = new FileInputStream(fileSource);
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
