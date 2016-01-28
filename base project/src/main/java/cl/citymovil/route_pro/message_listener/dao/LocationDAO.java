package cl.citymovil.route_pro.message_listener.dao;

import java.util.ArrayList;
import java.util.List;

import cl.citymovil.route_pro.message_listener.domain.Location;


public interface LocationDAO{
	
	public ArrayList<Location> getLocationList();
	
	public void updateLocation(long LocationId);

    public void mergeLocation(Location loc);
    
    public void persistLocation(Location loc);
    
    public void deleteLocation(long LocationId);
}
