package cl.citymovil.route_pro.message_listener.dao;

import java.util.List;

import cl.citymovil.route_pro.message_listener.domain.LocationTmp;



public interface LocationTmpDAO {
	
	public List<LocationTmp> getTmpLocationList();
	
	public void updateTmpLocation(long LocationId);

    public void mergeTmpLocation(LocationTmp loc);
    
    public void persistTmpLocation(LocationTmp loc);
    
    public void deleteTmpLocation(long LocationId);

}
