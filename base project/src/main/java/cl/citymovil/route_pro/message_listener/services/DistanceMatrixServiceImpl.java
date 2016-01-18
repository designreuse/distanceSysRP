package cl.citymovil.route_pro.message_listener.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.citymovil.route_pro.message_listener.dao.LocationDAO;
import cl.citymovil.route_pro.message_listener.dao.LocationDAOImpl;
import cl.citymovil.route_pro.message_listener.domain.DistanceTime;
import cl.citymovil.route_pro.message_listener.domain.Location;

@Service
public class DistanceMatrixServiceImpl implements DistanceMatrixService{

	
	@Autowired
	LocationDAO locationDAO;
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<List<Location>> Preprocess() {
		//Busqueda de nuevas locaciones 
		//Busueda de las locaciones anteriores si es que encuentro nuevas locaciones, si no hay nuevas locaciones, retorno null.
		
		System.out.println("en Preprocess %%%%%%%%%");
		//locationList = new ArrayList<Location>();
//		LocationDAO locationDAO = new LocationDAOImpl();
		//List<Location>[] li = new Array<Location>();
		ArrayList<List<Location>> li = new ArrayList<List<Location>>(2);
		
		List<Location> list;
				
		list = locationDAO.getLocationList();
		System.out.println("<<<<<<<<<<<<  saliendo del getLocationList()  >>>>>>>>>>>>>>>>");
		
		List<Location> listTmp = null;//locationDAO.getTmpLocationList();
		
		li.add(0, list);
		li.add(1, listTmp);
		
		
		//Location location = (Location) list.iterator();
//		while (((Iterator<Location>) location).hasNext()){
//			System.out.println("Imprimiendo Algo");
//		  System.out.println(((Iterator<Location>) location).next());
//		}
		System.out.println("Saliendo");
		
		return li;
	}

	@Override
	public List<Location> Process(List<Location> newLocation, List<Location> oldLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void PostProcess(List<DistanceTime> saveDistanceTime) {
		// TODO Auto-generated method stub
		
	}


	void mergeLocation(Location loc) {
		// TODO Auto-generated method stub
		
	}


	 void persistLocation(Location loc) {
		// TODO Auto-generated method stub
		
	}

}
