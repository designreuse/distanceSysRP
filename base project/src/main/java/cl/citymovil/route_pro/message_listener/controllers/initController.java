package cl.citymovil.route_pro.message_listener.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.maps.GeoApiContext;

import cl.citymovil.route_pro.message_listener.services.interfaces.LocationManager;

@Controller
public class initController {
	protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private LocationManager locationManager;

    @RequestMapping(value="/hello.htm")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response){
           


        Map<String, Object> myModel = new HashMap<String, Object>();
        myModel.put("location", this.locationManager.getLocations());
        GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyB-ZZHRgGvMLczqzDZnmFBds4Zs27wm1AY");
        
        try{
	      
	        
	        String origins[]={"San Francisco, Californie, États-Unis"};
	        String destiny[]={ "Victoria, BC, Canada"};
	        
	        GeocodingResult[] results =  GeocodingApi.geocode(context,
	        	    "1600 Amphitheatre Parkway Mountain View, CA 94043").await();
	        int i;
	        System.out.println("Inicio ciclo for"+results.length);
	        for(i=0; i< results.length ; i++){
	        	System.out.println(results[i].addressComponents[0]);
	        }
	        
	        DistanceMatrix result = DistanceMatrixApi.getDistanceMatrix(context, origins, destiny).await();
	        System.out.println("viendo el result: ");
	        
	        System.out.println(ToStringBuilder.reflectionToString(result, ToStringStyle.MULTI_LINE_STYLE));
	        System.out.println("------------");
	        
	        System.out.println(result.rows[0].elements[0].distance.inMeters);
	        myModel.put("MatrixDistanceResult", result);
	        System.out.println("-----con lat y log-------");
	        
	        String origins2[]={" 55.930, -3.118"};
	        String destiny2[]={ "50.087, 14"};
	        
	        DistanceMatrix result2 = DistanceMatrixApi.getDistanceMatrix(context, origins2, destiny2).await();
	        System.out.println("viendo el result: ");
	        
	        System.out.println(ToStringBuilder.reflectionToString(result2, ToStringStyle.MULTI_LINE_STYLE));
	 
	        System.out.println("------------");
	        
	        System.out.println(result2.rows[0].elements[0].distance.inMeters);
	        
	        
	      //  System.out.println(ToStringBuilder.reflectionToString(results, ToStringStyle.MULTI_LINE_STYLE));
//	        System.out.println(results.);
        
        
        }catch(Exception e){
        	e.printStackTrace();
        	System.out.println("ahhhhhhhhhhhh:"+e);
        }
        	
        
        myModel.put("blabla", "mojojojo");

        return new ModelAndView("hello", "model", myModel);
    }


    public void setLocationManager(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

}

