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

	//debo considerar que el largo de distanceTimeMatrixUtility es variable, para poder modificar 
	//la cantidad de string en el array que contiene tanto el origen como el destino en process
	@Override
	public void PostProcess(DistanceTimeMatrixUtility[]  distanceTimeMatrixUtility) {
		 System.out.println("///////////iniciando el PostProcess//////////");
		Integer cantidadDeSolicitudesGoogle=distanceTimeMatrixUtility.length;
		int countArraySolicitudGoogle=0;
		for(countArraySolicitudGoogle=0; countArraySolicitudGoogle < cantidadDeSolicitudesGoogle; countArraySolicitudGoogle++){
			List<Long[]> idOriginsDestiny = distanceTimeMatrixUtility[countArraySolicitudGoogle].getIdOriginDestiny();
			DistanceMatrix distanceDuration = distanceTimeMatrixUtility[countArraySolicitudGoogle].getDistanceMatrix();
			 String[] origins = distanceTimeMatrixUtility[countArraySolicitudGoogle].getOrigen();
			 String[] destinies = distanceTimeMatrixUtility[countArraySolicitudGoogle].getDestiny();
			 
			 System.out.println("//////////////////////////////////////");
					 System.out.println("Origins: "+origins.length+" - Destiny: "+destinies.length+" idOriginsDes:"+idOriginsDestiny);
			 System.out.println("//////////////////////////////////////");
			 if(origins.length==destinies.length && origins.length==idOriginsDestiny.size() && destinies.length==idOriginsDestiny.size()){
				 
				 System.out.println("//////////////////////////////////////");
				 System.out.println("//////////////////////////////////////");
				 System.out.println("///////////////LOS TRES ARREGLOS TIENEN EL MISMO LARGO///////////////////////");
				 System.out.println("//////////////////////////////////////");
				 System.out.println("////////////////TODO OK//////////////////////");
				 System.out.println("//////////////////////////////////////");
			 }
			for(int count=0; count< distanceTimeMatrixUtility.length; count++){
				
				
				
			}
		}
		
		
		
		
	}


	void mergeLocation(Location loc) {
		// TODO Auto-generated method stub
		
	}


	 void persistLocation(Location loc) {
		// TODO Auto-generated method stub
		
	}

}
