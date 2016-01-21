package cl.citymovil.route_pro.message_listener.services;

import cl.citymovil.route_pro.solver.util.DistanceTimeMatrixUtility;
import cl.citymovil.route_pro.solver.util.LocationContainer;

public interface AskToGoogle {
	
	public DistanceTimeMatrixUtility getDistanceByGoogle(LocationContainer locationConteiner);
	public void getTimeByGoogle();

}
