package bean;

import java.sql.Timestamp;
/**
 * @Author: Alan
 * @since : Java_8_151
 * @version: 1.0
 */
public class Earthquake {
	private int id;
	private Timestamp UTC_date;
	private float latitude;
	private float longitude;
	private float depth;
	private float magnitude;
	private String region;
	
	public Earthquake() {
	}

	/**
	 * @param id
	 * @param uTC_date
	 * @param latitude
	 * @param longitude
	 * @param depth
	 * @param magnitude
	 * @param region
	 */
	public Earthquake(int id, Timestamp uTC_date, float latitude, float longitude, float depth, float magnitude,String region){
		this.id = id;
		this.UTC_date = uTC_date;
		this.latitude = latitude;
		this.longitude = longitude;
		this.depth = depth;
		this.magnitude = magnitude;
		this.region = region;
	}

	/**
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public Timestamp getUTC_date() {
		return UTC_date;
	}

	/**
	 * @param uTC_date
	 * @throws java.lang.IllegalArgumentException
	 */
	public void setUTC_date(Timestamp uTC_date) throws java.lang.IllegalArgumentException {
		this.UTC_date = uTC_date;
	}

	/**
	 * @return
	 */
	public float getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 */
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return
	 */
	public float getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 */
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return
	 */
	public float getDepth() {
		return depth;
	}

	/**
	 * @param depth
	 */
	public void setDepth(float depth) {
		this.depth = depth;
	}

	/**
	 * @return
	 */
	public float getMagnitude() {
		return magnitude;
	}

	/**
	 * @param magnitude
	 */
	public void setMagnitude(float magnitude) {
		this.magnitude = magnitude;
	}

	/**
	 * @return
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @param region
	 */
	public void setRegion(String region) {
		this.region = region;
	}
}
