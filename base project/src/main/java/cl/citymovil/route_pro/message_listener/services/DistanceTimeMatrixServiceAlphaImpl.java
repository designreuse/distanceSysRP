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
			logger.info("**********************ENTRO AL NULL**********************");
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
	public ArrayList<RelationLocation> Process(LocationContainer locationConteiner) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void PostProcessAlpha(ArrayList<RelationLocation> relationLocation) {
		// TODO Auto-generated method stub
		
	}

}
