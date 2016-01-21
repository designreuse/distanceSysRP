package cl.citymovil.route_pro.message_listener.services;


import java.util.List;

import com.google.maps.model.DistanceMatrix;

import cl.citymovil.route_pro.message_listener.domain.DistanceTime;
import cl.citymovil.route_pro.message_listener.domain.DistanceTimeMatriz;
import cl.citymovil.route_pro.message_listener.domain.Location;
import cl.citymovil.route_pro.message_listener.domain.LocationTmp;
import cl.citymovil.route_pro.solver.util.DistanceTimeMatrixUtility;
import cl.citymovil.route_pro.solver.util.LocationContainer;

public interface DistanceMatrixService {
	
	LocationContainer Preprocess();
	
	 List <DistanceMatrix> Process(LocationContainer locationConteiner);

	void PostProcess(List <DistanceTime> saveDistanceTime);

}
