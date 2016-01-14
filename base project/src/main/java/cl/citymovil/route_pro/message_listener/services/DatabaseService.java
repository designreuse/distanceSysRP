package cl.citymovil.route_pro.message_listener.services;

import java.util.Date;
import java.util.List;

import cl.citymovil.route_pro.message_listener.domain.Location;
import cl.citymovil.route_pro.message_listener.domain.ScheduledCustomer;

public interface DatabaseService {

	void addVehiclePositionTest(Location location);
	void updateVehicleLastPosition(String vehicleId, double latitude, double longitude,Date date);
	void addVehiclePosition(String vehicleId, double latitude, double longitude,Date date);
	List<ScheduledCustomer> getScheduledCustomersByVehicleExternalId(String externalId);
	void updateScheduledCustomerRealArrivalTime(long scheduledCustomerId,Date date);
	
}
