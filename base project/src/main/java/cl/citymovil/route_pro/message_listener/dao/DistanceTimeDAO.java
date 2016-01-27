package cl.citymovil.route_pro.message_listener.dao;

import java.util.List;

import cl.citymovil.route_pro.message_listener.domain.DistanceTime;


public interface DistanceTimeDAO {
	
	public List<DistanceTime> getDistanceTimeList();
	

    public void mergeDistanceTime(DistanceTime distanceTime);
    
    public void persistDistanceTime(DistanceTime distanceTime);

}
