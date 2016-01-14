package cl.citymovil.route_pro.message_listener.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Table(name="scheduled_customer_view")
@Immutable
public class ScheduledCustomer {

	@Id
	@Column(name = "scheduled_customer_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int scheduledCustomerId;

	@Column(name="external_id")
	private String vehicleExternalId;
	
	@Column
	private double latitude;
	
	@Column
	private double longitude;

	public int getScheduledCustomerId() {
		return scheduledCustomerId;
	}

	public void setScheduledCustomerId(int scheduledCustomerId) {
		this.scheduledCustomerId = scheduledCustomerId;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}
