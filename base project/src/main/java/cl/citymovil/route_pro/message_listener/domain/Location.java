package cl.citymovil.route_pro.message_listener.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

	public static final Logger log = LoggerFactory.getLogger(Location.class);

	@JsonProperty("location_id")
	@Id
	@Column(name="location_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long locationId;

	private double latitude;
	private double longitude;
	
	public  Location(){}
	
	public Location(double latitude ,double longitude ){
		
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public long getLocationId() {
		return this.locationId;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

    public String toString() {
		return String.valueOf(this.locationId);
	}
	
	
}
