package cl.citymovil.route_pro.message_listener.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cl.citymovil.route_pro.message_listener.domain.ScheduledCustomer;

@Repository
public class ScheduledCustomerDAOImpl implements ScheduledCustomerDAO {

	@PersistenceContext//(type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	@Transactional(readOnly = true)
	public List<ScheduledCustomer> getScheduledCustomersByVehicleExternalId(String externalId) {
		
		Query query = this.em.createQuery("SELECT s FROM ScheduledCustomer s where s.vehicleExternalId = :external_id")
				.setParameter("external_id", externalId );
		return (List<ScheduledCustomer>)query.getResultList();
	}

	@Transactional
	public void updateScheduledCustomerRealArrivalTime(long scheduledCustomerId, Date date ) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Query query = this.em.createNativeQuery("update scheduled_customer "
				+ "SET real_arrival_time = '"+formatter.format(date)+"' "
				+ "where "
						+ "scheduled_customer_id = '"+scheduledCustomerId+"' "
						+ "and real_arrival_time is null");
		query.executeUpdate();
		
	}
}
