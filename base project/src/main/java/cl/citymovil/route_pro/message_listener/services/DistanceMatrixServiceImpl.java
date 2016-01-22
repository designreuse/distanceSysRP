package cl.citymovil.route_pro.message_listener.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.maps.model.DistanceMatrix;

import cl.citymovil.route_pro.message_listener.domain.DistanceTime;
import cl.citymovil.route_pro.message_listener.domain.DistanceTimeMatriz;
import cl.citymovil.route_pro.message_listener.domain.Location;
import cl.citymovil.route_pro.message_listener.domain.LocationTmp;
import cl.citymovil.route_pro.solver.util.DistanceTimeMatrixUtility;
import cl.citymovil.route_pro.solver.util.LocationContainer;

@Service
public class DistanceMatrixServiceImpl implements DistanceMatrixService{

	
	@Autowired
	LocationContainer conteinerLocation;
	
	@Autowired
	AskToGoogle askToGoogle;

	@Override
	public LocationContainer Preprocess() {
		//Busqueda de nuevas locaciones 
		//Busueda de las locaciones anteriores si es que encuentro nuevas locaciones, si no hay nuevas locaciones, retorno null.
		
		System.out.println("en Preprocess %%%%% ");
		
		conteinerLocation.LoadLocationConteiner();
		
		return conteinerLocation;

	}

	@Override
	public  DistanceTimeMatrixUtility[]  Process(LocationContainer locationConteiner) {
		System.out.println(":::::::  Iniciando Proceso de Carga de GOOGLE  ::::::::::");
		
		List<LocationTmp> newLocation = locationConteiner.getLocationTmp();
		if(newLocation==null){
			
			System.out.println("No hay nuevas Locaciones para realizar preguntas a Google, Saliendo de Process");
			return null;
			
		}else{
			
			DistanceTimeMatrixUtility[]  resp = askToGoogle.getDistanceByGoogle(locationConteiner);
			System.out.println(":::::::  TERMINANDO Proceso de Carga de GOOGLE  ::::::::::");
			
			return resp;
		
		}
//		askToGoogle.getDistanceByGoogle();
	}

	@Override
	public void PostProcess(DistanceTimeMatrixUtility[]  saveDistanceTime) {
		// TODO Auto-generated method stub
		
	}


	void mergeLocation(Location loc) {
		// TODO Auto-generated method stub
		
	}


	 void persistLocation(Location loc) {
		// TODO Auto-generated method stub
		
	}

}
