package cl.citymovil.route_pro.message_listener.dao;

import java.util.List;

import cl.citymovil.route_pro.message_listener.domain.Location;


public interface LocationDAO{
	
	public List<Location> getLocationList(int customerId);
	
	public List<Location> getTmpLocationList(int customerId);

    public void mergeLocation(Location loc);
    
    public void persistLocation(Location loc);

}
