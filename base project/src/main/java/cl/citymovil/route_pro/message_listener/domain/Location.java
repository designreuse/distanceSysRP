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

	public static Logger getLog() {
		return log;
	}

	@JsonProperty("location_id")
	@Id
	@Column(name="location_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long locationId;
	private Double latitude;//Los valores double e int so primitivos y en java no admiten null,
	//para solucionar esto, se hace uso de Double y de Integer, que son clases en java que admiten este tipo de valores.
	
	private Double longitude;
	
	public  Location(){}

	public Location(Double latitude, Double longitude) {

		this.latitude = latitude;
		this.longitude = longitude;
	}
	


	public Double getLatitude() {
		return latitude;
	}

	public long getLocationId() {
		return this.locationId;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

    public String toString() {
		return String.valueOf(this.locationId);
	}
    
   
		
}
