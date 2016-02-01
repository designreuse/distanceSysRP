package cl.citymovil.route_pro.message_listener.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.citymovil.route_pro.message_listener.dao.DistanceTimeDAO;
import cl.citymovil.route_pro.message_listener.domain.DistanceTime;
import cl.citymovil.route_pro.message_listener.domain.DistanceTimeData;
import cl.citymovil.route_pro.message_listener.domain.DistanceTimeDataComplete;
import cl.citymovil.route_pro.message_listener.domain.Location;
import cl.citymovil.route_pro.message_listener.domain.LocationTmp;
import cl.citymovil.route_pro.solver.util.LocationContainer;
import cl.citymovil.route_pro.solver.util.LocationContainerForGoogleAsk;
import cl.citymovil.route_pro.solver.util.RelationLocation;

@Service
public class DistanceTimeMatrixServiceAlphaImpl implements DistanceTimeMatrixServiceAlpha{
	
	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	DistanceTimeDAO distanceTimeDAO;
	
	@Autowired
	AskToGoogle askToGoogle;
	  

	/**Genera el HashMap de la lista de origenes a destino que actualmente se encuentran en la base de datos **/
	@Override
	public Map<Long, Map<Long, DistanceTimeDataComplete>> PreprocessAlpha(ArrayList<Location> listWithIdLocation) {
		Map<Long, Map<Long, DistanceTimeDataComplete>> distanceTimeMatrixHashMap = new HashMap<Long, Map<Long, DistanceTimeDataComplete>>();
		logger.info("\n**Inicio PreprocessAlpha**\n");
		List <DistanceTime> distanceTimeMatrixOrigin=distanceTimeDAO.getDistanceTimeOriginsOf(listWithIdLocation);
		Map<Long, DistanceTimeDataComplete> valueOrigin = new HashMap<Long, DistanceTimeDataComplete>();
		
		
		//List <DistanceTime> distanceTimeMatrixDestiny=distanceTimeDAO.getDistanceTimeDestiniesOf(listWithIdLocation);
		/**Generación del HashMap de la matriz de distancia para el Origen a Destino**/
		if(distanceTimeMatrixOrigin!=null){
		
			/**COMPLETO EL HASHMAP DE TODOS LOS DATOS DE LA MATRIZ DE DISTANCIA DESDE LOS ORGENES A LOS DESTINOS**/
//			for(int count=0; count < distanceTimeMatrixOrigin.size(); count++){
			Long idOrigen= null;
			for(int count=0; count < distanceTimeMatrixOrigin.size(); count++){
				System.out.println("ini for<<<>>><<<<>>count"+count+" size->distanceTimeMatrixOrigin:"+distanceTimeMatrixOrigin.size());
				
				DistanceTimeDataComplete distanceTimeDataComplete = new DistanceTimeDataComplete( );
				distanceTimeDataComplete.setGoingDistance((long)distanceTimeMatrixOrigin.get(count).getDistance());
				distanceTimeDataComplete.setGoingTime((long) distanceTimeMatrixOrigin.get(count).getDuration());
				valueOrigin.put(distanceTimeMatrixOrigin.get(count).getDestination(),distanceTimeDataComplete);
				if(idOrigen==null){
					idOrigen=distanceTimeMatrixOrigin.get(count).getOrigin();
					System.out.println("Primer ingreso");
				}
				if(distanceTimeMatrixOrigin.get(count).getOrigin()!=idOrigen){
					System.out.println("\n (if) no igual- guardando valueOrigin\n");
					distanceTimeMatrixHashMap.put(distanceTimeMatrixOrigin.get(count).getDestination(), valueOrigin);
					
					valueOrigin.clear();
					
					idOrigen=distanceTimeMatrixOrigin.get(count).getOrigin(); 
					valueOrigin.put(distanceTimeMatrixOrigin.get(count).getDestination(), distanceTimeDataComplete);
					System.out.println("\n generando nuevo valueOrigin\n");
				
				}else{
					System.out.println("\n (else) igual- guardando valueOrigin\n");
					valueOrigin.put(distanceTimeMatrixOrigin.get(count).getDestination(), distanceTimeDataComplete);
					
				}

				System.out.println("\nfin for<<<>>><<<<>>>>>><<<<<<<<<>>>>>>>>>>>>>>>>>");
			}
//			if(changeFlag==true){
				System.out.println("\n ### entro al guardado de cola\n");
				distanceTimeMatrixHashMap.put(idOrigen, valueOrigin);
//			}
			System.out.println("OJOOOO===>distanceTimeMatrixHashMap.size"+distanceTimeMatrixHashMap.size());
			//return distanceTimeMatrixHashMap;
		}else{
			logger.info("****No hay ningun dato de la matriz de distancia de Origen a Destino ***************");
			//return null;
			
		}
		/**Generación del HashMap de la matriz de distancia para el Destino al Origen **/

		if(distanceTimeMatrixHashMap.isEmpty()){
			logger.info("**** El HASHMAP esta VACIO, HAY QUE CALCULARLO COMPLETO ***************");
			return null;
			
		}else{
			logger.info("\n **** ENVIANDO EL HASHMAP ORIGEN AL CONTROLADOR ***************  \n");
			return distanceTimeMatrixHashMap;
		}
	
		
		
	}
	
	/**En base al hashMap generado en ProprocessAlpha, genera las listas de string[] para realizar las consultas a google**/
	@Override
	public ArrayList<LocationContainerForGoogleAsk> PreprocessBeta(	
		Map<Long, Map<Long, DistanceTimeDataComplete>> distanceTimeHashMap, ArrayList <Location> arrayWithIdLocation) {
		ArrayList <LocationContainerForGoogleAsk> listOfLocationContainerForGoogleAsk = new ArrayList <LocationContainerForGoogleAsk>();
//		List <LocationContainer> listLocationContainer= new ArrayList <LocationContainer>();
		ArrayList <Location> listLocationOrigen= new ArrayList <Location>(); 
		
	 
//		System.out.println("\n--------------------------------------------\n");
//		System.out.println("\ndistanceTimeHashMap\n"+distanceTimeHashMap.size());
		 
		for(int countOrigen=0; countOrigen < arrayWithIdLocation.size(); countOrigen++){
			  Map<Long, DistanceTimeDataComplete> origenMap = distanceTimeHashMap.get(arrayWithIdLocation.get(countOrigen).getLocationId());
			  ArrayList <Location> listLocationDestiny= new ArrayList <Location>();
			  Location locationOrigin= new Location(arrayWithIdLocation.get(countOrigen));
			 
			  
			  for(int countDestino=0; countDestino < arrayWithIdLocation.size(); countDestino++){
				  /** SI ESTA EL DATO EN EL HASHMAP NO HACE NADA **/    
					try{
						origenMap.containsKey(arrayWithIdLocation.get(countDestino).getLocationId());
						
						/** SI NO ESTA EL DATO, HACE LO SGTE **/  
					}catch(Exception e){
						listLocationDestiny.add(arrayWithIdLocation.get(countDestino));
						
//							
					}
			  }	
			  if(listLocationDestiny.isEmpty()!=true){
				  listLocationOrigen.add(locationOrigin);
				  LocationContainerForGoogleAsk locationContainerForGoogleAsk = new LocationContainerForGoogleAsk(listLocationOrigen, listLocationDestiny);
				  System.out.println("( listLocationDestiny.isEmpty()!=true )GUARDANDO EN EL LISTLOCATIONCONTAINER"+countOrigen+" Locaiton ID"+locationOrigin.getLocationId());
				  
				  listOfLocationContainerForGoogleAsk.add(locationContainerForGoogleAsk);
				  listLocationOrigen= new ArrayList <Location>();
//				  locationContainerTmp.setLocation(listLocationOrigen);
//				  locationContainerTmp.setLocation(listLocationDestino);
				  
//				  for(int x=0; x < locationContainerTmp.getLocation().size(); x++){
//					 long origenID = locationContainerTmp.getLocation().get(x).getLocationId();
//					 System.out.println("origenID( listLocationDestino.isEmpty()!=true ) Imprimiendo lo guardado"); 
//					  System.out.println("id del origen:"+origenID);
//					  
//					  
//				  }
//				  for(int y=0; y < locationContainerTmp.getLocationTmp().size(); y++){
//					  
//					 long destID = locationContainerTmp.getLocationTmp().get(y).getLocationId();
//					 System.out.println("destID( listLocationDestino.isEmpty()!=true ) Imprimiendo lo guardado"); 
//					 System.out.println("id del destino:"+destID);
//					  
//					  
//				  }
				  
//				  locationContainerTmp.listTmpLocation();
//				  locationContainerTmp.listLocation();
//				  listLocationContainer.add(locationContainerTmp);
//				 
//				  listLocationOrigen.clear();
//				  listLocationDestino.clear();
//				  locationContainerTmp = new LocationContainer(); 
			  }
		}
		if(listOfLocationContainerForGoogleAsk.isEmpty()){
			System.out.println("Entro AL EMPTY");
			return null;
			
		}else{
			System.out.println("RETURN con listLocationContainer CON CARNE??, MOSTRANDO TODO::");
			List<LocationTmp> newLocationContainer = new ArrayList<LocationTmp>();
			List<Location> oldLocationContainer = new ArrayList <Location>();
//			for(int i=0; i < listLocationContainer.size(); i++){
//				 newLocationContainer = listLocationContainer.get(i).getLocationTmp();
//				 oldLocationContainer = listLocationContainer.get(i).getLocation();
//				
//				System.out.println("/////INICIO ULTIMO FOR() /////"+listLocationContainer.get(i).getLocation().size());
//				 for(int x=0; x < listLocationContainer.get(i).getLocation().size(); x++){
//					  System.out.println("fffff Imprimiendo lo guardado");
//					 long origenID = listLocationContainer.get(i).getLocation().get(x).getLocationId();
//					  System.out.println("id del origen:"+listLocationContainer.get(i).getLocation().get(x).getLocationId());
//					  
//					  
//				  }
//				 System.out.println("/////INICIO ULTIMO FOR() /////"+listLocationContainer.get(i).getLocationTmp().size());
//				  for(int y=0; y < listLocationContainer.get(i).getLocationTmp().size(); y++){
//					  System.out.println("ffffffffffImprimiendo lo guardado, y:"+y);
//					 long destID = listLocationContainer.get(i).getLocationTmp().get(y).getLocationId();
//					  System.out.println("id del destino:"+listLocationContainer.get(i).getLocationTmp().get(y).getLocationId()+"  destID"+destID);
//					  
//					  
//				  }
//					System.out.println("///// FIN ULTIMO FOR() /////");
//				
//			}
			return listOfLocationContainerForGoogleAsk;
		}

	}

	@Override
	public ArrayList<RelationLocation> Process(LocationContainerForGoogleAsk locationContainerForGoogle) {
logger.info("\n**Inicio Process**\n");
List<Location> listOriginLocation = locationContainerForGoogle.getLocationOrigin();

List<Location> listDestLocation = locationContainerForGoogle.getLocationDestiny();

if(listDestLocation==null || listDestLocation.size()==0){
	logger.info("No hay nuevas Locaciones para realizar preguntas a Google, Saliendo de Process");
	logger.info("\n**FIN Process**\n");
	return null;
	
}else{
	ArrayList<RelationLocation>  resp = askToGoogle.getDistanceByGoogleAlpha(locationContainerForGoogle);
	
	logger.info(":::::::");
	logger.info(":::::::");
	logger.info(":::::::");
	logger.info(":::::::");
	logger.info(":::::::");
	logger.info(":::::::");
	for(RelationLocation re: resp){
		System.out.println("imprimiendo el resultado de askToGoogle. \n getGoingDistance"
	+re.getGoingDistance()
	+"\n getGoingDuration:"+re.getGoingDuration()
	+"id ORIGEN:"+re.getIdFirstLocation()
	+"ID DESTINO:"+re.getIdSecondLocation());
		
	}
	logger.info(":::::::  TERMINANDO Proceso de Carga de GOOGLE  ::::::::::");
	
	logger.info("\n**FIN Process**\n");
	return resp;
}
	}

	@Override
	public void PostProcessAlpha(ArrayList<RelationLocation> relationLocationOfAllLocation) {
		logger.info("\n**Inicio PostProcess**\n");
		 for(int count=0; count < relationLocationOfAllLocation.size() ; count++){
			 RelationLocation relacion = relationLocationOfAllLocation.get(count);
			 logger.info("\n ///////////// count: "+count);
			 logger.info("Datos Extraidos GoingDistance: "+relacion.getGoingDistance());
			 logger.info("Id Primer Location: "+relacion.getIdFirstLocation());
			 logger.info("Id Segundo Location: "+relacion.getIdSecondLocation());
			 logger.info("///////////// \n");
			 
			 
			 DistanceTime d = new DistanceTime(relacion.getIdFirstLocation(), relacion.getIdSecondLocation() ,relacion.getGoingDistance().longValue(),relacion.getGoingDuration().longValue());    
//			 locationTmp.setLocationId(relacion.getIdFirstLocation());
			
			 distanceTimeDAO.persistDistanceTime(d);
			 
			 
			 
			// distanceTimeDAO.mergeDistanceTime(d);
			 
		 }
			logger.info("\n**Saliendo de PostProcess**\n");
		
	}



}
