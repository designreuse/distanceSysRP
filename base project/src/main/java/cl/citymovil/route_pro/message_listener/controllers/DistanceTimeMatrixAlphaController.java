package cl.citymovil.route_pro.message_listener.controllers;

import java.util.ArrayList;
import java.util.Map;

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
    	
    	Map<Long, Map<Long, DistanceTimeDataComplete>> distanceTimeMatrixHashMap = distanceTimeMatrixServiceAlpha.PreprocessAlpha(arrayWithIdLocation);     	
    	
    	if(distanceTimeMatrixHashMap==null){
    		
     		logger.info("(FAIL)Don't have Location.");
     		
     	}else{
     		
     		logger.info("(OK)The PreprocessAlpha was return OK.");
     		distanceTimeMatrixServiceAlpha.PreprocessBeta(distanceTimeMatrixHashMap);
     		
     		
     		
     		
     		
     		
 		   //	ArrayList<RelationLocation>  distanceMatrixList = distanceMatrixService.Process(locationConteiner);
 		   	
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
