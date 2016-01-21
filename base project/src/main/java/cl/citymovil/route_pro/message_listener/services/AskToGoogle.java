package cl.citymovil.route_pro.message_listener.services;

import java.util.List;

import com.google.maps.model.DistanceMatrix;

import cl.citymovil.route_pro.solver.util.DistanceTimeMatrixUtility;
import cl.citymovil.route_pro.solver.util.LocationContainer;

public interface AskToGoogle {
	
	public List <DistanceMatrix> getDistanceByGoogle(LocationContainer locationConteiner);
	public void getTimeByGoogle();

}
