package cl.citymovil.route_pro.message_listener.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cl.citymovil.route_pro.message_listener.domain.DistanceTime;
import cl.citymovil.route_pro.message_listener.domain.DistanceTimeData;
import cl.citymovil.route_pro.message_listener.domain.Location;


public interface DistanceTimeDAO {
	
	public List<DistanceTime> getDistanceTimeList();
	

    public void mergeDistanceTime(DistanceTime distanceTime);
    
    public void persistDistanceTime(DistanceTime distanceTime);
    
    
    public List<DistanceTime> getDistanceTimeOriginsOf(ArrayList <Location> locationList);
    
    public List<DistanceTime> getDistanceTimeDestiniesOf(ArrayList <Location> locationList);

}
