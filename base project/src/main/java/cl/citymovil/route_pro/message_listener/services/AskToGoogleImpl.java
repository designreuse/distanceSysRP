package cl.citymovil.route_pro.message_listener.services;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.stereotype.Service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;

import cl.citymovil.route_pro.message_listener.domain.DistanceTimeMatriz;
import cl.citymovil.route_pro.message_listener.domain.Location;
import cl.citymovil.route_pro.message_listener.domain.LocationTmp;
import cl.citymovil.route_pro.solver.util.LocationConteiner;

@Service
public class AskToGoogleImpl implements AskToGoogle{
	
	

	@SuppressWarnings("null")
	@Override
	public DistanceTimeMatriz[] getDistanceByGoogle(LocationConteiner locationConteiner){//String[] newLocations,String[] oldLocations) {
		  GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyB-ZZHRgGvMLczqzDZnmFBds4Zs27wm1AY");
		  //String origins2[]={" 55.930, -3.118"};
	      //  String destiny2[]={ "50.087, 14"};
		 
		  int i=0;
		  DistanceTimeMatriz[] distanceTimeMatriz = null;
		  String[] origins = null;
		  String[] destiny = null;
		  List<Location>  oldLocations= locationConteiner.getLocation();
		  List<LocationTmp> newLocations = locationConteiner.getLocationTmp();
		  
		  
		  for(LocationTmp locTmp: newLocations){
			  
		  			for(Location loc: oldLocations){
			  
						try {
								origins[0]= locTmp.getLatitudeTmp()+","+locTmp.getLongitudeTmp() ;
								destiny[0]= loc.getLatitude()+","+loc.getLongitude();
								
								DistanceMatrix result = DistanceMatrixApi.getDistanceMatrix(context, origins, destiny).await();
								
								System.out.println("viendo el result: ");
						        System.out.println(ToStringBuilder.reflectionToString(result, ToStringStyle.MULTI_LINE_STYLE));
						        System.out.println(result.rows[0].elements[0].distance.inMeters);
						        
						        
						      
						        Long Distance = result.rows[0].elements[0].distance.inMeters;
						       
						        
						        
						        distanceTimeMatriz[i].setDistance(Distance.doubleValue());
						       // distanceTimeMatriz[i].setEnd(loc.getLocationId());
							i++;
						} catch (Exception e) {
							
							e.printStackTrace();
							return null;
							
						}
				   
			   	}
		  		i++;
		  		
			  
			  
		  }
		return distanceTimeMatriz;       
	
	}

	@Override
	public void getTimeByGoogle() {
		// TODO Auto-generated method stub
//		return null;
	}

}
