package cl.citymovil.route_pro.message_listener.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cl.citymovil.route_pro.message_listener.domain.DistanceTime;
import cl.citymovil.route_pro.message_listener.domain.DistanceTimeData;
import cl.citymovil.route_pro.message_listener.domain.Location;
import cl.citymovil.route_pro.message_listener.domain.ScheduledCustomer;

@Repository
public class DistanceTimeDAOImpl implements DistanceTimeDAO{
	
	@PersistenceContext//(type=PersistenceContextType.EXTENDED)
    public EntityManager em;
	
	
	public void setEntityManager(EntityManager em) {
	        this.em = em;
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<DistanceTime> getDistanceTimeList() {
		
		Query query = this.em.createQuery("SELECT s FROM DistanceTime s");


		List<DistanceTime> distanceTime = (List<DistanceTime>)query.getResultList();
		
		return distanceTime;
	}
	//NUEVO METODO DE DISTANCETIME PARA OBTENER LOS DATOS DE UNA MATRIZ DE DISTANCIA EN BASE A UNA LISTA DE LOCATION
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List <DistanceTime> getDistanceTimeOriginsOf(ArrayList <Location> locationList) {
		System.out.println("**\nDEntro de getDistanceTimeOf\n**\n");
		List <DistanceTime> distTime = new ArrayList <DistanceTime>();
		for(int count=0; count < locationList.size(); count++){
			Query query = this.em.createQuery("SELECT s FROM DistanceTime s where s.origin = :origins")
					.setParameter("origins", locationList.get(count).getLocationId() );
			
			
				distTime = (List<DistanceTime>) query.getResultList();
			
			if(distTime.isEmpty()){
				System.out.println("No se encontro ninguna matriz de distancia para la locación con ID="+locationList.get(count).getLocationId());
			}else{
				distTime.addAll(distTime);
				//System.out.println("Encontre");
//				System.out.println("La matriz de distancia encontrada tiene los datos: \n Origen"+distTime.getOrigin()+" \n Destiny:"+distTime.getDestination()+"\n Distance: "+distTime.getDistance());
//				listDistanceTime.add(count, distTime);
			}
		}
		if(distTime.isEmpty()){
			return null;
		}else{
			return distTime;
		}
		
		
		
		
//		Query query = this.em.createQuery("SELECT s FROM DistanceTime s");
//
//
//		List<DistanceTime> distanceTime = (List<DistanceTime>)query.getResultList();
//		
//		return distanceTime;
	}


	@Transactional
	public void mergeDistanceTime(DistanceTime distanceTime) {
		em.merge(distanceTime);
		
	}

	@Override
	@Transactional
	public void persistDistanceTime(DistanceTime distanceTime) {
		System.out.println("Ingresando a la base de datos un DistanceTime, sus datos son:\n");
		System.out.println("Origen :"+distanceTime.getOrigin()+"\nDestination:"+distanceTime.getDestination()+"\nDistance:"+distanceTime.getDistance()+"\nDuration:"+distanceTime.getDuration());
		em.persist(distanceTime);
	
	}

}
