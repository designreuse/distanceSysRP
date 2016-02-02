package cl.citymovil.route_pro.message_listener.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.math3.util.OpenIntToFieldHashMap.Iterator;
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
	public Map<Long, Map<Long, DistanceTimeData>> PreprocessAlpha(ArrayList<Location> listWithIdLocation) {
		Map<Long, Map<Long, DistanceTimeData>> distanceTimeMatrixHashMapOrigin = new HashMap<Long, Map<Long, DistanceTimeData>>();
		logger.info("\n**Inicio PreprocessAlpha AQUIIIIIII**\n");
		
		
		List <DistanceTime> distanceTimeMatrix=distanceTimeDAO.getDistanceTimeOriginsOf(listWithIdLocation);
		Map<Long, DistanceTimeData> distanceTimeMatrixHashMapDestiny = new HashMap<Long, DistanceTimeData>();
		/**Generación del HashMap de la matriz de distancia para el Origen a Destino**/
		if(distanceTimeMatrix!=null){
		
			/**COMPLETO EL HASHMAP DE TODOS LOS DATOS DE LA MATRIZ DE DISTANCIA DESDE LOS ORGENES A LOS DESTINOS**/
//			for(int count=0; count < distanceTimeMatrixOrigin.size(); count++){
			Long idOrigen= null;
			for(int count=0; count < distanceTimeMatrix.size(); count++){
				System.out.println("ini for<<<>>><<<<>>count"+count+" size->distanceTimeMatrixOrigin:"+distanceTimeMatrix.size());
				
				DistanceTimeData distanceTimeData = new DistanceTimeData((long)distanceTimeMatrix.get(count).getDistance(),(long)distanceTimeMatrix.get(count).getDuration());
//				distanceTimeData.setGoingDistance((long)distanceTimeMatrixOrigin.get(count).getDistance());
//				distanceTimeData.setGoingTime((long) distanceTimeMatrixOrigin.get(count).getDuration());
				
				if(idOrigen==null){
					idOrigen=(Long)distanceTimeMatrix.get(count).getOrigin();
					System.out.println("Primer ingreso");
				}
				if((Long)distanceTimeMatrix.get(count).getOrigin()!=idOrigen){
					System.out.println("\n (if) no igual- guardando valueOrigin\n");
					distanceTimeMatrixHashMapOrigin.put(idOrigen, distanceTimeMatrixHashMapDestiny);
					
					distanceTimeMatrixHashMapDestiny=new HashMap<Long, DistanceTimeData>();
					
					idOrigen=distanceTimeMatrix.get(count).getOrigin(); 
					distanceTimeMatrixHashMapDestiny.put((Long)distanceTimeMatrix.get(count).getDestination(), distanceTimeData);
					System.out.println("\n generando nuevo valueOrigin\n");
				
				}else{
					distanceTimeMatrixHashMapDestiny.put((Long)distanceTimeMatrix.get(count).getDestination(), distanceTimeData);
					System.out.println("\n (else) igual- ya se ha guardando el destiny\n");
//					distanceTimeMatrixHashMapDestiny.put(distanceTimeMatrix.get(count).getDestination(), distanceTimeDataComplete);
					
				}

				System.out.println("\nfin for<<<>>><<<<>>>>>><<<<<<<<<>>>>>>>>>>>>>>>>>");
			}
			if(distanceTimeMatrixHashMapDestiny.isEmpty()!=true){
				System.out.println("\n ### entro al guardado de cola\n");
				distanceTimeMatrixHashMapOrigin.put(idOrigen, distanceTimeMatrixHashMapDestiny);
			}
			System.out.println("OJOOOO===>distanceTimeMatrixHashMap.size"+distanceTimeMatrixHashMapOrigin.size());
			//return distanceTimeMatrixHashMap;
		}else{
			logger.info("****No hay ningun dato de la matriz de distancia de Origen a Destino ***************");
			//return null;
			
		}
		/**Generación del HashMap de la matriz de distancia para el Destino al Origen **/

		if(distanceTimeMatrixHashMapOrigin.isEmpty()){
			logger.info("**** El HASHMAP esta VACIO, HAY QUE CALCULARLO COMPLETO ***************");
			distanceTimeMatrixHashMapOrigin.clear();
			 return distanceTimeMatrixHashMapOrigin;
			
		}else{
			logger.info("\n **** ENVIANDO EL HASHMAP ORIGEN AL CONTROLADOR ***************  \n");
			return distanceTimeMatrixHashMapOrigin;
		}
	
		
		
	}
	
	
	
	/**En base al hashMap generado en ProprocessAlpha, genera las listas de string[] para realizar las consultas a google**/
	@Override
	public ArrayList<LocationContainerForGoogleAsk> PreprocessBeta(	
		Map<Long, Map<Long, DistanceTimeData>> distanceTimeHashMap, ArrayList <Location> arrayWithIdLocation) {
		ArrayList <LocationContainerForGoogleAsk> listOfLocationContainerForGoogleAsk = new ArrayList <LocationContainerForGoogleAsk>();
		LocationContainerForGoogleAsk locationContainerForGoogleAsk = new LocationContainerForGoogleAsk();
		ArrayList <Location> listLocationOrigen= new ArrayList <Location>(); 
		ArrayList <Location> listLocationDestiny= new ArrayList <Location>();  
//		System.out.println("////////DESCRIPCION DEL CONTENIDO DEL HASHMAP/////// ");
//		for (Map.Entry<Long, Map<Long, DistanceTimeData>> outer : distanceTimeHashMap.entrySet()) {
//		    System.out.println("Key: " + outer.getKey() +  "\n");
//		   for(Entry<Long, DistanceTimeData> algo : distanceTimeHashMap.get(outer.getKey()).entrySet() ){
//			   System.out.println("Key = " + algo.getKey() + ", Value = " + algo.getValue().distance );
//		   }
////		    
//		 }
		for(int countOrigen=0; countOrigen < arrayWithIdLocation.size() ; countOrigen++){
			Long idOrigen; 
			idOrigen = arrayWithIdLocation.get(countOrigen).getLocationId();
			/**SI NO ESTA EL ORIGEN, HAY QUE ALMACENAR EL ORIGEN CON TODOS LOS POSIBLES DESTINOS**/
			if(distanceTimeHashMap.get(idOrigen)==null){
				ArrayList<Location> listLocationDestinyTmp = new ArrayList <Location>(); 
				listLocationDestinyTmp.addAll(arrayWithIdLocation);
				listLocationDestinyTmp.remove(arrayWithIdLocation.get(countOrigen));
				logger.info("(desc HashMap) No hay definición del punto"+idOrigen+", va con todos los destinos");
				 listLocationOrigen.add(arrayWithIdLocation.get(countOrigen));
				 
				 listLocationDestiny.addAll(listLocationDestinyTmp);
				 locationContainerForGoogleAsk.setLocationOrigin(listLocationOrigen);
				 locationContainerForGoogleAsk.setLocationDestiny(listLocationDestiny);
				 listOfLocationContainerForGoogleAsk.add(locationContainerForGoogleAsk);
				 listLocationOrigen= new ArrayList <Location>(); 
				 listLocationDestiny= new ArrayList <Location>();
				 listLocationDestinyTmp= new ArrayList <Location>();
				 locationContainerForGoogleAsk=new LocationContainerForGoogleAsk(); 
			}else{
				/**SI ESTA EL ORIGEN, HAY QUE ALMACENAR SOLO LOS DESTINOS QUE NO SE ENCUENTRAN**/
					for(int countDestiny=0; countDestiny < arrayWithIdLocation.size() ; countDestiny++){
						Long idDestiny;
						idDestiny= arrayWithIdLocation.get(countDestiny).getLocationId();
						if(idOrigen!=idDestiny){
							DistanceTimeData dataLocation;
							dataLocation = distanceTimeHashMap.get(idOrigen).get(idDestiny);
						
							if(dataLocation==null){
								System.out.println("El dato no se encuentra!!!!!!! [idOrigen:"+idOrigen+"][idDestiny:"+idDestiny+"]"); 
								listLocationDestiny.add(arrayWithIdLocation.get(countDestiny));	 
							}else{
								 
								System.out.println("Estos son los valores encontrados \n distance:"+dataLocation.distance+" time:"+dataLocation.time);	
							}
						}
					}
					if(listLocationDestiny.isEmpty()!=true){
						listLocationOrigen.add(arrayWithIdLocation.get(countOrigen));
						 locationContainerForGoogleAsk.setLocationOrigin(listLocationOrigen);
						 locationContainerForGoogleAsk.setLocationDestiny(listLocationDestiny);
						 listOfLocationContainerForGoogleAsk.add(locationContainerForGoogleAsk);
						 listLocationOrigen= new ArrayList <Location>(); 
						 listLocationDestiny= new ArrayList <Location>();
						 locationContainerForGoogleAsk=new LocationContainerForGoogleAsk(); 
						
					}
			}
		
		}

		if(listOfLocationContainerForGoogleAsk.isEmpty()){
			System.out.println("Entro AL EMPTY");
			return null;
			
		}else{
			System.out.println("RETURN con listLocationContainer CON CARNE??, MOSTRANDO TODO::");
		
			return listOfLocationContainerForGoogleAsk;
		}

	}

	@Override
	public ArrayList<RelationLocation> Process(LocationContainerForGoogleAsk locationContainerForGoogle) {
logger.info("\n**Inicio Process**\n");
List<Location> listOriginLocation = locationContainerForGoogle.getLocationOrigin();

List<Location> listDestLocation = locationContainerForGoogle.getLocationDestiny();

if(listDestLocation==null || listDestLocation.size()==0){
	for(int count=0; count < listOriginLocation.size(); count++){
		logger.info("Imprimiendo Origen en Process:"+listOriginLocation.get(count).getLocationId());
	}
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
		logger.trace("\n");

		if(relationLocationOfAllLocation!=null){
			logger.trace("\n**Inicio PostProcess**\n"+relationLocationOfAllLocation.size() );			
		 for(int count=0; count < relationLocationOfAllLocation.size() ; count++){
			 RelationLocation relacion = relationLocationOfAllLocation.get(count);
			 logger.trace("\n ///////////// count: "+count);
			 logger.trace("Datos Extraidos GoingDistance: "+relacion.getGoingDistance());
			 logger.trace("Id Primer Location: "+relacion.getIdFirstLocation());
			 logger.trace("Id Segundo Location: "+relacion.getIdSecondLocation());
			 logger.trace("///////////// \n");
			 
			 
			 DistanceTime d = new DistanceTime(relacion.getIdFirstLocation(), relacion.getIdSecondLocation() ,relacion.getGoingDistance().longValue(),relacion.getGoingDuration().longValue());    
//			 locationTmp.setLocationId(relacion.getIdFirstLocation());
			
			 distanceTimeDAO.persistDistanceTime(d);
			// distanceTimeDAO.mergeDistanceTime(d);
			 
		 }
		}else{
			logger.trace("\n(PostProcessAlpha)**relationLocationOfAllLocation==null**\n");
		}
			logger.trace("\n (PostProcessAlpha)**Saliendo de PostProcess**\n");
		
	}



}
