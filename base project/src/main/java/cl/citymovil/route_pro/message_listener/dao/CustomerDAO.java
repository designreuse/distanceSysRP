package cl.citymovil.route_pro.message_listener.dao;

import java.util.List;

import cl.citymovil.route_pro.message_listener.domain.Customer;
import cl.citymovil.route_pro.message_listener.domain.Location;


public interface CustomerDAO {
	
	public List<Customer> getCustomerList();
	
	public List<Customer> getCustomerWithNewLocation();

    public void mergeLocation(Location loc);
    
    public void persistLocation(Location loc);
}
