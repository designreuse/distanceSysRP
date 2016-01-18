package cl.citymovil.route_pro.message_listener.services;

import java.util.ArrayList;
import java.util.List;

import cl.citymovil.route_pro.message_listener.domain.DistanceTime;
import cl.citymovil.route_pro.message_listener.domain.Location;

public interface DistanceMatrixService {
	
	ArrayList<List<Location>> Preprocess();
	
	List<Location> Process(List <Location> newLocation, List <Location> oldLocation);

	void PostProcess(List <DistanceTime> saveDistanceTime);

}
