package cl.citymovil.route_pro.message_listener.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="location_tmp")
public class LocationTmp {
	
	public static final Logger log = LoggerFactory.getLogger(Location.class);

	public static Logger getLog() {
		return log;
	}
	
	@JsonProperty("location_tmp_id")
	@Id
	@Column(name="location_tmp_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long locationId;
	
	@JsonProperty("latitude_tmp")
	@Column(name="latitude_tmp")
	private Double latitudeTmp;//Los valores double e int so primitivos y en java no admiten null,
	//para solucionar esto, se hace uso de Double y de Integer, que son clases en java que admiten este tipo de valores.
	
	@JsonProperty("longitude_tmp")
	@Column(name="longitude_tmp")
	private Double longitudeTmp;
	
	public  LocationTmp(){}

	public LocationTmp(Double latitude, Double longitude) {

		this.latitudeTmp = latitude;
		this.longitudeTmp = longitude;
	}
	public Double getLatitude() {
		return latitudeTmp;
	}

	public long getLocationId() {
		return this.locationId;
	}

	public Double getLongitude() {
		return longitudeTmp;
	}

	public void setLatitude(Double latitude) {
		this.latitudeTmp = latitude;
	}

	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}

	public void setLongitude(Double longitude) {
		this.longitudeTmp = longitude;
	}

    public String toString() {
		return String.valueOf(this.locationId);
	}

	
	

}
