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
	
	private List<Location> location=null;
	private List<LocationTmp> locationTmp=null;
	
	
	public void LoadLocationConteiner(){
		
		List<LocationTmp> listTmp;
		
		//////////////////////////////////
			try {
				listTmp=locationTmpDAO.getTmpLocationList();
				System.out.println(" <><><> ::: EN TRY Buscando la lista de locaciones ya registradas ::: <><><> ");
				List<Location> list;
				
				list = locationDAO.getLocationList();
				
				this.location=list;
				this.locationTmp=listTmp;
				
				System.out.println("::: LISTANDO LOCACIONES :::");
				this.listLocation();
				
				
			    listTmp = locationTmpDAO.getTmpLocationList();//locationDAO.getTmpLocationList();
			   
			
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(" *******<><><> ::: Saliendo porque no hay nueva locacion ::: <><><> ");
			}
			catch (IllegalAccessError e) {
				System.out.println(" *******<><><> ::: Saliendo porque no hay nueva locacion ::: <><><> "+e);
				// TODO: handle exception
			}
			
			System.out.println("<<<<<<<<<<<<  saliendo del getLocationList()  >>>>>>>>>>>>>>>>");
		
			
		
		
		//LocationTmpDAO locationTmpDAO;
			
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
	public void listLocation(){
		if(this.location==null){
			
			System.out.println(":: La lista de Locaciones se encuentra vacia o no ha sido cargada correctamente ::");
		}else{
			
			
			 for (Location i: this.location) {
			        System.out.println ("Dirección LocationID:"+i.getLocationId()+" Latitud:"+i.getLatitude()); //Muestra cada uno de los nombres dentro de listaDeNombres
			    }
			
		}
		
		
	}
	
	public void listTmpLocation(){
		
	}
	
	
	

}
