package cl.citymovil.route_pro.message_listener.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cl.citymovil.route_pro.message_listener.domain.Location;

@Repository(value="vehiclePositionDAO")
public class VehiclePositionDAOImpl implements VehiclePositionDAO {

	@PersistenceContext//(type = PersistenceContextType.EXTENDED)
	private EntityManager em;
	
	Logger logger = LoggerFactory.getLogger(VehiclePositionDAOImpl.class);

	@Transactional(readOnly = true)
	public void addVehiclePositionTest(Location location) {
		
		em.persist(location);
	}
	
	
	
	@Transactional(readOnly = true)
	public void addVehiclePosition(String external_id, double latitude, double longitude, Date date) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		Query query = this.em.createNativeQuery("insert into vehicle_position "
				+ "(vehicle_id, latitude, longitude, date) "
				+ "select vehicle_id, '"+latitude+"', '"+longitude+"', '"+formatter.format(date)+"' "
						+ "from vehicle where external_id = '"+external_id+"';");
		query.executeUpdate();
		
	}
	
	@Transactional(readOnly = true)
	public void updateVehicleLastPosition(String externalId, double latitude, double longitude, Date date) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Query query = this.em.createNativeQuery("update last_position set "
				+ "latitude = '"+latitude+"',"
				+ "longitude = '"+longitude+"',"
				+ "date = '"+formatter.format(date)+"' "
				+ "where vehicle_id = "
				+ "(select vehicle_id from vehicle where external_id = '"+externalId+"')");
		query.executeUpdate();
	}

}
