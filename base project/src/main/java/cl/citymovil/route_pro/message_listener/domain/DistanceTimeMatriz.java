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
public class DistanceTimeMatriz {
	public static final Logger log = LoggerFactory.getLogger(Location.class);

	public static Logger getLog() {
		return log;
	}
	
	@JsonProperty("distance_time_matrix_id")
	@Id
	@Column//(name="distance_time_matrix_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer distanceTimeMatrixId;
//	
//	private Double distance;
//	private Double duration;
//	private Integer end;
//	private Integer start;
	
	public  DistanceTimeMatriz(){}

	
//	public Double getDistance() {
//		return distance;
//	}
//	public void setDistance(Double distance) {
//		this.distance = distance;
//	}
//	public Double getDuration() {
//		return duration;
//	}
//	public void setDuration(Double duration) {
//		this.duration = duration;
//	}
//	public Integer getEnd() {
//		return end;
//	}
//	public void setEnd(Integer end) {
//		this.end = end;
//	}
//	public Integer getStart() {
//		return start;
//	}
//	public void setStart(Integer start) {
//		this.start = start;
//	}
	public Integer getDistanceTimeMatrixId() {
		return distanceTimeMatrixId;
	}
	public void setDistanceTimeMatrixId(Integer distanceTimeMatrixId) {
		this.distanceTimeMatrixId = distanceTimeMatrixId;
	}

}
