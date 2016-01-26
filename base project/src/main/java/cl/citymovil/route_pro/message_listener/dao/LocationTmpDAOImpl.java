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
	public List<LocationTmp> getTmpLocationList() throws Exception {
		
		Query query = this.em.createQuery("SELECT s FROM LocationTmp s");
		
		System.out.println("Consulta REalizada a LocationTmp");
		List<LocationTmp> locationsTmp = (List<LocationTmp>)query.getResultList();
		
		if(locationsTmp.size()==0)
		{
			throw new Exception("No hay resultados");
		}
		
		
		return locationsTmp;
	}

	@Override
	public void deleteTmpLocation(long LocationId) {
		// TODO Auto-generated method stub
		
	}

	

	@Transactional(readOnly = true)
	public void mergeTmpLocation(LocationTmp loc) {
		em.persist(loc);
		
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
