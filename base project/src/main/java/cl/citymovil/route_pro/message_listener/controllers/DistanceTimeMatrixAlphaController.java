package cl.citymovil.route_pro.message_listener.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cl.citymovil.route_pro.message_listener.dao.LocationDAO;
import cl.citymovil.route_pro.message_listener.domain.DistanceTimeData;
import cl.citymovil.route_pro.message_listener.domain.DistanceTimeDataComplete;
import cl.citymovil.route_pro.message_listener.domain.Location;
import cl.citymovil.route_pro.message_listener.services.DistanceTimeMatrixServiceAlpha;
import cl.citymovil.route_pro.solver.util.LocationContainer;
import cl.citymovil.route_pro.solver.util.LocationContainerForGoogleAsk;
import cl.citymovil.route_pro.solver.util.RelationLocation;

@Controller
@RequestMapping(value="/requestalpha.htm")
public class DistanceTimeMatrixAlphaController {
	
	/** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private DistanceTimeMatrixServiceAlpha distanceTimeMatrixServiceAlpha;
    /********** Inicio Adicional*/
    
    @Autowired
    private LocationDAO locationDAO;
    /********** Fin Adicional*/
    
    
    
    @RequestMapping(method = RequestMethod.GET)
     public void onSubmit()
     {
    	
    	   /********** Adicional*/
    	ArrayList<Location> arrayWithIdLocation = locationDAO.getLocationList();
    	  /********** Fin Adicional*/
    	
    	Map<Long, Map<Long, DistanceTimeData>> distanceTimeMatrixHashMap= new HashMap<Long, Map<Long, DistanceTimeData>>();
    	distanceTimeMatrixHashMap= distanceTimeMatrixServiceAlpha.PreprocessAlpha(arrayWithIdLocation);     	
    	
    	if(distanceTimeMatrixHashMap==null){
    		
     		logger.info("(FAIL)Don't have Location.");
     		
     	}else{
     		
     		logger.info("(OK)The PreprocessAlpha was return OK.");
     		for (Map.Entry<Long, Map<Long, DistanceTimeData>> outer : distanceTimeMatrixHashMap.entrySet()) {
    		    System.out.println("Key: " + outer.getKey() +  "\n");
    		    
    	
			
			for( Entry<Long, DistanceTimeData> algo : outer.getValue().entrySet() ){
    			   System.out.println("Key = " + algo.getKey() + ", distance = " + algo.getValue().distance  );
    		   }
//    		    for (Map.Entry<Long, DistanceTimeDataComplete> inner : entry.getValue().entrySet()) {
//    		        System.out.println("Key = " + inner.getKey() + ", Value = " + inner.getValue());
//    		    }
    		 } 
     		ArrayList<LocationContainerForGoogleAsk> listOfLocationContainerForGoogle =new  ArrayList <LocationContainerForGoogleAsk>();
     		listOfLocationContainerForGoogle=distanceTimeMatrixServiceAlpha.PreprocessBeta(distanceTimeMatrixHashMap,arrayWithIdLocation);
     		if(listOfLocationContainerForGoogle==null){
     			System.out.println("Location Container NO CONTINE NADA");
     		}else{
     			System.out.println("ESTAMOS OK Location Container  CONTINE ALGO");
     		}
     		System.out.println("\n***********PUNTOS EN CONFLICTO****************\n");
     		for(LocationContainerForGoogleAsk b: listOfLocationContainerForGoogle){
     			System.out.println("\n ///////// (INICIO)Imprimiendo los resultados en un for ");
//     			ArrayList<Location> listOrigin = b.getLocationOrigin();
//     			ArrayList<Location> listDestiny = b.getLocationDestiny();
//     			for(Location ori : listOrigin){
//     				System.out.println("Datos Origen:"+ori.getLocationId());
//     			}
//     			for(Location dest : listDestiny){
//     				System.out.println("Datos destino:"+dest.getLocationId());
//     			}
     			
     			System.out.println("///////// Enviando a Process");
     			
     			ArrayList<RelationLocation> distanceMatrixList = distanceTimeMatrixServiceAlpha.Process(b);
//     			
     			distanceTimeMatrixServiceAlpha.PostProcessAlpha(distanceMatrixList);
     		}
     		
     		
     		
     		
     		
//     		for(int count=0; count < locationContainerForGoogle.size(); count++){
////     			ArrayList<RelationLocation>  distanceMatrixList = distanceTimeMatrixServiceAlpha.Process(locationContainerForGoogle.get(count));
//     		}
//     			
 		   	
 		  // 	distanceMatrixService.PostProcessAlpha(distanceMatrixList);
    	
     	} 
     }
    protected void formBackingObject(HttpServletRequest request) throws ServletException {
    	
    }

//    public void setLocationManager(DistanceMatrixService distanceMatrixService) {
//        this.distanceMatrixService = distanceMatrixService;
//    }
//
//    public DistanceMatrixService getProductManager() {
//        return distanceMatrixService;
//    } 

}
