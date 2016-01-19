package cl.citymovil.route_pro.message_listener.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cl.citymovil.route_pro.message_listener.domain.LocationTmp;


@Repository
public class LocationTmpDAOImpl implements LocationTmpDAO{

	@PersistenceContext
    public EntityManager em;
	
	
	public void setEntityManager(EntityManager em) {
	        this.em = em;
	}
		

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<LocationTmp> getTmpLocationList() {
		Query query = this.em.createQuery("SELECT s FROM LocationTmp s");
		List<LocationTmp> locationsTmp = (List<LocationTmp>)query.getResultList();
		return locationsTmp;
	}
	

	
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<LocationTmp> getLocationList() {
		System.out.println("/////// inicialndo getLocationList ///////");
		
		Query query = this.em.createQuery("SELECT s FROM Location s");//en la consulta, es necesario el 
		                                                              //nombre de la clase y no el nombre de la table de la base de datos
		
		
		
		
		List<LocationTmp> locations = (List<LocationTmp>)query.getResultList();
		
		// return em.createQuery("select p from Location p ").getResultList();
		// return em.createQuery("select loc from Location loc order by loc.id").getResultList();
		//Query query = em.createQuery("SELECT s FROM Location s");
		
		//return (List<Location>)query.getResultList();
		
		return locations;
	}



	

	@Override
	public void deleteTmpLocation(long LocationId) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void mergeTmpLocation(LocationTmp loc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void persistTmpLocation(LocationTmp loc) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateTmpLocation(long LocationId) {
		// TODO Auto-generated method stub
		
	}



}
