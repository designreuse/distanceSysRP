package cl.citymovil.route_pro.message_listener.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import cl.citymovil.route_pro.message_listener.domain.DistanceTime;
import cl.citymovil.route_pro.message_listener.domain.Location;
import cl.citymovil.route_pro.message_listener.domain.LocationTmp;
import cl.citymovil.route_pro.solver.util.LocationConteiner;

@Service
public class DistanceMatrixServiceImpl implements DistanceMatrixService{

	
	@Autowired
	LocationConteiner conteinerLocation ;
	
	

	@Override
	public LocationConteiner Preprocess() {
		//Busqueda de nuevas locaciones 
		//Busueda de las locaciones anteriores si es que encuentro nuevas locaciones, si no hay nuevas locaciones, retorno null.
		
		System.out.println("en Preprocess %%%%% ");
		
		conteinerLocation.LoadLocationConteiner();
		
		return conteinerLocation;
		//LocationConteiner conteinerLocation = new LocationConteiner();
		
	
		
		
		
		//Location location = (Location) list.iterator();
//		while (((Iterator<Location>) location).hasNext()){
//			System.out.println("Imprimiendo Algo");
//		  System.out.println(((Iterator<Location>) location).next());
//		}
		//System.out.println("Saliendo");
		
	
	}

	@Override
	public List<Location> Process(List<LocationTmp> newLocation, List<Location> oldLocation) {
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
