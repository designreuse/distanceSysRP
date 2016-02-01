package cl.citymovil.route_pro.message_listener.services;

import java.util.ArrayList;
import java.util.Map;

import cl.citymovil.route_pro.message_listener.domain.DistanceTimeDataComplete;
import cl.citymovil.route_pro.message_listener.domain.Location;
import cl.citymovil.route_pro.solver.util.LocationContainer;
import cl.citymovil.route_pro.solver.util.RelationLocation;

public interface DistanceTimeMatrixServiceAlpha {
	
	Map<Long, Map<Long, DistanceTimeDataComplete>>  PreprocessAlpha(ArrayList <Location> arrayWithIdLocation);
	
	ArrayList <LocationContainer> PreprocessBeta(Map<Long, Map<Long, DistanceTimeDataComplete>> distanceTimeHashMap);
	
	ArrayList<RelationLocation>  Process(LocationContainer locationContainer);
	
	void PostProcessAlpha(	ArrayList<RelationLocation>  relationLocation);


}
