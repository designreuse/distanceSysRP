package cl.citymovil.route_pro.message_listener.services;

import cl.citymovil.route_pro.message_listener.domain.DistanceTimeMatriz;
import cl.citymovil.route_pro.solver.util.LocationConteiner;

public interface AskToGoogle {
	
	public DistanceTimeMatriz[] getDistanceByGoogle(LocationConteiner locationConteiner);
//	 String origins2[]={" 55.930, -3.118"};
//    String destiny2[]={ "50.087, 14"};
	
	public void getTimeByGoogle();

}
