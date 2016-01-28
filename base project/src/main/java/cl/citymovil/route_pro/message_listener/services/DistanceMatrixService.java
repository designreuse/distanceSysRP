package cl.citymovil.route_pro.message_listener.services;



import java.util.ArrayList;
import java.util.Map;

import cl.citymovil.route_pro.message_listener.domain.DistanceTimeData;
import cl.citymovil.route_pro.message_listener.domain.Location;
import cl.citymovil.route_pro.solver.util.DistanceTimeMatrixUtility;
import cl.citymovil.route_pro.solver.util.LocationContainer;
import cl.citymovil.route_pro.solver.util.RelationLocation;

public interface DistanceMatrixService {
	
	Map<Long, Map<Long, DistanceTimeData>>  PreprocessAlpha(ArrayList <Location> arrayWithIdLocation);
	
	void PostProcessAlpha(	ArrayList<RelationLocation>  relationLocation);
	
	LocationContainer Preprocess();
	
	ArrayList<RelationLocation>  Process(LocationContainer locationConteiner);

	void PostProcess(	ArrayList<RelationLocation>  relationLocation);

}
