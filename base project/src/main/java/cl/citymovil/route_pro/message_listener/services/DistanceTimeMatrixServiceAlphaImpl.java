package cl.citymovil.route_pro.message_listener.services;

import java.util.ArrayList;
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
import cl.citymovil.route_pro.solver.util.LocationContainer;
import cl.citymovil.route_pro.solver.util.RelationLocation;

@Service
public class DistanceTimeMatrixServiceAlphaImpl implements DistanceTimeMatrixServiceAlpha{
	
	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	DistanceTimeDAO distanceTimeDAO;

	/**Genera el HashMap de la lista de origenes a destino que actualmente se encuentran en la base de datos **/
	@Override
	public Map<Long, Map<Long, DistanceTimeDataComplete>> PreprocessAlpha(ArrayList<Location> listWithIdLocation) {
		Map<Long, Map<Long, DistanceTimeDataComplete>> distanceTimeMatrixHashMap = new HashMap<Long, Map<Long, DistanceTimeDataComplete>>();
		logger.info("\n**Inicio PreprocessAlpha**\n");
		List <DistanceTime> distanceTimeMatrixOrigin=distanceTimeDAO.getDistanceTimeOriginsOf(listWithIdLocation);
		Map<Long, DistanceTimeDataComplete> valueOrigin = new HashMap<Long, DistanceTimeDataComplete>();
		boolean changeFlag;
		changeFlag=false;
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
					changeFlag=true;
				
				}else{
					System.out.println("\n (else) igual- guardando valueOrigin\n");
					valueOrigin.put(distanceTimeMatrixOrigin.get(count).getDestination(), distanceTimeDataComplete);
					changeFlag=false;
				}
				
//				valueOrigin.keySet();
//				valueOrigin.entrySet();
				
//				distanceTimeMatrixHashMap.put(distanceTimeMatrixOrigin.get(count).getOrigin(), valueOrigin);
				//DistanceTimeDataComplete valueKeyInMap = valueOrigin.get(distanceTimeMatrixOrigin.get(count).getOrigin());
				//Map<Long, DistanceTimeDataComplete> origenKeyIngresado = distanceTimeMatrixHashMap.get(distanceTimeMatrixOrigin.get(count).getOrigin());
//				logger.info("####Agregado en HASHMAP ORIGIN=>Origen:"+distanceTimeMatrixOrigin.get(count).getOrigin()
//						+"Destino:"+distanceTimeMatrixOrigin.get(count).getDestination()+"\n distancia going:"+distanceTimeMatrixOrigin.get(count).getDistance()+
//						"duracion going:"+distanceTimeMatrixOrigin.get(count).getDuration());
				
				//distanceTimeMatrixHashMap.put(distanceTimeMatrixOrigin.get(count).getOrigin(), valueOrigin);
				//distanceTimeMatrixHashMap.putAll(m);
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
//		if(distanceTimeMatrixDestiny!=null){
//			
//			for(int count=0; count < distanceTimeMatrixDestiny.size(); count++){
//				
//				Map<Long, DistanceTimeDataComplete> valueDestiny = new HashMap<Long, DistanceTimeDataComplete>();
//				DistanceTimeDataComplete distanceTimeDataCompleteDestiny = new DistanceTimeDataComplete( );
//				distanceTimeDataCompleteDestiny.setCommingDistance((long)distanceTimeMatrixDestiny.get(count).getDistance());
//				distanceTimeDataCompleteDestiny.setCommingTime((long)distanceTimeMatrixDestiny.get(count).getDuration());
//				//(long)distanceTimeMatrixDestiny.get(count).getDistance(), (long) distanceTimeMatrixDestiny.get(count).getDuration()
//				valueDestiny.put(distanceTimeMatrixDestiny.get(count).getOrigin(),distanceTimeDataCompleteDestiny);
//				
//				distanceTimeMatrixHashMap.put(distanceTimeMatrixDestiny.get(count).getDestination(), valueDestiny);
//				logger.info("####Agregado en hashMAP DESTINY=>Origen:"+distanceTimeMatrixDestiny.get(count).getOrigin()
//						+"Destino:"+distanceTimeMatrixDestiny.get(count).getDestination());
//				
//			}
//			//return distanceTimeMatrixHashMap;
//		}else{
//			logger.info("****No hay ningun dato de la matriz de distancia de Destino a Origen  ***************");
//			//return null;
//			
//			
//		}
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
	public ArrayList<LocationContainer> PreprocessBeta(
			Map<Long, Map<Long, DistanceTimeDataComplete>> distanceTimeHashMap) {
		System.out.println("\n--------------------------------------------\n");
		System.out.println("\ndistanceTimeHashMap\n"+distanceTimeHashMap.size());
		 
		for( Entry<Long, Map<Long, DistanceTimeDataComplete>> b : distanceTimeHashMap.entrySet()) {
		       Long origenKey = b.getKey();
		       Map<Long, DistanceTimeDataComplete> destinosOrigen = b.getValue();
		       System.out.println("Primer for Orgen:"+origenKey+"\n Cantidad de Destinos:"+destinosOrigen.size());
		       
		       for(Entry<Long, DistanceTimeDataComplete> dest : destinosOrigen.entrySet()){
		    	   Long destinyKey = dest.getKey();
		    	   DistanceTimeDataComplete destinyData = dest.getValue();
		    	   System.out.println("///////////////////////////\n");
		    	   System.out.println("Segundo for Orgen:"+origenKey+"\n destino:"+destinyKey);
		    	   System.out.println("distancia de ida:"+destinyData.getGoingDistance());
		    	   System.out.println("tiempo de ida:"+destinyData.getGoingTime());
		    	   System.out.println("///////////////////////////\n");
		       }
		    }
//			for(Entry<Long, Map<Long, DistanceTimeDataComplete>> distTmeDataCompl : distanceTimeHashMap.entrySet()){//.entrySet()){
//				Long key = distTmeDataCompl.getKey();
//				Map<Long, DistanceTimeDataComplete> value = distTmeDataCompl.getValue();
//				System.out.println("value size"+value.size());
//				System.out.println("Esta es La Key que arroja:"+key+"\n y su contenido es:"+value.toString());
//				
//				for (Entry<Long, Map<Long, DistanceTimeDataComplete>> valueDestiny : ( distTmeDataCompl).entrySet()) {
//					Long keyDestiny = valueDestiny.getKey();
//					Map<Long, DistanceTimeDataComplete> value2 = valueDestiny.getValue();
//					System.out.println("key Origen:"+key+" keyDestiny:"+keyDestiny);
//					System.out.println("Valor Distancia de Ida:"+value2.get(keyDestiny).getGoingDistance());
//					//System.out.println("Valor Distancia de Vuelta:"+value2.getCommingDistance());
//					System.out.println("Valor Duracion de Ida:"+value2.get(keyDestiny).getGoingTime());
//					//System.out.println("Valor Duracion de Vuelta:"+value2.getCommingTime());
//					
//					
//				}
//				
//			}
		return null;
	}

	@Override
	public ArrayList<RelationLocation> Process(LocationContainer locationConteiner) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void PostProcessAlpha(ArrayList<RelationLocation> relationLocation) {
		// TODO Auto-generated method stub
		
	}



}
