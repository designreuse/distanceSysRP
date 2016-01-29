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

	@Override
	public Map<Long, Map<Long, DistanceTimeDataComplete>> PreprocessAlpha(ArrayList<Location> listWithIdLocation) {
		Map<Long, Map<Long, DistanceTimeDataComplete>> distanceTimeMatrixHashMap = new HashMap<Long, Map<Long, DistanceTimeDataComplete>>();
		logger.info("\n**Inicio PreprocessAlpha**\n");
		List <DistanceTime> distanceTimeMatrixOrigin = distanceTimeDAO.getDistanceTimeOriginsOf(listWithIdLocation);
		List <DistanceTime> distanceTimeMatrixDestiny = distanceTimeDAO.getDistanceTimeDestiniesOf(listWithIdLocation);
		/**Generación del HashMap de la matriz de distancia para el Origen a Destino**/
		if(distanceTimeMatrixOrigin!=null){
		
			/**COMPLETO EL HASHMAP DE TODOS LOS DATOS DE LA MATRIZ DE DISTANCIA DESDE LOS ORGENES A LOS DESTINOS**/
			for(int count=0; count < distanceTimeMatrixOrigin.size(); count++){
				
				Map<Long, DistanceTimeDataComplete> valueOrigin = new HashMap<Long, DistanceTimeDataComplete>();
				DistanceTimeDataComplete distanceTimeDataComplete = new DistanceTimeDataComplete( );
				distanceTimeDataComplete.setGoingDistance((long)distanceTimeMatrixOrigin.get(count).getDistance());
				distanceTimeDataComplete.setGoingTime((long) distanceTimeMatrixOrigin.get(count).getDuration());
				
				valueOrigin.put(distanceTimeMatrixOrigin.get(count).getDestination(),distanceTimeDataComplete);
				
				distanceTimeMatrixHashMap.put(distanceTimeMatrixOrigin.get(count).getOrigin(), valueOrigin);
				
			}
			//return distanceTimeMatrixHashMap;
		}else{
			logger.info("****No hay ningun dato de la matriz de distancia de Origen a Destino ***************");
			//return null;
			
		}
		/**Generación del HashMap de la matriz de distancia para el Destino al Origen **/
		if(distanceTimeMatrixDestiny!=null){
			
			for(int count=0; count < distanceTimeMatrixDestiny.size(); count++){
				
				Map<Long, DistanceTimeDataComplete> valueDestiny = new HashMap<Long, DistanceTimeDataComplete>();
				DistanceTimeDataComplete distanceTimeDataCompleteDestiny = new DistanceTimeDataComplete( );
				distanceTimeDataCompleteDestiny.setCommingDistance((long)distanceTimeMatrixDestiny.get(count).getDistance());
				distanceTimeDataCompleteDestiny.setCommingTime((long)distanceTimeMatrixDestiny.get(count).getDuration());
				//(long)distanceTimeMatrixDestiny.get(count).getDistance(), (long) distanceTimeMatrixDestiny.get(count).getDuration()
				valueDestiny.put(distanceTimeMatrixDestiny.get(count).getDestination(),distanceTimeDataCompleteDestiny);
				
				distanceTimeMatrixHashMap.put(distanceTimeMatrixDestiny.get(count).getOrigin(), valueDestiny);
				
			}
			//return distanceTimeMatrixHashMap;
		}else{
			logger.info("****No hay ningun dato de la matriz de distancia de Destino a Origen  ***************");
			//return null;
			
			
		}
		if(distanceTimeMatrixHashMap.isEmpty()){
			logger.info("**** El HASHMAP esta VACIO, HAY QUE CALCULARLO COMPLETO ***************");
			return null;
			
		}else{
			logger.info("\n **** ENVIANDO EL HASHMAP AL CONTROLADOR ***************  \n");
			return distanceTimeMatrixHashMap;
		}
	
		
		
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
