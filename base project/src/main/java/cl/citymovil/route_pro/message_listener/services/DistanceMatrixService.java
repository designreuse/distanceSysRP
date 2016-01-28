package cl.citymovil.route_pro.message_listener.services;



import java.util.ArrayList;

import cl.citymovil.route_pro.solver.util.DistanceTimeMatrixUtility;
import cl.citymovil.route_pro.solver.util.LocationContainer;
import cl.citymovil.route_pro.solver.util.RelationLocation;

public interface DistanceMatrixService {
	
	ArrayList <Long []>  PreprocessAlpha();
	
	LocationContainer Preprocess();
	
	ArrayList<RelationLocation>  Process(LocationContainer locationConteiner);

	void PostProcess(	ArrayList<RelationLocation>  distanceTimeMatrixUtility);

}
