package cl.citymovil.route_pro.message_listener.dao;

import java.util.Date;

import cl.citymovil.route_pro.message_listener.domain.Location;

public interface VehiclePositionDAO {

	public void addVehiclePositionTest(Location location);

	public void addVehiclePosition(String external_id, double latitude, double longitude, Date date );
	
	public void updateVehicleLastPosition(String external_id, double latitude, double longitude, Date date );

}
