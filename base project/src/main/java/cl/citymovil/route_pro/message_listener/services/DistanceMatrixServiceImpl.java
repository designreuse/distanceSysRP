package cl.citymovil.route_pro.message_listener.services;

import java.util.List;

import cl.citymovil.route_pro.message_listener.domain.DistanceTime;
import cl.citymovil.route_pro.message_listener.domain.Location;

public class DistanceMatrixServiceImpl implements DistanceMatrixService{

	@Override
	public List<Location>[] Preprocess() {
		//Busqueda de nuevas locaciones 
		//Busueda de las locaciones anteriores si es que encuentro nuevas locaciones, si no hay nuevas locaciones, retorno null.
		return null;
	}

	@Override
	public List<Location> Process(List<Location> newLocation, List<Location> oldLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void PostProcess(List<DistanceTime> saveDistanceTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mergeLocation(Location loc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void persistLocation(Location loc) {
		// TODO Auto-generated method stub
		
	}

}
