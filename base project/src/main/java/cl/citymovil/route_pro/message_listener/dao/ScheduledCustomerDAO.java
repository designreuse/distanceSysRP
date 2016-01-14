package cl.citymovil.route_pro.message_listener.dao;

import java.util.Date;
import java.util.List;

import cl.citymovil.route_pro.message_listener.domain.ScheduledCustomer;

public interface ScheduledCustomerDAO {
	
	List<ScheduledCustomer> getScheduledCustomersByVehicleExternalId(String externalId);
	void updateScheduledCustomerRealArrivalTime(long scheduledCustomerId, Date date ); 
	
}
