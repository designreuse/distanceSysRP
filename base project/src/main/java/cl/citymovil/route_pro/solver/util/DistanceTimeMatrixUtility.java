package cl.citymovil.route_pro.solver.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.maps.model.DistanceMatrix;

import cl.citymovil.route_pro.message_listener.dao.LocationDAO;
import cl.citymovil.route_pro.message_listener.domain.DistanceTimeMatriz;
import cl.citymovil.route_pro.message_listener.domain.Location;

@Service
public class DistanceTimeMatrixUtility {
	
	
	
	public static final Logger log = LoggerFactory.getLogger(Location.class);

	public static Logger getLog() {
		return log;
	}
	
	private DistanceMatrix distanceMatrix;
	private String[] origen;
	private String[] destiny;
	
	
	public DistanceTimeMatrixUtility(){}
	
	public DistanceMatrix getDistanceMatrix() {
		return distanceMatrix;
	}
	public void setDistanceMatrix(DistanceMatrix distanceMatrix) {
		this.distanceMatrix = distanceMatrix;
	}
	public String[] getOrigen() {
		return origen;
	}
	public void setOrigen(String[] origen) {
		this.origen = origen;
	}
	public String[] getDestiny() {
		return destiny;
	}
	public void setDestiny(String[] destiny) {
		this.destiny = destiny;
	}


}
