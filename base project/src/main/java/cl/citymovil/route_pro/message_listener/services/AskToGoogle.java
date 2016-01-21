package cl.citymovil.route_pro.message_listener.services;

import cl.citymovil.route_pro.solver.util.DistanceTimeMatrixUtility;
import cl.citymovil.route_pro.solver.util.LocationConteiner;

public interface AskToGoogle {
	
	public DistanceTimeMatrixUtility getDistanceByGoogle(LocationConteiner locationConteiner);
	public void getTimeByGoogle();

}
