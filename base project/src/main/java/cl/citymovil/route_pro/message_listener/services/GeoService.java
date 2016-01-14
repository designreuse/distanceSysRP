package cl.citymovil.route_pro.message_listener.services;

import java.util.List;

import cl.citymovil.route_pro.message_listener.domain.Location;
import cl.citymovil.route_pro.message_listener.domain.ScheduledCustomer;

public interface GeoService {

	long isNearScheduledCustomer(List<ScheduledCustomer> scheduledCustomers, Location current);

}
