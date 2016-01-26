package cl.citymovil.route_pro.message_listener.services;


import java.util.ArrayList;

import cl.citymovil.route_pro.solver.util.LocationContainer;
import cl.citymovil.route_pro.solver.util.RelationLocation;

public interface AskToGoogle {
	
	public ArrayList<RelationLocation>  getDistanceByGoogle(LocationContainer locationConteiner);
	public void getTimeByGoogle();

}
