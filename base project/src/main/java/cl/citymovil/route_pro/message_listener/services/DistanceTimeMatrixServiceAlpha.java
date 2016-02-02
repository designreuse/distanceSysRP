package cl.citymovil.route_pro.message_listener.services;

import java.util.ArrayList;
import java.util.Map;

import cl.citymovil.route_pro.message_listener.domain.DistanceTimeData;
import cl.citymovil.route_pro.message_listener.domain.DistanceTimeDataComplete;
import cl.citymovil.route_pro.message_listener.domain.Location;
import cl.citymovil.route_pro.solver.util.LocationContainer;
import cl.citymovil.route_pro.solver.util.LocationContainerForGoogleAsk;
import cl.citymovil.route_pro.solver.util.RelationLocation;

public interface DistanceTimeMatrixServiceAlpha {
	
	Map<Long, Map<Long, DistanceTimeData>>  PreprocessAlpha(ArrayList <Location> arrayWithIdLocation);
	
	ArrayList<LocationContainerForGoogleAsk> PreprocessBeta(Map<Long, Map<Long, DistanceTimeData>> distanceTimeHashMap, ArrayList <Location> arrayWithIdLocation );
	
	ArrayList<RelationLocation>  Process(LocationContainerForGoogleAsk locationContainerForGoogle);
	
	void PostProcessAlpha(	ArrayList<RelationLocation>  relationLocation);
}
