package cl.citymovil.route_pro.solver.util;

import java.util.List;

import javax.persistence.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cl.citymovil.route_pro.message_listener.dao.LocationDAO;
import cl.citymovil.route_pro.message_listener.dao.LocationDAOImpl;
import cl.citymovil.route_pro.message_listener.dao.LocationTmpDAO;
import cl.citymovil.route_pro.message_listener.dao.LocationTmpDAOImpl;
import cl.citymovil.route_pro.message_listener.domain.Location;
import cl.citymovil.route_pro.message_listener.domain.LocationTmp;


@Service
public class LocationConteiner {
	
	public static final Logger log = LoggerFactory.getLogger(Location.class);

	public static Logger getLog() {
		return log;
	}
	@Autowired
	LocationDAO locationDAO;
	
	@Autowired
	LocationTmpDAO locationTmpDAO;
//	LocationDAO locationDAO= new LocationDAOImpl();
//	LocationTmpDAO locationTmpDAO = new LocationTmpDAOImpl();
//	@Autowired
//	LocationConteiner conteinerLocation ;
	
	private List<Location> location;
	private List<LocationTmp> locationTmp;
	
	
	public void LoadLocationConteiner(){
//		LocationDAO locationDAO= new LocationDAOImpl();
//		LocationTmpDAO locationTmpDAO = new LocationTmpDAOImpl();
		
		List<LocationTmp> listTmp;
//		listTmp=locationTmpDAO.getTmpLocationList();
//		
//		if(listTmp!=null){
//			return;
//			
//		}else{
			List<Location> list;
			
			list = locationDAO.getLocationList();
			System.out.println("Afuera del GET en list.tostring"+list.toString());
			this.location=list;
			this.locationTmp=null;
			
//		}
		
		//LocationTmpDAO locationTmpDAO;
		
		
		
		
		
		
		System.out.println("<<<<<<<<<<<<  saliendo del getLocationList()  >>>>>>>>>>>>>>>>");
		
	    listTmp = locationTmpDAO.getTmpLocationList();//locationDAO.getTmpLocationList();
	
		
	}
	public List<Location> getLocation() {
		return location;
	}
	public void setLocation(List<Location> location) {
		this.location = location;
	}
	public List<LocationTmp> getLocationTmp() {
		return locationTmp;
	}
	public void setLocationTmp(List<LocationTmp> locationTmp) {
		this.locationTmp = locationTmp;
	}
	
	
	

}
