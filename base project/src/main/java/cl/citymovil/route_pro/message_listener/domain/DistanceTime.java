package cl.citymovil.route_pro.message_listener.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="distance_time_matrix")
public class DistanceTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("distance_time_matrix_id")
	@Column(name="distance_time_matrix_id")
	private String distanceTimeMatrixId;

	private long origin;
	private long destination;
	private double distance;
	private double duration;

	public String getDistanceTimeMatrixId() {
		return distanceTimeMatrixId;
	}

	public void setDistanceTimeMatrixId(String startend) {
		this.distanceTimeMatrixId = startend;
	}

	public long getOrigin() {
		return origin;
	}

	public void setOrigin(int start) {
		this.origin = start;
	}

	public long getDestination() {
		return destination;
	}

	public void setDestination(int end) {
		this.destination = end;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
}
