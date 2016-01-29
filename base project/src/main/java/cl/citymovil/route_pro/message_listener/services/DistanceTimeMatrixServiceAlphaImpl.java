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
import cl.citymovil.route_pro.message_listener.domain.Location;
import cl.citymovil.route_pro.solver.util.LocationContainer;
import cl.citymovil.route_pro.solver.util.RelationLocation;

@Service
public class DistanceTimeMatrixServiceAlphaImpl implements DistanceTimeMatrixServiceAlpha{
	
	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	DistanceTimeDAO distanceTimeDAO;

	@Override
	public Map<Long, Map<Long, DistanceTimeData>> PreprocessAlpha(ArrayList<Location> listWithIdLocation) {
		Map<Long, Map<Long, DistanceTimeData>> distanceTimeMatrixHashMap = new HashMap<Long, Map<Long, DistanceTimeData>>();
		logger.info("\n**Inicio PreprocessAlpha**\n");
		List <DistanceTime> distanceTimeMatrixOrigin = distanceTimeDAO.getDistanceTimeOriginsOf(listWithIdLocation);
		List <DistanceTime> distanceTimeMatrixDestiny = distanceTimeDAO.getDistanceTimeDestiniesOf(listWithIdLocation);
		/**Generación del HashMap de la matriz de distancia para el Origen a Destino**/
		if(distanceTimeMatrixOrigin!=null){
		
			for(int count=0; count < distanceTimeMatrixOrigin.size(); count++){
				
				Map<Long, DistanceTimeData> value = new HashMap<Long, DistanceTimeData>();
				DistanceTimeData distanceTimeData = new DistanceTimeData( (long)distanceTimeMatrixOrigin.get(count).getDistance(), (long) distanceTimeMatrixOrigin.get(count).getDuration());
				value.put(distanceTimeMatrixOrigin.get(count).getDestination(),distanceTimeData);
				
				distanceTimeMatrixHashMap.put(distanceTimeMatrixOrigin.get(count).getOrigin(), value);
				
			}
			//return distanceTimeMatrixHashMap;
		}else{
			logger.info("****No hay ningun dato de la matriz de distancia de Origen a Destino ***************");
			//return null;
			
		}
		/**Generación del HashMap de la matriz de distancia para el Destino al Origen **/
		if(distanceTimeMatrixDestiny!=null){
			
			for(int count=0; count < distanceTimeMatrixDestiny.size(); count++){
				
				Map<Long, DistanceTimeData> valueDestiny = new HashMap<Long, DistanceTimeData>();
				DistanceTimeData distanceTimeData = new DistanceTimeData( (long)distanceTimeMatrixDestiny.get(count).getDistance(), (long) distanceTimeMatrixDestiny.get(count).getDuration());
				valueDestiny.put(distanceTimeMatrixOrigin.get(count).getDestination(),distanceTimeData);
				
				distanceTimeMatrixHashMap.put(distanceTimeMatrixOrigin.get(count).getOrigin(), valueDestiny);
				
			}
			return distanceTimeMatrixHashMap;
		}else{
			logger.info("****No hay ningun dato de la matriz de distancia de Origen a Destino ***************");
			return null;
			
			
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
