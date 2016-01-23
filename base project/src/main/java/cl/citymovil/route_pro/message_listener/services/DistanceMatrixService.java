package cl.citymovil.route_pro.message_listener.services;



import cl.citymovil.route_pro.solver.util.DistanceTimeMatrixUtility;
import cl.citymovil.route_pro.solver.util.LocationContainer;

public interface DistanceMatrixService {
	
	LocationContainer Preprocess();
	
	DistanceTimeMatrixUtility[]  Process(LocationContainer locationConteiner);

	void PostProcess(DistanceTimeMatrixUtility[]  distanceTimeMatrixUtility);

}
