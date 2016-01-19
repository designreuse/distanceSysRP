package cl.citymovil.route_pro.message_listener.services;


import java.util.List;

import cl.citymovil.route_pro.message_listener.domain.DistanceTime;
import cl.citymovil.route_pro.message_listener.domain.DistanceTimeMatriz;
import cl.citymovil.route_pro.message_listener.domain.Location;
import cl.citymovil.route_pro.message_listener.domain.LocationTmp;
import cl.citymovil.route_pro.solver.util.LocationConteiner;

public interface DistanceMatrixService {
	
	LocationConteiner Preprocess();
	
	DistanceTimeMatriz[] Process(LocationConteiner locationConteiner);

	void PostProcess(List <DistanceTime> saveDistanceTime);

}
