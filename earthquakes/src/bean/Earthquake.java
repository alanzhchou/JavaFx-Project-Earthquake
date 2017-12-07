package bean;

import java.sql.Timestamp;

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

	public Earthquake(int id, Timestamp uTC_date, float latitude, float longitude, float depth, float magnitude,String region) {
		this.id = id;
		this.UTC_date = uTC_date;
		this.latitude = latitude;
		this.longitude = longitude;
		this.depth = depth;
		this.magnitude = magnitude;
		this.region = region;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getUTC_date() {
		return UTC_date;
	}

	public void setUTC_date(Timestamp uTC_date) {
		UTC_date = uTC_date;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getDepth() {
		return depth;
	}

	public void setDepth(float depth) {
		this.depth = depth;
	}

	public float getMagnitude() {
		return magnitude;
	}

	public void setMagnitude(float magnitude) {
		this.magnitude = magnitude;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
}
