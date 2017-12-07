package bean;

import java.sql.Timestamp;

public class Filter {
	Timestamp dateFrom;
	Timestamp dateTo;
	float latitudeMin;
	float latitudeMax;
	float longitudeMin;
	float longitudeMax;
	float depthMin;
	float depthMax;
	float magnitudeMin;
	float magnitudeMax;

	public Filter(Timestamp dateFrom, Timestamp dateTo, float latitudeMin, float latitudeMax, float longitudeMin,
			float longitudeMax, float depthMin, float depthMax, float magnitudeMin, float magnitudeMax) {
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.latitudeMin = latitudeMin;
		this.latitudeMax = latitudeMax;
		this.longitudeMin = longitudeMin;
		this.longitudeMax = longitudeMax;
		this.depthMin = depthMin;
		this.depthMax = depthMax;
		this.magnitudeMin = magnitudeMin;
		this.magnitudeMax = magnitudeMax;
	}

	public boolean test(Earthquake earthquake) {
		if (this.dateFrom.after(earthquake.getUTC_date()))
			return false;
		if (this.dateTo.before(earthquake.getUTC_date()))
			return false;
		if (this.latitudeMin > earthquake.getLatitude())
			return false;
		if (this.latitudeMax < earthquake.getLatitude())
			return false;
		if (this.longitudeMin > earthquake.getLongitude())
			return false;
		if (this.longitudeMax < earthquake.getLongitude())
			return false;
		if (this.depthMin > earthquake.getDepth())
			return false;
		if (this.depthMax < earthquake.getDepth())
			return false;
		if (this.magnitudeMin > earthquake.getMagnitude())
			return false;
		if (this.magnitudeMax < earthquake.getMagnitude())
			return false;
		return true;
	}
}
