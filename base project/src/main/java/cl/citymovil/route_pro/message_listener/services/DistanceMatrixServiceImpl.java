package cl.citymovil.route_pro.message_listener.services;


import java.util.ArrayList;
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
import cl.citymovil.route_pro.solver.util.RelationLocation;

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
		
//		System.out.println("en Preprocess %%%%% ");
		
		conteinerLocation.LoadLocationConteiner();
		
		return conteinerLocation;

	}

	@Override
	public  ArrayList<RelationLocation>  Process(LocationContainer locationConteiner) {
//		System.out.println(":::::::  Iniciando Proceso de Carga de GOOGLE  ::::::::::");
		
		List<LocationTmp> newLocation = locationConteiner.getLocationTmp();
		if(newLocation==null){
			
//			System.out.println("No hay nuevas Locaciones para realizar preguntas a Google, Saliendo de Process");
			return null;
			
		}else{
			
			ArrayList<RelationLocation>  resp = askToGoogle.getDistanceByGoogle(locationConteiner);
//			System.out.println(":::::::  TERMINANDO Proceso de Carga de GOOGLE  ::::::::::");
			
			return resp;
		
		}
//		askToGoogle.getDistanceByGoogle();
	}

	//debo considerar que el largo de distanceTimeMatrixUtility es variable, para poder modificar 
	//la cantidad de string en el array que contiene tanto el origen como el destino en process
	@Override
	public void PostProcess(ArrayList<RelationLocation> relationLocationOfAllLocation) {
//		 System.out.println("///////////iniciando el PostProcess//////////");
//		 System.out.println("///////////AUN SIN IMPLEMENTAR//////////"+relationLocationOfAllLocation.size());
		 
		 for(int count=0; count < relationLocationOfAllLocation.size() ; count++){
			 RelationLocation relacion = relationLocationOfAllLocation.get(count);
//			 System.out.println("///////////// count: "+count);
//			 System.out.println("Datos Extraidos GoingDistance: "+relacion.getGoingDistance());
//			 System.out.println("Datos Extraidos GoingDistance: "+relacion.getFirstLocation()[0]+":"+relacion.getFirstLocation()[1]);
//			 System.out.println("Datos Extraidos GoingDistance: "+relacion.getSecondLocation()[0]+":"+relacion.getSecondLocation()[1]);
//			 System.out.println("/////////////");
		 }
		 
		 

		
	}


	void mergeLocation(Location loc) {
		// TODO Auto-generated method stub
		
	}


	 void persistLocation(Location loc) {
		// TODO Auto-generated method stub
		
	}

}
