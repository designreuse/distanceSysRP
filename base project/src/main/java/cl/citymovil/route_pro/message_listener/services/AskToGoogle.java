package cl.citymovil.route_pro.message_listener.services;

import cl.citymovil.route_pro.message_listener.domain.DistanceTimeMatriz;
import cl.citymovil.route_pro.solver.util.LocationConteiner;

public interface AskToGoogle {
	
	public DistanceTimeMatriz[] getDistanceByGoogle(LocationConteiner locationConteiner);
	public void getTimeByGoogle();

}
