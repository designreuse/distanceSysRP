package cl.citymovil.route_pro.solver.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.citymovil.route_pro.message_listener.dao.LocationDAO;
import cl.citymovil.route_pro.message_listener.domain.DistanceTimeMatriz;
import cl.citymovil.route_pro.message_listener.domain.Location;

@Service
public class DistanceTimeMatrixUtility {
	
	
	
	public static final Logger log = LoggerFactory.getLogger(Location.class);

	public static Logger getLog() {
		return log;
	}
	private DistanceTimeMatriz[] distanceTimeMatriz;
	
	public DistanceTimeMatriz[] getDistanceTimeMatriz() {
		return distanceTimeMatriz;
	}

	public void setDistanceTimeMatriz(DistanceTimeMatriz[] distanceTimeMatriz) {
		this.distanceTimeMatriz = distanceTimeMatriz;
	}
	public void appendDistanceTime(DistanceTimeMatriz distanceTimeMatriz){
		System.out.println("////////////////////////////////////////////////////////////////////////////////");
		System.out.println("ingresando un DistanceTime al arreglo con largo: "+this.distanceTimeMatriz.length);
		this.distanceTimeMatriz[this.distanceTimeMatriz.length]=distanceTimeMatriz;
		System.out.println("////////////////////////////////////////////////////////////////////////////////");
		System.out.println("Ingreso realizado al arreglo con nuevo largo: "+this.distanceTimeMatriz.length);
		
		
	}

	

}
