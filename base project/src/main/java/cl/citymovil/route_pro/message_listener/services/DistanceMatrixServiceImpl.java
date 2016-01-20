package cl.citymovil.route_pro.message_listener.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import cl.citymovil.route_pro.message_listener.domain.DistanceTime;
import cl.citymovil.route_pro.message_listener.domain.DistanceTimeMatriz;
import cl.citymovil.route_pro.message_listener.domain.Location;
import cl.citymovil.route_pro.message_listener.domain.LocationTmp;
import cl.citymovil.route_pro.solver.util.LocationConteiner;

@Service
public class DistanceMatrixServiceImpl implements DistanceMatrixService{

	
	@Autowired
	LocationConteiner conteinerLocation;
	
	@Autowired
	AskToGoogle askToGoogle;
	
	
	
	

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
	public DistanceTimeMatriz[] Process(LocationConteiner locationConteiner) {
		System.out.println(":::::::  Iniciando Proceso de Carga de GOOGLE  ::::::::::");
		
		List<LocationTmp> newLocation = locationConteiner.getLocationTmp();
		if(newLocation==null){
			System.out.println("No hay nuevas Locaciones para realizar preguntas a Google, Saliendo de Process");
			return null;
		}else{
			
			
			
			DistanceTimeMatriz[] resp = askToGoogle.getDistanceByGoogle(locationConteiner);
			System.out.println(":::::::  TERMINANDO Proceso de Carga de GOOGLE  ::::::::::");
			return resp;
//			 for (LocationTmp locTmp: newLocation) {
//				 String LatLongNEWLocation=String.valueOf(locTmp.getLatitudeTmp())+","+String.valueOf(locTmp.getLongitudeTmp());
//				
//				 
//				 	for(Location loc: oldLocation){
//				 		 origins[contLoc]=LatLongNEWLocation; 
//				 		 String LatLongOLDLocation=String.valueOf(loc.getLatitude())+","+String.valueOf(loc.getLongitude());
//				 		 destiny[contLoc]=LatLongOLDLocation; 
//						 contLoc++;
//						 
//			    	  
//			       }
//				 	MatrizDisData[0][1]=askToGoogle.getDistanceByGoogle();
//				 	
//				 
//			    }
			 
		
			
			
		}
//		askToGoogle.getDistanceByGoogle();
		
		
		
	
		
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
