package cl.citymovil.route_pro.message_listener.services;

import cl.citymovil.route_pro.message_listener.domain.Location;
import cl.citymovil.route_pro.message_listener.domain.LocationTmp;

public interface DataBaseInteractionService {

	public Location makeLocationWithLocationTmp(LocationTmp locTmp);

}
