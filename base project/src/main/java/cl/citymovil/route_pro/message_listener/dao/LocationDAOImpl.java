package cl.citymovil.route_pro.message_listener.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cl.citymovil.route_pro.message_listener.domain.Location;

@Repository(value = "locationDao")
public class LocationDAOImpl implements LocationDAO{
	
    public EntityManager em;
	
	 @PersistenceContext(type=PersistenceContextType.EXTENDED)
	    public void setEntityManager(EntityManager em) {
	 	        this.em = em;
	 }
	

	@Transactional(readOnly = true)
	public List<Location> getLocationList() {
		  return em.createQuery("select p from Location p ").getResultList(); //order by p.id    
	}

	@Transactional(readOnly = false)
	public void mergeLocation(Location loc) {
		em.merge(loc);
		
	}

	@Transactional(readOnly = false)
	public void persistLocation(Location loc) {
		em.persist(loc);
		
	}
	
	

}
