package cl.citymovil.route_pro.message_listener.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.citymovil.route_pro.message_listener.domain.Location;
import cl.citymovil.route_pro.message_listener.domain.ScheduledCustomer;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;


@Service
public class GeoServiceImpl implements GeoService{

//	@Autowired
//	DatabaseService databaseService;
	
	public GeoServiceImpl() {
		
//		locationList = new ArrayList<Location>();
//		locationList.add(new Location(-33.415799, -70.600868));//el bosque norte 0134
//		System.out.println("initializing geoservice ...");
	}
	
//	public boolean pointsAreClose(Location a, Location b) {
//
//		LatLng pointA = new LatLng(a.getLatitude(), a.getLongitude());
//		LatLng pointB = new LatLng(b.getLatitude(), b.getLongitude());
//
//		int meters = (int) LatLngTool.distance(pointA, pointB, LengthUnit.METER);
//
//		if (meters < 100) {
//			System.out.println("está a menos de 100 metros");
//			return true;
//		}
//		return false;
//	}

	public long isNearScheduledCustomer(List<ScheduledCustomer> scheduledCustomers, Location current) {//List<Location> locationList, 

		for (Iterator<ScheduledCustomer> iterator = scheduledCustomers.iterator(); iterator.hasNext();) {
			ScheduledCustomer scheduledCustomer = (ScheduledCustomer) iterator.next();

			LatLng pointA = new LatLng(scheduledCustomer.getLatitude(), scheduledCustomer.getLongitude());
			LatLng pointB = new LatLng(current.getLatitude(), current.getLongitude());

			int meters = (int) LatLngTool.distance(pointA, pointB, LengthUnit.METER);

			if (meters < 100) {
				System.out.println("está a menos de 100 metros del cliente "+ scheduledCustomer.getScheduledCustomerId() );
				return scheduledCustomer.getScheduledCustomerId();
			}
		}
		return 0;
	}
	
}
