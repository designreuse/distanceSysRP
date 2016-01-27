package cl.citymovil.route_pro.message_listener.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cl.citymovil.route_pro.message_listener.domain.DistanceTime;

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

	@Override
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
