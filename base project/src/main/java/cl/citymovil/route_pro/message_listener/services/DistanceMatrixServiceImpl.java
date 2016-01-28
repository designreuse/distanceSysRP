package cl.citymovil.route_pro.message_listener.services;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.citymovil.route_pro.message_listener.dao.DistanceTimeDAO;
import cl.citymovil.route_pro.message_listener.dao.LocationTmpDAO;
import cl.citymovil.route_pro.message_listener.domain.DistanceTime;
import cl.citymovil.route_pro.message_listener.domain.DistanceTimeData;
import cl.citymovil.route_pro.message_listener.domain.Location;
import cl.citymovil.route_pro.message_listener.domain.LocationTmp;
import cl.citymovil.route_pro.solver.util.LocationContainer;
import cl.citymovil.route_pro.solver.util.RelationLocation;

@Service
public class DistanceMatrixServiceImpl implements DistanceMatrixService{

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	LocationContainer conteinerLocation;
	
	@Autowired
	AskToGoogle askToGoogle;
	
	@Autowired
	DistanceTimeDAO distanceTimeDAO;
	
	@Autowired
	LocationTmpDAO locationTmpDAO;


	@Override
	public LocationContainer Preprocess() {
		logger.info("\n**Inicio Preprocess**\n");
		//Busqueda de nuevas locaciones 
		//Busueda de las locaciones anteriores si es que encuentro nuevas locaciones, si no hay nuevas locaciones, retorno null.
		boolean resultContainer = conteinerLocation.LoadLocationConteiner();
		if(resultContainer==false){
			return null;
		}
		int sizeLocationTmpActual = conteinerLocation.getLocationTmp().size();
		logger.info("cantidad de LocationTmp"+sizeLocationTmpActual);
		
		logger.info("\n**FIN Preprocess**\n");
		return conteinerLocation;

	}

	@Override
	public  ArrayList<RelationLocation>  Process(LocationContainer locationConteiner) {
		logger.info("\n**Inicio Process**\n");
		
		List<LocationTmp> newLocation = locationConteiner.getLocationTmp();
		if(newLocation==null || newLocation.size()==0){
			
			logger.info("No hay nuevas Locaciones para realizar preguntas a Google, Saliendo de Process");
			logger.info("\n**FIN Process**\n");
			return null;
			
		}else{
			
			ArrayList<RelationLocation>  resp = askToGoogle.getDistanceByGoogle(locationConteiner);
			logger.info(":::::::  TERMINANDO Proceso de Carga de GOOGLE  ::::::::::");
			
			logger.info("\n**FIN Process**\n");
			return resp;
		
		}
	}
	@Override
	public void PostProcess(ArrayList<RelationLocation> relationLocationOfAllLocation) {
			logger.info("\n**Inicio PostProcess**\n");
		 for(int count=0; count < relationLocationOfAllLocation.size() ; count++){
			 RelationLocation relacion = relationLocationOfAllLocation.get(count);
			 LocationTmp locationTmp = new LocationTmp(relacion.getIdFirstLocation());
			 logger.info("\n ///////////// count: "+count);
			 logger.info("Datos Extraidos GoingDistance: "+relacion.getGoingDistance());
			 logger.info("Id Primer Location: "+relacion.getIdFirstLocation());
			 logger.info("Id Segundo Location: "+relacion.getIdSecondLocation());
			 logger.info("///////////// \n");
			 
			 
			 DistanceTime d = new DistanceTime(relacion.getIdFirstLocation(), relacion.getIdSecondLocation() ,relacion.getGoingDistance().longValue(),relacion.getGoingDuration().longValue());    
//			 locationTmp.setLocationId(relacion.getIdFirstLocation());
			try {
				locationTmpDAO.deleteTmpLocation(locationTmp);
				System.out.println("Se Ha Borrado el LocationTMP");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 distanceTimeDAO.persistDistanceTime(d);
			 
			 
			 
			// distanceTimeDAO.mergeDistanceTime(d);
			 
		 }	
	}
	


	void mergeLocation(Location loc) {
		// TODO Auto-generated method stub
		
	}


	 void persistLocation(Location loc) {
		// TODO Auto-generated method stub
		
	}

	 
	@Override
	public Map<Long, Map<Long, DistanceTimeData>> PreprocessAlpha(ArrayList<Location> listWithIdLocation) {
		Map<Long, Map<Long, DistanceTimeData>> distanceTimeMatrixHashMap = new HashMap<Long, Map<Long, DistanceTimeData>>();
		logger.info("\n**Inicio PreprocessAlpha**\n");
		List <DistanceTime> distanceTimeMatrixActual = distanceTimeDAO.getDistanceTimeOriginsOf(listWithIdLocation);
		if(distanceTimeMatrixActual!=null){
		
			for(int count=0; count < distanceTimeMatrixActual.size(); count++){
				
				Map<Long, DistanceTimeData> value = new HashMap<Long, DistanceTimeData>();
				DistanceTimeData distanceTimeData = new DistanceTimeData( (long)distanceTimeMatrixActual.get(count).getDistance(), (long) distanceTimeMatrixActual.get(count).getDuration());
				value.put(distanceTimeMatrixActual.get(count).getDestination(),distanceTimeData);
				
				distanceTimeMatrixHashMap.put(distanceTimeMatrixActual.get(count).getOrigin(), value);
				
			}
			return distanceTimeMatrixHashMap;
		}else{
			return null;
			
		}
		//Busqueda de nuevas locaciones 
		//Busueda de las locaciones anteriores si es que encuentro nuevas locaciones, si no hay nuevas locaciones, retorno null.
		
//		boolean resultContainer = conteinerLocation.MakeLocationContainerWithArrayLocation(listWithIdLocation);
//		if(resultContainer==false){
//			return null;
//		}else{
//			
//			return conteinerLocation;
//		}
		
		
	}

	@Override
	public void PostProcessAlpha(ArrayList<RelationLocation> relationLocationOfAllLocation) {
		logger.info("\n**Inicio PostProcessAlpha**\n");
		 for(int count=0; count < relationLocationOfAllLocation.size() ; count++){
			 RelationLocation relacion = relationLocationOfAllLocation.get(count);
//			 LocationTmp locationTmp = new LocationTmp(relacion.getIdFirstLocation());
			 logger.info("\n ///////////// count: "+count);
			 logger.info("Datos Extraidos GoingDistance: "+relacion.getGoingDistance());
			 logger.info("Id Primer Location: "+relacion.getIdFirstLocation());
			 logger.info("Id Segundo Location: "+relacion.getIdSecondLocation());
			 logger.info("///////////// \n");
			 
			 
			 DistanceTime d = new DistanceTime(relacion.getIdFirstLocation(), relacion.getIdSecondLocation() ,relacion.getGoingDistance().longValue(),relacion.getGoingDuration().longValue());    
			 distanceTimeDAO.persistDistanceTime(d);
			 
			 
			 
			// distanceTimeDAO.mergeDistanceTime(d);
			 
		 }	
		
	}

	

}
