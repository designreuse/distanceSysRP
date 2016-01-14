package cl.citymovil.route_pro.message_listener.services.interfaces;

import java.util.List;

import cl.citymovil.route_pro.message_listener.domain.Location;


public interface LocationManager {

	  public List<Location> getLocations();
	    
	    public void newLocation(String direccion);
}
