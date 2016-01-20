package cl.citymovil.route_pro.message_listener.services;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
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
		  DistanceTimeMatriz[] distanceTimeMatriz = null;
		 
		  int i=0;
//		  DistanceTimeMatriz[] distanceTimeMatriz = null;
		  
		  //String[] destiny = null;
		  List<Location>  oldLocations= locationConteiner.getLocation();
		  List<LocationTmp> newLocations = locationConteiner.getLocationTmp();
		  
		  for(LocationTmp locTmp: newLocations){
			  
		  			for(Location loc: oldLocations){
			  
						try {
//								String origins[] ={ locTmp.getLatitudeTmp()+","+locTmp.getLongitudeTmp() ,"-33,02 , -70,003" ,"-34,05, -70,005" };
								String origins[] ={ "-33.415858, -70.600841" , "-33.431582, -70.586795", "-33.433809, -70.612752"};
								
								
								String destiny[] ={ loc.getLatitude()+","+loc.getLongitude(), "-34.55, -70.0055","-33.02 , -70.003" };
								//destiny[i]= loc.getLatitude()+","+loc.getLongitude();
								
								DistanceMatrix result = DistanceMatrixApi.getDistanceMatrix(context, origins, origins).await();
								
								System.out.println("::::::::::: viendo el result ::::::::::::: ");
						        System.out.println(ToStringBuilder.reflectionToString(result, ToStringStyle.MULTI_LINE_STYLE));
						        System.out.println(":::::::: Viendo que hay en rows ::::::::::");
						        System.out.println(ToStringBuilder.reflectionToString(result.rows, ToStringStyle.MULTI_LINE_STYLE));
						        System.out.println("::::::::::::::::::::::::::::------------------------------------::::::::::::::::::::::::::::");
						        for(i=0; i<result.rows.length; i++){
						        	 System.out.println(":::::::: Viendo que hay en rows con i ::::::::::");
								        System.out.println(ToStringBuilder.reflectionToString(result.rows[i], ToStringStyle.MULTI_LINE_STYLE));
								        
								        for(int j = 0; j<result.rows[i].elements.length; j++){
								        	System.out.println(":::::::: Viendo que hay en ELEMENTS con i ::::::::::");
								        		
								        		System.out.println("distance: "+ result.rows[i].elements[j].distance.inMeters);
								        		System.out.println("distance: "+ result.rows[i].elements[j].duration.inSeconds);
								        	
//								        	  System.out.println(ToStringBuilder.reflectionToString(result.rows[i].elements[j], ToStringStyle.MULTI_LINE_STYLE));
								        	
								        }
						        	
						        }
						       
						        
						        
						        //System.out.println(result.rows[0].elements[0].distance.inMeters);
						        
						      
						        Long Distance = result.rows[0].elements[0].distance.inMeters;
						  
						        
						        
						        distanceTimeMatriz[i].setDistance(-22.33);
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
