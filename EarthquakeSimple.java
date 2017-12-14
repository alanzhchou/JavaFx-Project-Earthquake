package bean;

import javafx.beans.property.*;

public class EarthquakeSimple {
	private SimpleStringProperty UTC_date;
	private SimpleStringProperty latitude;
	private SimpleStringProperty longitude;
	private SimpleStringProperty depth;
	private SimpleStringProperty magnitude;
	private SimpleStringProperty region;

	public EarthquakeSimple(Earthquake earthquake) {
		this.UTC_date = new SimpleStringProperty(earthquake.getUTC_date().toString());
		this.latitude = new SimpleStringProperty(Float.toString(earthquake.getLatitude()));
		this.longitude = new SimpleStringProperty(Float.toString(earthquake.getLongitude()));
		this.depth = new SimpleStringProperty(Float.toString(earthquake.getDepth()));
		this.magnitude = new SimpleStringProperty(Float.toString(earthquake.getMagnitude()));
		this.region = new SimpleStringProperty(earthquake.getRegion());
	}

	public float getMagnitude(){
		return Float.valueOf(magnitude.getValue());
	}

	public float getLongitude(){
		return Float.valueOf(longitude.getValue());
	}

	public float getLatitude(){
		return Float.valueOf(latitude.getValue());
	}


	public SimpleStringProperty UTC_dateProperty() {
		return UTC_date;
	}

	public SimpleStringProperty latitudeProperty() {
		return latitude;
	}

	public SimpleStringProperty longitudeProperty() {
		return longitude;
	}

	public SimpleStringProperty depthProperty() {
		return depth;
	}

	public SimpleStringProperty magnitudeProperty() {
		return magnitude;
	}

	public SimpleStringProperty regionProperty() {
		return region;
	}
}
